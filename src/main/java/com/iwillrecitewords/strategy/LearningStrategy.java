package com.iwillrecitewords.strategy;

import com.iwillrecitewords.model.Word;

/**
 * 学习策略接口：定义所有学习模式的统一规范
 */
public interface LearningStrategy {
    /**
     * 策略名称
     */
    String getStrategyName();

    /**
     * 加载学习单词列表
     */
    void loadWordList();

    /**
     * 获取当前学习的单词
     */
    Word getCurrentWord();

    /**
     * 获取下一个单词
     */
    Word getNextWord();

    /**
     * 处理学习结果
     */
    void handleLearnResult(Word word, boolean isKnow);

    /**
     * 是否还有下一个单词
     */
    boolean hasNextWord();

    /**
     * 获取学习进度
     */
    String getProgressText();
}