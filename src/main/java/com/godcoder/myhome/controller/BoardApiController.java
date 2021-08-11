package com.godcoder.myhome.controller;

import java.util.List;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
public class BoardApiController {

        @Autowired
        private BoardRepository boardrepository;

        @GetMapping("/boards")
        List<Board> all(@RequestParam(required = false) String title) {
            if(StringUtils.isEmpty(title)){
                return boardrepository.findAll();
            }else{
                return boardrepository.findByTitle(title);
            }

        }

        @PostMapping("/boards")
        Board newBoard(@RequestBody Board board) {
            return boardrepository.save(board);
        }

        @GetMapping("/boards/{id}")
        Board one(@PathVariable Long id) {
            return boardrepository.findById(id).orElse(null);
        }


        @PutMapping("/boards/{id}")
        Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

            return boardrepository.findById(id)
                    .map(Board -> {
                        Board.setTitle(newBoard.getTitle());
                        Board.setContent(newBoard.getContent());
                        return boardrepository.save(Board);
                    })
                    .orElseGet(() -> {
                        newBoard.setId(id);
                        return boardrepository.save(newBoard);
                    });
        }

        @DeleteMapping("/boards/{id}")
        void deleteBoard(@PathVariable Long id) {
            boardrepository.deleteById(id);
        }

}
