package com.godcoder.myhome.service;


import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
       String encodedPassword =  passwordEncoder.encode(user.getPassword());
       // 사용자가 전해준 password를 encode 하는 코드
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        // 들어온 password를 인코딩 한뒤 그값을 패스워드로 지정후 true값을 주어서 자격을 준다.roleRepository.
        Role role = new Role();
        role.setId(1L);
        // 이후 새로운 role를 만들어서 그값을 user에 있는 List에 전해준다.
        user.add(role);

        return userRepository.save(user);
    }

}
