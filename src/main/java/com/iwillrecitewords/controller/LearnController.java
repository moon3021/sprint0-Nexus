package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WordService;
import com.iwillrecitewords.view.LearnView;

public class LearnController {
    private final LearnView learnView;
    private final WordService wordService;
    private Word currentWord;
    private int learnedCount;

    public LearnController(LearnView learnView) {
        this.learnView = learnView;
        this.wordService = new WordService();
        this.currentWord = wordService.getRandomWord();
        this.learnedCount = 0;
    }

    // 记错了按钮 → 已修复，添加错词
    public void handleWrongButtonClick() {
        // 🔥 修复点：添加到错词本
        MainUI.WRONG_WORD_SERVICE.addWrongWord(currentWord);

        currentWord = wordService.getRandomWord();
        learnView.updateWordUI(currentWord);
    }

    // 认识按钮
    public void handleKnowButtonClick() {
        learnedCount++;
        currentWord = wordService.getRandomWord();
        learnView.updateWordUI(currentWord);
        learnView.updateLearnedCountUI();
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public int getLearnedCount() {
        return learnedCount;
    }

    public String getPosAndMeaning(Word word) {
        return word.getPartOfSpeech() + " · " + word.getMeaning();
    }
}