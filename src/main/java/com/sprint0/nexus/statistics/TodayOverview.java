package com.sprint0.nexus.statistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 今日学习总览（数据传输对象）
 */
public class TodayOverview {
    private LocalDate date;             // 日期（今日）
    private int totalWords;             // 今日学习单词总数
    private int correctCount;           // 正确单词数
    private int incorrectCount;         // 错误单词数
    private double accuracyRate;        // 正确率（百分比）
    private Map<String, Integer> wordTypeStats; // 按单词类型的统计（如：核心词-5，高频词-3）
    private List<LearningRecord> todayRecords; // 今日所有学习记录

    // 构造器（全参，方便初始化）
    public TodayOverview(LocalDate date, int totalWords, int correctCount,
                         int incorrectCount, double accuracyRate,
                         Map<String, Integer> wordTypeStats,
                         List<LearningRecord> todayRecords) {
        this.date = date;
        this.totalWords = totalWords;
        this.correctCount = correctCount;
        this.incorrectCount = incorrectCount;
        this.accuracyRate = accuracyRate;
        this.wordTypeStats = wordTypeStats;
        this.todayRecords = todayRecords;
    }

    // Getter 方法（必须提供，否则外部无法访问属性）
    public LocalDate getDate() {
        return date;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public double getAccuracyRate() {
        return accuracyRate;
    }

    public Map<String, Integer> getWordTypeStats() {
        return wordTypeStats;
    }

    public List<LearningRecord> getTodayRecords() {
        return todayRecords;
    }

    // Setter 方法（可选，根据业务需求决定是否允许修改）
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public void setIncorrectCount(int incorrectCount) {
        this.incorrectCount = incorrectCount;
    }

    public void setAccuracyRate(double accuracyRate) {
        this.accuracyRate = accuracyRate;
    }

    public void setWordTypeStats(Map<String, Integer> wordTypeStats) {
        this.wordTypeStats = wordTypeStats;
    }

    public void setTodayRecords(List<LearningRecord> todayRecords) {
        this.todayRecords = todayRecords;
    }
}