package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.repository.FileWordRepository;
import com.iwillrecitewords.repository.WordRepository;

import java.util.List;
import java.util.Random;

/**
 * 单词服务：仅负责单词流转，数据读写委托给仓储接口
 */
public class WordService {
    private final WordRepository wordRepository; // 依赖接口，不依赖具体实现
    private final List<Word> wordLibrary;
    private final Random random;
    private int currentIndex;

    // 构造函数注入仓储实现，支持后续替换
    public WordService() {
        this.wordRepository = new FileWordRepository();
        this.wordLibrary = wordRepository.loadAllWords();
        this.random = new Random();
        this.currentIndex = 0;
    }

    // 支持自定义注入仓储，方便单元测试
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
        this.wordLibrary = wordRepository.loadAllWords();
        this.random = new Random();
        this.currentIndex = 0;
    }

    public Word getRandomWord() {
        if (wordLibrary.isEmpty()) {
            return new Word("无词库", "/", "adj.", "请检查词库文件", "No word library", "请配置词库");
        }
        int index = random.nextInt(wordLibrary.size());
        return wordLibrary.get(index);
    }

    public int getTotalWordCount() {
        return wordLibrary.size();
    }

    public void resetCurrentIndex() {
        this.currentIndex = 0;
    }

    public Word getCurrentWord() {
        if (wordLibrary.isEmpty() || currentIndex >= wordLibrary.size()) {
            return null;
        }
        return wordLibrary.get(currentIndex);
    }

    public Word nextWord() {
        if (wordLibrary.isEmpty()) {
            return null;
        }
        currentIndex++;
        if (currentIndex >= wordLibrary.size()) {
            return null;
        }
        return wordLibrary.get(currentIndex);
    }

    public boolean hasNextWord() {
        return !wordLibrary.isEmpty() && currentIndex < wordLibrary.size() - 1;
    }
}