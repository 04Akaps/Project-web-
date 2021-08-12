package com.godcoder.myhome.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),    // 자기 자신의 id 값 (DB에 의 값을 적는다>)
            inverseJoinColumns = @JoinColumn(name = "role_id")  // 상대 테이블의 id 값  (DB에 의 값을 적는다>)
    )
    private List<Role> roles = new ArrayList<>();

    public void add(Role role){
        roles.add(role);
    }

}
