package com.iwillrecitewords.service;

import com.iwillrecitewords.util.FileUtil;

/**
 * 学习统计服务：持久化存储学习数据，提供统计能力
 */
public class StudyStatService {
    private int learnedCount;  // 已学单词数量
    private int reviewCount;   // 复习单词数量

    public StudyStatService() {
        // 启动时从本地加载已存数据
        this.learnedCount = FileUtil.loadLearnedCount();
        this.reviewCount = 0; // 后续可扩展复习数据持久化
    }

    /**
     * 新增一个已学单词，自动保存到本地
     */
    public void incrementLearnedCount() {
        learnedCount++;
        FileUtil.saveLearnedCount(learnedCount);
    }

    /**
     * 获取已学单词数量
     */
    public int getLearnedCount() {
        return learnedCount;
    }

    /**
     * 获取复习单词数量
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * 新增复习单词数
     */
    public void incrementReviewCount() {
        reviewCount++;
    }
}