package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinTable( // 중간 테이블이 user_role 이기 떄문에(DB에서) joinTable를 활용 한다.
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),    // 자기 자신이 USER_ROLE과 연결되어 있는 값을 입력
            inverseJoinColumns = @JoinColumn(name = "role_id")  // 상대 테이블의 id 값  (DB에 의 값을 적는다>)
    )
    private List<Role> roles = new ArrayList<>();

  //  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) //joincolumn과 같은 효과를 준다.
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public void add(Role role){
        roles.add(role);
    }

}
