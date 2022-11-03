package oit.is.z0604.kaizi.janken2.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z0604.kaizi.janken2.model.Entry;
import oit.is.z0604.kaizi.janken2.model.User;
import oit.is.z0604.kaizi.janken2.model.Match;
import oit.is.z0604.kaizi.janken2.model.UserMapper;
import oit.is.z0604.kaizi.janken2.model.MatchMapper;
import oit.is.z0604.kaizi.janken2.model.MatchInfo;
import oit.is.z0604.kaizi.janken2.model.MatchInfoMapper;

import oit.is.z0604.kaizi.janken2.service.AsyncKekka;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;
  @Autowired
  MatchMapper matchMapper;
  @Autowired
  MatchInfoMapper matchInfoMapper;
  @Autowired
  AsyncKekka Kekka;

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("loginUser", loginUser);
    ArrayList<User> users = userMapper.selectAllUser();
    model.addAttribute("users", users);
    ArrayList<MatchInfo> matchinfo = matchInfoMapper.selectActiveMatchInfo();
    model.addAttribute("matchinfo", matchinfo);
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("loginUser", loginUser);
    User users = userMapper.selectById(id);
    model.addAttribute("users", users);
    return "match.html";
  }

  @Transactional
  @GetMapping("/fight")
  public String fight(@RequestParam Integer id, @RequestParam String hand, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("loginUser", loginUser);
    User user1 = userMapper.selectByName(prin.getName());
    User users = userMapper.selectById(id);
    model.addAttribute("user1", user1);
    model.addAttribute("users", users);
    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(user1.getId());
    matchinfo.setUser2(users.getId());
    matchinfo.setUser1Hand(hand);
    matchinfo.setIsActive(true);
    if(matchInfoMapper.selectUser1MatchInfo(user1.getId())==0 && matchInfoMapper.selectUser2MatchInfo(user1.getId())==0){
      matchInfoMapper.insertMatchInfo(matchinfo);
      model.addAttribute("matchinfo", matchinfo);
    }else{
      MatchInfo tmp = matchInfoMapper.selectUserMatchInfo(user1.getId());
      Match match = new Match();
      match.setUser1(tmp.getUser1());
      match.setUser2(tmp.getUser2());
      match.setUser1Hand(tmp.getUser1Hand());
      match.setUser2Hand(hand);
      match.setIsActive(true);
      matchMapper.insertMatch(match);
      model.addAttribute("match", match);
      matchInfoMapper.updateByIsActive(tmp);
    }
    return "wait.html";
  }

  @GetMapping("active")
  public SseEmitter active() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.Kekka.asyncActiveMatches(sseEmitter);
    return sseEmitter;
  }










  @PostMapping("/name")
  public String name(@RequestParam String name, ModelMap model) {
    String nameResult = name;
    model.addAttribute("nameResult", nameResult);
    return "janken.html";
  }

  @GetMapping("/rock")
  public String rock(Principal prin, ModelMap model) {
    String hand = "Gu";
    String result = "Draw!";
    String loginUser = prin.getName();
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);
    this.entry.addUser(loginUser);
    model.addAttribute("room", this.entry);
    return "janken.html";
  }

  @GetMapping("/scissors")
  public String scissors(Principal prin, ModelMap model) {
    String hand = "Choki";
    String result = "You Lose!";
    String loginUser = prin.getName();
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);
    this.entry.addUser(loginUser);
    model.addAttribute("room", this.entry);
    return "janken.html";
  }

  @GetMapping("/paper")
  public String paper(Principal prin, ModelMap model) {
    String hand = "Pa";
    String result = "You Win!";
    String loginUser = prin.getName();
    model.addAttribute("hand", hand);
    model.addAttribute("result", result);
    this.entry.addUser(loginUser);
    model.addAttribute("room", this.entry);
    return "janken.html";
  }

}
