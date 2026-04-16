package com.iwillrecitewords.model;

public class WrongWord {
    private Long id;
    private Long userId;      // 用户ID
    private String word;     // 单词
    private String meaning;  // 释义
    private String createTime; // 错词记录时间

    // 无参构造
    public WrongWord() {}

    // 全参构造
    public WrongWord(Long id, Long userId, String word, String meaning, String createTime) {
        this.id = id;
        this.userId = userId;
        this.word = word;
        this.meaning = meaning;
        this.createTime = createTime;
    }

    // getter和setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }
    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
}
