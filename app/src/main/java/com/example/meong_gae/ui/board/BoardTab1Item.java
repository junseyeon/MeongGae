package com.example.meong_gae.ui.board;

// 공지사항 아이템
public class BoardTab1Item {
    private String title;
    private String contents;

    public BoardTab1Item(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
