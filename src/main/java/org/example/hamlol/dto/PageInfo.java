package org.example.hamlol.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PageInfo {
    private final int totalPosts;      // 모든 글 개수
    private final int currentPage;     // 현재 페이지 번호
    private final int postsPerPage;    // 한 페이지당 표시할 글 개수
    private final int displayPageNum;  // 한 번에 표시할 페이지 개수

}
