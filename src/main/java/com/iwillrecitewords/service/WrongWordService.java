package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 错词本服务：仅负责错词的增删改查
 */
public class WrongWordService {
    private List<Word> wrongWordList;
    // 错词本文件路径，和你的词库文件放在同一目录
    private static final String WRONG_WORD_PATH = "wrong_words.txt";

    public WrongWordService() {
        this.wrongWordList = new ArrayList<>();
        // 初始化加载错词本
        loadWrongWordList();
    }

    /**
     * 添加错词
     */
    public void addWrongWord(Word word) {
        // 空值校验+去重
        if (word == null || !word.isValid() || wrongWordList.contains(word)) {
            return;
        }
        wrongWordList.add(word);
        saveWrongWordList();
    }

    /**
     * 加载错词本
     */
    private void loadWrongWordList() {
        // 1. 读取文件，加空值保护
        String content = FileUtil.readFile(WRONG_WORD_PATH);
        if (content == null || content.isBlank()) {
            this.wrongWordList = new ArrayList<>();
            return;
        }
        // 2. 解析单词列表
        this.wrongWordList = FileUtil.parseWordList(content);
    }

    /**
     * 保存错词本到文件
     */
    private void saveWrongWordList() {
        // 把单词列表转为和词库一样的格式，一行一个单词
        StringBuilder sb = new StringBuilder();
        for (Word word : wrongWordList) {
            // 格式和你的红宝书.txt保持一致，用制表符\t分隔
            sb.append(word.getWord()).append("\t")
                    .append(word.getPhonetic()).append("\t")
                    .append(word.getPartOfSpeech()).append("\t")
                    .append(word.getMeaning()).append("\t")
                    .append(word.getExampleEn()).append("\t")
                    .append(word.getExampleCn()).append("\n");
        }
        // 写入文件
        FileUtil.writeFile(WRONG_WORD_PATH, sb.toString());
    }

    /**
     * 获取所有错词
     */
    public List<Word> getWrongWordList() {
        return new ArrayList<>(wrongWordList);
    }

    /**
     * 获取错词总数
     */
    public int getWrongWordCount() {
        return wrongWordList.size();
    }
}