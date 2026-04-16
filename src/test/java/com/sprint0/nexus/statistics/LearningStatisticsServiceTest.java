package com.sprint0.nexus.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 学习统计服务测试类
 * 测试 LearningStatisticsService 的核心方法
 */
public class LearningStatisticsServiceTest {

    private LearningStatisticsService service;

    @BeforeEach
    void setUp() {
        service = new LearningStatisticsService(); // 初始化服务（会触发 initMockData）
    }

    @Test
    void testGetTodayOverview() {
        Long userId = 1L;
        TodayOverview overview = service.getTodayOverview(userId);

        // 基础断言：今日日期、非空（若有数据）
        assertEquals(LocalDate.now(), overview.getDate());
        assertNotNull(overview.getWordTypeStats());
    }

    @Test
    void testGetWeeklyTrend() {
        Long userId = 1L;
        Map<LocalDate, Integer> trend = service.getWeeklyTrend(userId);

        // 断言：近7天数据，每天都有值（即使为0）
        assertEquals(7, trend.size());
        assertTrue(trend.containsKey(LocalDate.now().minusDays(6))); // 包含7天前
        assertTrue(trend.containsKey(LocalDate.now())); // 包含今天
    }

    @Test
    void testGetWordTypeStatistics() {
        Long userId = 1L;
        Map<String, Map<String, Integer>> stats = service.getWordTypeStatistics(userId);

        // 断言：至少包含模拟数据中的类型（如"核心词"、"高频词"）
        assertTrue(stats.containsKey("核心词"));
        assertTrue(stats.containsKey("高频词"));
        assertTrue(stats.containsKey("低频词"));
    }
}