package com.sprint0.nexus.statistics;

import java.time.LocalDate;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学习统计服务类
 * 负责处理用户学习数据的统计、分析与查询
 */
public class LearningStatisticsService {

    // 模拟数据源（实际项目中应注入Repository访问数据库）
    private List<LearningRecord> mockData;

    public LearningStatisticsService() {
        initMockData(); // 初始化模拟数据
    }

    // ---------- 核心统计方法 ----------

    /**
     * 获取今日学习总览
     *
     * @param userId 用户ID
     * @return TodayOverview 今日学习总览对象
     */
    public TodayOverview getTodayOverview(Long userId) {
        LocalDate today = LocalDate.now();

        // 过滤出今日、指定用户的学习记录
        List<LearningRecord> todayRecords = mockData.stream()
                .filter(r -> r.getStudyDate().equals(today) && r.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (todayRecords.isEmpty()) {
            // 返回包含空列表的TodayOverview对象
            return new TodayOverview(
                    today,
                    0,
                    0,
                    0,
                    0.0,
                    Collections.emptyMap(),
                    Collections.emptyList()  // 添加第7个参数：今日记录列表
            );
        }

        // 统计总数、正确数、错误数
        int totalWords = todayRecords.size();
        int correctCount = (int) todayRecords.stream().filter(LearningRecord::isCorrect).count();
        int wrongCount = totalWords - correctCount;
        double accuracyRate = totalWords == 0 ? 0.0 : (double) correctCount / totalWords * 100;

        // 统计单词类型分布（如：核心词、高频词的数量）
        Map<String, Integer> wordTypeStats = todayRecords.stream()
                .collect(Collectors.groupingBy(
                        LearningRecord::getWordType,
                        Collectors.summingInt(r -> 1)
                ));

        // 构造TodayOverview对象，传入所有7个参数
        return new TodayOverview(
                today,
                totalWords,
                correctCount,
                wrongCount,
                accuracyRate,
                wordTypeStats,
                todayRecords  // 第7个参数：今日记录列表
        );
    }

    /**
     * 获取学习趋势（近7天）
     *
     * @param userId 用户ID
     * @return 每日学习单词数的列表（按日期升序）
     */
    public Map<LocalDate, Integer> getWeeklyTrend(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6); // 近7天（包含今天）

        // 过滤出近7天、指定用户的学习记录
        List<LearningRecord> weekRecords = mockData.stream()
                .filter(r -> {
                    LocalDate recordDate = r.getStudyDate();
                    // 包含今天和过去6天（共7天）
                    return !recordDate.isBefore(sevenDaysAgo) &&
                            !recordDate.isAfter(today) &&
                            r.getUserId().equals(userId);
                })
                .collect(Collectors.toList());

        // 按日期分组，统计每日学习单词数
        Map<LocalDate, Integer> dailyStats = weekRecords.stream()
                .collect(Collectors.groupingBy(
                        LearningRecord::getStudyDate,
                        Collectors.summingInt(r -> 1)
                ));

        // 补全近7天的日期（即使某天没有学习记录，也显示0）
        Map<LocalDate, Integer> fullWeekStats = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = sevenDaysAgo.plusDays(i);
            fullWeekStats.put(date, dailyStats.getOrDefault(date, 0));
        }

