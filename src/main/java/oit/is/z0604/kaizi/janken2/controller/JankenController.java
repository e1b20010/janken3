package oit.is.z0604.kaizi.janken2.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0604.kaizi.janken2.model.Entry;
import oit.is.z0604.kaizi.janken2.model.User;
import oit.is.z0604.kaizi.janken2.model.Match;
import oit.is.z0604.kaizi.janken2.model.UserMapper;
import oit.is.z0604.kaizi.janken2.model.MatchMapper;
import oit.is.z0604.kaizi.janken2.model.MatchInfo;
import oit.is.z0604.kaizi.janken2.model.MatchInfoMapper;

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

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    model.addAttribute("loginUser", loginUser);
    ArrayList<User> users = userMapper.selectAllUser();
    model.addAttribute("users", users);
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
    matchInfoMapper.insertMatchInfo(matchinfo);
    model.addAttribute("matchinfo", matchinfo);
    return "wait.html";
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
