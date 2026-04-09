package com.iwillrecitewords.strategy;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.repository.FileWordRepository;
import com.iwillrecitewords.repository.WordRepository;

import java.util.List;

/**
 * 错词复习策略：仅从错词本获取单词
 */
public class WrongWordReviewStrategy implements LearningStrategy {
    private final WordRepository wordRepository;
    private List<Word> wrongWordList;
    private int currentIndex;

    public WrongWordReviewStrategy() {
        this.wordRepository = new FileWordRepository();
        loadWordList();
    }

    @Override
    public String getStrategyName() {
        return "错词复习";
    }

    @Override
    public void loadWordList() {
        this.wrongWordList = wordRepository.loadWrongWords();
        this.currentIndex = 0;
    }

    @Override
    public Word getCurrentWord() {
        if (wrongWordList.isEmpty() || currentIndex >= wrongWordList.size()) {
            return null;
        }
        return wrongWordList.get(currentIndex);
    }

    @Override
    public Word getNextWord() {
        if (!hasNextWord()) {
            return null;
        }
        currentIndex++;
        return getCurrentWord();
    }

    @Override
    public void handleLearnResult(Word word, boolean isKnow) {
        // 认识的单词，从错词本移除
        if (isKnow) {
            wrongWordList.remove(word);
            wordRepository.saveWrongWords(wrongWordList);
        }
    }

    @Override
    public boolean hasNextWord() {
        return !wrongWordList.isEmpty() && currentIndex < wrongWordList.size() - 1;
    }

    @Override
    public String getProgressText() {
        return "📖 复习进度：" + (currentIndex + 1) + "/" + wrongWordList.size();
    }
}