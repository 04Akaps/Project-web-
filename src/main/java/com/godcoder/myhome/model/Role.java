package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles") // user에 있는 컬럼 이름을 적어 준다. == user에 있는 JOINTABLE의 코드를 알아서 참조해 준다.
    @JsonIgnore // 데이터를 표기해주지 않는 설정을 해준다.
    private List<User> users;
}
