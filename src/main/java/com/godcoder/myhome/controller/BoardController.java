package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return"board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }

        return"board/form";
    }
    // 쓰기를 통해서 값을 만들게 될떄 새로운 Board를 생성해주는 코드
    // -> 후에 PostMapping을 통해서 새로 생성된 Board를 db에 추가해준다.

    //  RequestParam을 추가해 줌으로써 수정을 할수 있게 해준다.
    // -> 수정을 할시에 id값을 받아서 중복된 값이 없다면 새로 생성해주고
    // -> 중복된 값이 있다면 중복된 값을 찾아준다.
    // form.html 에서   <input type="hidden" th:field="*{id}"> 를 통해서 id를 받아서 비교한다.

    @PostMapping("/form")
    public String returndata(@ModelAttribute Board board){
        boardRepository.save(board);
        return "redirect:/board/list";
    }
    // -> 쓰기를 통해서 생성된 Board를 받아 오는 코드
    // 쓰기를 통해서 받아온 Board값을 저장해준뒤 다시 list(게시판)으로 이동해준다.
    // -> 이떄 값을 list페이지에서 보여주어야 하기 떄문에
    // -> redirect: 를 통해서 다시 GetMapping("/list")를 호출한다.





}
