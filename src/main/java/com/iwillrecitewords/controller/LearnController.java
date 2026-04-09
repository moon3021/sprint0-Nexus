package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.strategy.LearningStrategy;
import com.iwillrecitewords.view.LearnView;
import javafx.application.Platform;

public class LearnController {
    private final LearnView view;
    private final LearningStrategy learningStrategy;

    public LearnController(LearnView view, LearningStrategy strategy) {
        this.view = view;
        this.learningStrategy = strategy;
        // 加载单词列表
        this.learningStrategy.loadWordList();
    }

    public Word getCurrentWord() {
        return learningStrategy.getCurrentWord();
    }

    public String getProgressText() {
        return learningStrategy.getProgressText();
    }

    // 记错了：强制加入错词本！
    public void handleWrongButtonClick() {
        Word currentWord = learningStrategy.getCurrentWord();
        if (currentWord == null) return;

        // ✅ 强制保存到错词本（修复点）
        MainUI.WRONG_WORD_SERVICE.addWrongWord(currentWord);
        // 执行策略逻辑
        learningStrategy.handleLearnResult(currentWord, false);
        // 下一个单词
        loadNextWord();
    }

    // 认识了
    public void handleKnowButtonClick() {
        Word currentWord = learningStrategy.getCurrentWord();
        if (currentWord == null) return;

        learningStrategy.handleLearnResult(currentWord, true);
        loadNextWord();
    }

    private void loadNextWord() {
        Word nextWord = learningStrategy.getNextWord();
        if (nextWord == null) {
            Platform.runLater(view::showLearningComplete);
            return;
        }
        Platform.runLater(() -> {
            view.updateWordUI(nextWord);
            view.updateLearnedCountUI();
        });
    }
}