package org.example.hamlol.controller;

import org.example.hamlol.dto.BoardTitlesRequestDto;
import org.example.hamlol.dto.BoardTitlesResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/board")

public class BoardController {

    @GetMapping("/titles")
    public ResponseEntity<BoardTitlesResponseDto>getTitles(BoardTitlesRequestDto boardTitlesRequestDto){
        List<String> titles = Arrays.asList(
                "첫 번쨰 게시글 제목",
                "블로그에 대한 공지사항을 알려드립니다.",
                "누구나 작성할 수 있는 블로그",
                "블로그의 모든 것",
                "닥쳐"
        );
        return ResponseEntity.ok(
                BoardTitlesResponseDto
                        .builder()
                        .titles(
                                titles
                                        .stream()
                                        .filter(title -> title.contains(boardTitlesRequestDto.title()))
                                        .collect(Collectors.toUnmodifiableList())

                        )
                        .build()
        );
    }
}
