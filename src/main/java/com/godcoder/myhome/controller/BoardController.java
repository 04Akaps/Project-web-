package com.godcoder.myhome.controller;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardValidator boardValidator;
    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
       // List<Board> boards = boardRepository.findAll();
       // Page<Board> boards = boardRepository.findAll(PageRequest.of(0,20));
       // Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);


        model.addAttribute("startpage", startPage);
        model.addAttribute("endpage", endPage);
        model.addAttribute("boards", boards);
        return"board/list";
    }
    //Page를 통해서 말 그대로 page를 구성할수 있다
    // page를 사용하게 되면 list.html에서는 list.size를 사용하면 안되고
    // tatalElement를 사용해야 한다.(get은 붙이지 않아도 된다)
    // 수정전 : ${#lists.size(boards)}
    // 수전후 : ${boards.totalElements}

    // pageable를 변수로 받게 하면서 페이지를 지정해 줄수가 있다.

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
    public String returndata(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()) {
            return "board/form";
        }

        String username = authentication.getName();
        boardService.save(username, board);
        return "redirect:/board/list";
    }
    // -> 쓰기를 통해서 생성된 Board를 받아 오는 코드
    // 쓰기를 통해서 받아온 Board값을 저장해준뒤 다시 list(게시판)으로 이동해준다.
    // -> @ModelAttribute를 통해서 입력한 값을 받아온다 .

    // -> 이떄 값을 list페이지에서 보여주어야 하기 떄문에
    // -> redirect: 를 통해서 다시 GetMapping("/list")를 호출한다.
    // redirect를 붙여줌으로써 바로  list메서드를 실행한다.


    // Authentication 인증된 정보를 받아 오는 인터페이스
    // 말그대로 인증된 user의 정보를 꺼내올수 있는 인터페이스 이다.
    // 글을 작성하는 사용자는 인증이 되어있는 사용자 이기 떄문에 Authentication인터페이스를 사용하면 된다.
    // 인증 정보가 하나이기 떄문에 이렇게 받아 올수가 있다.

    // board.serUser(user)를 통해서 값을 저장할수도 있지만
    // 다른 해킹의 위험이 있기 떄문에 Authentication를 이용한다.




}
