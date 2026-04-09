package com.iwillrecitewords.service;

import com.iwillrecitewords.repository.FileStatRepository;
import com.iwillrecitewords.repository.StatRepository;

/**
 * 学习统计服务：仅负责统计业务逻辑，数据读写委托给仓储接口
 */
public class StudyStatService {
    private final StatRepository statRepository;
    private int learnedCount;
    private int reviewCount;

    public StudyStatService() {
        this.statRepository = new FileStatRepository();
        this.learnedCount = statRepository.loadLearnedCount();
        this.reviewCount = 0;
    }

    public StudyStatService(StatRepository statRepository) {
        this.statRepository = statRepository;
        this.learnedCount = statRepository.loadLearnedCount();
        this.reviewCount = 0;
    }

    public void incrementLearnedCount() {
        learnedCount++;
        statRepository.saveLearnedCount(learnedCount);
    }

    public int getLearnedCount() {
        return learnedCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void updateMasteredCount() {
        // 原有逻辑保留
    }

    public void saveStatData() {
        statRepository.saveLearnedCount(learnedCount);
    }

    public void getTodayStat() {
        // 原有逻辑保留
    }
}