package com.godcoder.myhome.service;


import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
       User user = userRepository.findByUsername(username);
       board.setUser(user);

       return boardRepository.save(board);
    }

    // 글을 작성하였을떄 자동으로 board에 user_id 의 값이 입력이 되는 Service를 제공한다.

}
