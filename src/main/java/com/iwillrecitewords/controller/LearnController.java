package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.view.LearnView;
import javafx.application.Platform;

/**
 * 背单词页面控制器：集中处理所有背单词的业务逻辑
 */
public class LearnController {
    private LearnView view; // 持有View的引用，用来更新UI
    private Word currentWord; // 当前正在学习的单词

    // 构造函数，注入View
    public LearnController(LearnView view) {
        this.view = view;
        // 初始化第一个单词
        initFirstWord();
    }

    /**
     * 初始化第一个单词（业务逻辑）
     */
    private void initFirstWord() {
        this.currentWord = MainUI.WORD_SERVICE.getRandomWord();
        MainUI.STAT_SERVICE.incrementLearnedCount();
    }

    /**
     * 获取当前单词（给View调用）
     */
    public Word getCurrentWord() {
        return currentWord;
    }

    /**
     * 拼接词性和释义（业务逻辑，View不需要关心怎么拼）
     */
    public String getPosAndMeaning(Word word) {
        if (word.getPartOfSpeech().isEmpty()) {
            return word.getMeaning();
        }
        return word.getPartOfSpeech() + " " + word.getMeaning();
    }

    /**
     * 获取今日已学数量（给View调用）
     */
    public int getLearnedCount() {
        return MainUI.STAT_SERVICE.getLearnedCount();
    }

    /**
     * 处理「记错了」按钮点击（业务逻辑）
     */
    public void handleWrongButtonClick() {
        // 后续可以扩展：把单词加入错词本
        loadNextWord();
    }

    /**
     * 处理「认识，下一个」按钮点击（业务逻辑）
     */
    public void handleKnowButtonClick() {
        loadNextWord();
    }

    /**
     * 加载下一个单词（核心业务逻辑）
     */
    private void loadNextWord() {
        // 1. 业务逻辑：获取新单词、更新统计
        this.currentWord = MainUI.WORD_SERVICE.getRandomWord();
        MainUI.STAT_SERVICE.incrementLearnedCount();

        // 2. 更新UI（必须用Platform.runLater包装，确保JavaFX线程安全）
        Platform.runLater(() -> {
            view.updateWordUI(currentWord);
            view.updateLearnedCountUI();
        });
    }
}