        return fullWeekStats;
    }

    /**
     * 获取单词类型统计（如：核心词、高频词的掌握情况）
     *
     * @param userId 用户ID
     * @return 按单词类型分组的统计（如：核心词-已掌握5个，未掌握3个）
     */
    public Map<String, Map<String, Integer>> getWordTypeStatistics(Long userId) {
        // 过滤出指定用户的学习记录
        List<LearningRecord> userRecords = mockData.stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (userRecords.isEmpty()) {
            return Collections.emptyMap();
        }

        // 按单词类型分组，再按单词状态分组统计数量
        return userRecords.stream()
                .collect(Collectors.groupingBy(
                        LearningRecord::getWordType, // 一级分组：单词类型
                        Collectors.groupingBy(       // 二级分组：单词状态
                                LearningRecord::getWordStatus,
                                Collectors.summingInt(r -> 1)
                        )
                ));
    }

    /**
     * 获取累计学习统计
     *
     * @param userId 用户ID
     * @return 累计统计信息
     */
    public Map<String, Object> getCumulativeStats(Long userId) {
        // 过滤出指定用户的所有学习记录
        List<LearningRecord> userRecords = mockData.stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (userRecords.isEmpty()) {
            return Map.of(
                    "totalWords", 0,
                    "totalStudyDays", 0,
                    "totalDuration", 0,
                    "accuracyRate", 0.0
            );
        }

        // 统计累计数据
        int totalWords = userRecords.size();
        int correctCount = (int) userRecords.stream().filter(LearningRecord::isCorrect).count();
        double accuracyRate = (double) correctCount / totalWords * 100;

        // 计算总学习天数
        long totalStudyDays = userRecords.stream()
                .map(LearningRecord::getStudyDate)
                .distinct()
                .count();

        // 计算总学习时长（分钟）
        long totalDuration = userRecords.stream()
                .mapToLong(r -> r.getDuration().toMinutes())
                .sum();

        return Map.of(
                "totalWords", totalWords,
                "totalStudyDays", totalStudyDays,
                "totalDuration", totalDuration,
                "accuracyRate", Math.round(accuracyRate * 100.0) / 100.0
        );
    }

    /**
     * 获取单词掌握进度统计
     *
     * @param userId 用户ID
     * @return 各状态单词数量统计
     */
    public Map<String, Long> getWordMasteryProgress(Long userId) {
        // 过滤出指定用户的学习记录
        List<LearningRecord> userRecords = mockData.stream()
                .filter(r -> r.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (userRecords.isEmpty()) {
            return Map.of(
                    "total", 0L,
                    "mastered", 0L,
                    "learning", 0L,
                    "unmastered", 0L
            );
        }

        // 统计各状态单词数量
        long total = userRecords.size();
        long mastered = userRecords.stream()
                .filter(r -> "已掌握".equals(r.getWordStatus()))
                .count();
        long learning = userRecords.stream()
                .filter(r -> "学习中".equals(r.getWordStatus()))
                .count();
        long unmastered = userRecords.stream()
                .filter(r -> "未掌握".equals(r.getWordStatus()))
                .count();

        return Map.of(
                "total", total,
                "mastered", mastered,
                "learning", learning,
                "unmastered", unmastered
        );
    }

    /**
     * 初始化模拟数据（实际项目中应从数据库加载）
     */
    private void initMockData() {
        mockData = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = today.minusDays(2);

        // 模拟今日学习记录（用户ID=1）
        mockData.add(new LearningRecord(1L, 1L, "abandon", "学习中", "核心词", true, today, Duration.ofMinutes(5)));
        mockData.add(new LearningRecord(2L, 1L, "ability", "已掌握", "高频词", true, today, Duration.ofMinutes(3)));
        mockData.add(new LearningRecord(3L, 1L, "abnormal", "未掌握", "低频词", false, today, Duration.ofMinutes(4)));

        // 模拟昨日学习记录（用户ID=1）
        mockData.add(new LearningRecord(4L, 1L, "aboard", "学习中", "核心词", true, yesterday, Duration.ofMinutes(6)));
        mockData.add(new LearningRecord(5L, 1L, "absence", "已掌握", "高频词", false, yesterday, Duration.ofMinutes(2)));
        mockData.add(new LearningRecord(6L, 1L, "absolute", "学习中", "核心词", true, yesterday, Duration.ofMinutes(4)));

        // 模拟前天学习记录（用户ID=1）
        mockData.add(new LearningRecord(7L, 1L, "absorb", "已掌握", "高频词", true, twoDaysAgo, Duration.ofMinutes(3)));
        mockData.add(new LearningRecord(8L, 1L, "abstract", "未掌握", "低频词", false, twoDaysAgo, Duration.ofMinutes(5)));

        // 模拟其他用户的学习记录（用户ID=2）
        mockData.add(new LearningRecord(9L, 2L, "abundant", "学习中", "核心词", true, today, Duration.ofMinutes(4)));
        mockData.add(new LearningRecord(10L, 2L, "abuse", "已掌握", "高频词", true, today, Duration.ofMinutes(3)));
    }
}