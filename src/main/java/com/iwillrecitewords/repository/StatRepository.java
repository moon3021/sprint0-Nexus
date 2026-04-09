package com.iwillrecitewords.repository;

/**
 * 学习统计仓储接口：定义统计数据的读写规范
 */
public interface StatRepository {
    /**
     * 保存已学单词数量
     */
    void saveLearnedCount(int count);

    /**
     * 加载已学单词数量
     */
    int loadLearnedCount();
}