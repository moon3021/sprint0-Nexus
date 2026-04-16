package com.sprint0.nexus.statistics;

import java.time.LocalDate;
import java.time.Duration;

/**
 * 学习记录实体类（对应数据库/业务中的学习记录）
 */
public class LearningRecord {
    private Long id;
    private Long userId;       // 用户ID
    private String word;       // 单词
    private String wordStatus; // 单词状态（如：已掌握、未掌握、学习中）
    private String wordType;   // 单词类型（如：考研核心、高频、低频）
    private boolean isCorrect; // 是否正确（答题结果）
    private LocalDate studyDate; // 学习日期
    private Duration duration; // 学习时长（Duration类型）

    // 构造器
    public LearningRecord(Long id, Long userId, String word, String wordStatus,
                          String wordType, boolean isCorrect, LocalDate studyDate,
                          Duration duration) {
        this.id = id;
        this.userId = userId;
        this.word = word;
        this.wordStatus = wordStatus;
        this.wordType = wordType;
        this.isCorrect = isCorrect;
        this.studyDate = studyDate;
        this.duration = duration;
    }

    // Getter方法（必须和报错中的调用对应）
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getWord() { return word; }
    public String getWordStatus() { return wordStatus; }
    public String getWordType() { return wordType; }
    public boolean isCorrect() { return isCorrect; } // 注意：boolean类型的getter可以是isXXX()
    public LocalDate getStudyDate() { return studyDate; }
    public Duration getDuration() { return duration; }
    public long getDurationMinutes() {
        return duration != null ? duration.toMinutes() : 0;
    }
}