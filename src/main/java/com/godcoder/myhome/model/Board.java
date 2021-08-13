package com.godcoder.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min =2, max = 30, message = "제목은 2자이상 30자 미만 입니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    //@JoinColumn 을 통해서 user_id를 참조하여 user의 값을 받아온다.
    private User user;
}
