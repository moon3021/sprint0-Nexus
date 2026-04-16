package com.iwillrecitewords.view;

import com.iwillrecitewords.model.Word;
import java.util.Scanner;

/**
 * 控制台视图：负责用户输入输出交互
 */
public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    // 显示欢迎信息
    public void showWelcome() {
        System.out.println("========================================");
        System.out.println("       我会背单词 - Sprint 1 MVP        ");
        System.out.println("========================================");
        System.out.println("指令说明：");
        System.out.println("  next  - 切换下一个单词");
        System.out.println("  stat  - 查看已学单词数量");
        System.out.println("  exit  - 退出程序");
        System.out.println("========================================\n");
    }

    // 显示单词信息
    public void showWord(Word word) {
        System.out.println("📚 当前单词：");
        System.out.println("  " + word);
        System.out.println();
    }

    // 获取用户输入指令
    public String getUserInput() {
        System.out.print("请输入指令：");
        return scanner.nextLine().trim().toLowerCase();
    }

    // 显示已学数量统计
    public void showStat(int learnedCount) {
        System.out.println("\n📊 学习统计：");
        System.out.println("  你已经学习了 " + learnedCount + " 个单词！");
        System.out.println();
    }

    // 显示未知指令提示
    public void showUnknownCommand() {
        System.out.println("❌ 未知指令，请输入 next/stat/exit\n");
    }

    // 显示退出信息
    public void showExit() {
        System.out.println("\n👋 再见！继续加油背单词！");
        scanner.close();
    }
}