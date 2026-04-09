package com.iwillrecitewords.repository;

import com.iwillrecitewords.model.Word;

import java.util.List;

/**
 * 单词仓储接口：定义单词数据的读写规范
 */
public interface WordRepository {
    /**
     * 加载所有单词
     */
    List<Word> loadAllWords();

    /**
     * 加载错词列表
     */
    List<Word> loadWrongWords();

    /**
     * 保存错词列表
     */
    void saveWrongWords(List<Word> wrongWords);

    /**
     * 添加单个错词
     */
    void addWrongWord(Word word);
}