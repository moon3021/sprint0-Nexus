package com.iwillrecitewords.strategy;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.repository.FileWordRepository;
import com.iwillrecitewords.repository.WordRepository;
import com.iwillrecitewords.service.StudyStatService;

import java.util.List;
import java.util.Random;

/**
 * 普通学习策略：随机从词库获取单词
 */
public class NormalLearnStrategy implements LearningStrategy {
    private final WordRepository wordRepository;
    private final StudyStatService statService;
    private List<Word> wordList;
    private int currentIndex;
    private final Random random;

    public NormalLearnStrategy() {
        this.wordRepository = new FileWordRepository();
        this.statService = new StudyStatService();
        this.random = new Random();
        loadWordList();
    }

    @Override
    public String getStrategyName() {
        return "普通单词学习";
    }

    @Override
    public void loadWordList() {
        this.wordList = wordRepository.loadAllWords();
        this.currentIndex = 0;
    }

    @Override
    public Word getCurrentWord() {
        if (wordList.isEmpty()) {
            return null;
        }
        return wordList.get(currentIndex);
    }

    @Override
    public Word getNextWord() {
        if (!hasNextWord()) {
            return null;
        }
        // 随机获取下一个单词
        currentIndex = random.nextInt(wordList.size());
        statService.incrementLearnedCount();
        return getCurrentWord();
    }

    @Override
    public void handleLearnResult(Word word, boolean isKnow) {
        // 普通模式下，认识则不处理，不认识则加入错词本（由Controller处理）
    }

    @Override
    public boolean hasNextWord() {
        return !wordList.isEmpty();
    }

    @Override
    public String getProgressText() {
        return "已学习：" + statService.getLearnedCount() + " 词";
    }
}