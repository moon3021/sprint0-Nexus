package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.util.FileUtil;

import java.util.List;
import java.util.Random;

/**
 * 单词服务：仅负责词库加载、单词流转与基础查询
 * 单一职责：错词管理已拆分到WrongWordService
 */
public class WordService {
    private final List<Word> wordLibrary;
    private final Random random;
    private int currentIndex; // 为顺序学习/策略模式预留的当前索引

    public WordService() {
        // 从本地文件加载词库
        this.wordLibrary = FileUtil.loadWordLibrary();
        this.random = new Random();
        this.currentIndex = 0;
    }

    /**
     * 随机获取一个单词（原有功能保留）
     */
    public Word getRandomWord() {
        if (wordLibrary.isEmpty()) {
            return new Word("无词库", "/", "adj.", "请检查红宝书.txt文件", "No word library", "请配置词库");
        }
        int index = random.nextInt(wordLibrary.size());
        return wordLibrary.get(index);
    }

    /**
     * 获取词库总单词数（原有功能保留）
     */
    public int getTotalWordCount() {
        return wordLibrary.size();
    }

    // ====================== 为策略模式/顺序学习预留的方法 ======================
    /**
     * 重置当前学习索引（给策略模式调用）
     */
    public void resetCurrentIndex() {
        this.currentIndex = 0;
    }

    /**
     * 获取当前正在学习的单词（给策略模式调用）
     */
    public Word getCurrentWord() {
        if (wordLibrary.isEmpty() || currentIndex >= wordLibrary.size()) {
            return null;
        }
        return wordLibrary.get(currentIndex);
    }

    /**
     * 切换到下一个单词（给策略模式调用）
     */
    public Word nextWord() {
        if (wordLibrary.isEmpty()) {
            return null;
        }
        // 索引+1，若超出范围则返回null（表示学习完成）
        currentIndex++;
        if (currentIndex >= wordLibrary.size()) {
            return null;
        }
        return wordLibrary.get(currentIndex);
    }

    /**
     * 检查是否还有下一个单词（给策略模式调用）
     */
    public boolean hasNextWord() {
        return !wordLibrary.isEmpty() && currentIndex < wordLibrary.size() - 1;
    }
    // 新增：获取全量词库列表，供词库管理页面使用
    public List<Word> getWordLibrary() {
        return wordLibrary;
    }
}