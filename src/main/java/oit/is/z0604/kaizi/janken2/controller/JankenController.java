package oit.is.z0604.kaizi.janken2.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z0604.kaizi.janken2.model.Entry;
import oit.is.z0604.kaizi.janken2.model.User;
import oit.is.z0604.kaizi.janken2.model.UserMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;
  /*
   * @GetMapping("/janken")
   * public String janken(Principal prin, ModelMap model) {
   * String loginUser = prin.getName();
   * this.entry.addUser(loginUser);
   * model.addAttribute("room", this.entry);
   * return "janken.html";
   * }
   */

  @GetMapping("/janken")
  public String janken(ModelMap model) {
    ArrayList<User> users = userMapper.selectAllUser();
    model.addAttribute("users", users);
    return "janken.html";
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
