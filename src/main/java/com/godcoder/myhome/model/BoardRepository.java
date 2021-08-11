package com.godcoder.myhome.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
