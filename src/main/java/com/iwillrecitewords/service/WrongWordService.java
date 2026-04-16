package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 错词本服务：仅负责错词的增删改查
 */
public class WrongWordService {
    private List<Word> wrongWordList;
    // 错词本文件路径，和词库文件同目录
    private static final String WRONG_WORD_PATH = "wrong_words.txt";

    public WrongWordService() {
        this.wrongWordList = new ArrayList<>();
        System.out.println("[WrongWordService] 初始化，加载错词本...");
        loadWrongWordList();
        System.out.println("[WrongWordService] 初始化完成，当前错词数：" + wrongWordList.size());
    }

    /**
     * 添加错词（临时放开限制，先排查问题）
     */
    public void addWrongWord(Word word) {
        System.out.println("[addWrongWord] 收到添加请求，单词：" + (word != null ? word.getWord() : "null"));

        // 仅做空值校验，临时注释其他限制，确保能添加
        if (word == null) {
            System.out.println("[addWrongWord] 单词为空，跳过");
            return;
        }

        wrongWordList.add(word);
        System.out.println("[addWrongWord] 成功添加单词，当前错词数：" + wrongWordList.size());
        saveWrongWordList();
    }

    /**
     * 加载错词本
     */
    private void loadWrongWordList() {
        // 打印文件绝对路径，确认文件位置
        File file = new File(WRONG_WORD_PATH);
        System.out.println("[loadWrongWordList] 错词文件绝对路径：" + file.getAbsolutePath());
        System.out.println("[loadWrongWordList] 文件是否存在：" + file.exists());

        // 读取文件+空值保护
        String content = FileUtil.readFile(WRONG_WORD_PATH);
        System.out.println("[loadWrongWordList] 读取到的文件内容长度：" + (content != null ? content.length() : "null"));

        if (content == null || content.isBlank()) {
            this.wrongWordList = new ArrayList<>();
            System.out.println("[loadWrongWordList] 文件为空/不存在，初始化空列表");
            return;
        }
        // 解析单词列表
        this.wrongWordList = FileUtil.parseWordList(content);
        System.out.println("[loadWrongWordList] 解析完成，加载错词数：" + wrongWordList.size());
    }

    /**
     * 保存错词本到文件
     */
    private void saveWrongWordList() {
        System.out.println("[saveWrongWordList] 开始保存错词本，错词数：" + wrongWordList.size());

        // 格式和词库完全一致，用制表符\t分隔
        StringBuilder sb = new StringBuilder();
        for (Word word : wrongWordList) {
            sb.append(word.getWord()).append("\t")
                    .append(word.getPhonetic() == null ? "" : word.getPhonetic()).append("\t")
                    .append(word.getPartOfSpeech() == null ? "" : word.getPartOfSpeech()).append("\t")
                    .append(word.getMeaning() == null ? "" : word.getMeaning()).append("\t")
                    .append(word.getExampleEn() == null ? "" : word.getExampleEn()).append("\t")
                    .append(word.getExampleCn() == null ? "" : word.getExampleCn()).append("\n");
        }
        System.out.println("[saveWrongWordList] 待写入内容长度：" + sb.length());

        // 写入文件
        FileUtil.writeFile(WRONG_WORD_PATH, sb.toString());
        System.out.println("[saveWrongWordList] 保存完成，文件路径：" + new File(WRONG_WORD_PATH).getAbsolutePath());
    }

    /**
     * 获取所有错词
     */
    public List<Word> getWrongWordList() {
        System.out.println("[getWrongWordList] 被调用，返回错词数：" + wrongWordList.size());
        return new ArrayList<>(wrongWordList);
    }

    /**
     * 获取错词总数
     */
    public int getWrongWordCount() {
        return wrongWordList.size();
    }
}