package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

        @Autowired
        private UserRepository userrepository;

        @GetMapping("/users")
        List<User> all() {
           return userrepository.findAll();
        }

        @PostMapping("/users")
        User newuser(@RequestBody User user) {
            return userrepository.save(user);
        }

        @GetMapping("/users/{id}")
        User one(@PathVariable Long id) {
            return userrepository.findById(id).orElse(null);
        }


        @PutMapping("/users/{id}")
        User replaceuser(@RequestBody User newuser, @PathVariable Long id) {

            return userrepository.findById(id)
                    .map(user -> {
                        user.setBoards(newuser.getBoards());
//                        user.getBoards().clear();
//                        user.getBoards().addAll(newuser.getBoards());
                        for(Board board : user.getBoards()){
                            board.setUser(user);
                        }
                        return userrepository.save(user);
                    })
                    .orElseGet(() -> {
                        newuser.setId(id);
                        return userrepository.save(newuser);
                    });
        }

        @DeleteMapping("/users/{id}")
        void deleteuser(@PathVariable Long id) {
            userrepository.deleteById(id);
        }

}
