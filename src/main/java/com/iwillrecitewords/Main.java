package com.iwillrecitewords;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WordService;
import com.iwillrecitewords.service.StudyStatService;
import com.iwillrecitewords.view.ConsoleView;

/**
 * 程序入口：协调各模块，启动交互流程
 */
public class Main {
    public static void main(String[] args) {
        // 初始化各模块
        WordService wordService = new WordService();
        StudyStatService statService = new StudyStatService();
        ConsoleView view = new ConsoleView();

        // 显示欢迎信息
        view.showWelcome();

        // 先推送第一个单词
        Word currentWord = wordService.getRandomWord();
        view.showWord(currentWord);
        statService.incrementLearnedCount();

        // 主交互循环
        boolean isRunning = true;
        while (isRunning) {
            String input = view.getUserInput();
            switch (input) {
                case "next":
                    // 切换下一个单词
                    currentWord = wordService.getRandomWord();
                    view.showWord(currentWord);
                    statService.incrementLearnedCount();
                    break;
                case "stat":
                    // 查看统计
                    view.showStat(statService.getLearnedCount());
                    break;
                case "exit":
                    // 退出程序
                    isRunning = false;
                    break;
                default:
                    // 未知指令
                    view.showUnknownCommand();
                    break;
            }
        }

        // 显示退出信息
        view.showExit();
    }
}