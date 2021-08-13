package com.godcoder.myhome.controller;


import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import com.godcoder.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        //UserService에서 처리를 해준다.
        userService.save(user);
        return "redirect:/";
    }

    // get은 단순히 해당 url의 html파일을 열어주는 역할을 한다
    // post는  해당 페이지에서 들어오는 정보를 받아오는 역할을 한다.

    // get에서 model을 통해서 전송을 해줄수도 있지만 따로 class가 있기떄문에
    // post를 이용용
}
