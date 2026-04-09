package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.repository.FileWordRepository;
import com.iwillrecitewords.repository.WordRepository;
import java.util.List;

public class WrongWordService {
    private final WordRepository repository;
    private List<Word> wrongWords;

    public WrongWordService() {
        this.repository = new FileWordRepository();
        this.wrongWords = repository.loadWrongWords();
    }

    // ✅ 修复：强制添加并保存
    public void addWrongWord(Word word) {
        if (word == null || !word.isValid()) return;
        if (!wrongWords.contains(word)) {
            wrongWords.add(word);
            repository.saveWrongWords(wrongWords);
            // 重新加载，保证同步
            wrongWords = repository.loadWrongWords();
        }
    }

    public List<Word> getWrongWordList() {
        return wrongWords;
    }

    public int getWrongWordCount() {
        return wrongWords.size();
    }
}