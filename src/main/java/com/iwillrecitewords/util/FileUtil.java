package com.iwillrecitewords.util;

import com.iwillrecitewords.model.Word;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类：零报错版，处理词库读取、学习数据持久化
 */
public class FileUtil {
    // 统计文件存储路径：放在用户目录下，避免权限问题
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String STAT_FOLDER = USER_DIR + "/IWillReciteWordsData";
    private static final String STAT_FILE = STAT_FOLDER + "/study_stat.txt";

    /**
     * 从resources读取Words.txt词库（适配制表符分隔，零报错）
     */
    public static List<Word> loadWordLibrary() {
        List<Word> wordList = new ArrayList<>();
        // 使用类加载器获取资源流，兼容IDE运行和打包后运行
        try (InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream("Words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (inputStream == null) {
                System.err.println("❌ 错误：未找到词库文件 Words.txt，请确保文件在 src/main/resources 目录下");
                return wordList;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 跳过空行和注释行
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 适配你的格式：单词\t词性. 释义（制表符分隔）
                String[] parts = line.split("\t", 2);
                if (parts.length < 2) {
                    continue; // 跳过格式不对的行
                }

                String wordText = parts[0].trim();
                String posAndMeaning = parts[1].trim();

                // 提取词性和释义
                String partOfSpeech = "";
                String meaning = posAndMeaning;

                // 尝试识别常见词性前缀
                if (posAndMeaning.length() > 2) {
                    String prefix = posAndMeaning.substring(0, Math.min(5, posAndMeaning.length()));
                    if (prefix.startsWith("n.") || prefix.startsWith("v.")
                            || prefix.startsWith("adj.") || prefix.startsWith("adv.")
                            || prefix.startsWith("prep.") || prefix.startsWith("conj.")
                            || prefix.startsWith("pron.") || prefix.startsWith("num.")) {
                        int firstSpace = posAndMeaning.indexOf(" ");
                        if (firstSpace > 0) {
                            partOfSpeech = posAndMeaning.substring(0, firstSpace).trim();
                            meaning = posAndMeaning.substring(firstSpace).trim();
                        }
                    }
                }

                // 构建单词对象
                Word word = new Word(
                        wordText,
                        "", // 音标留空
                        partOfSpeech,
                        meaning,
                        "", // 英文例句留空
                        ""  // 中文例句留空
                );
                wordList.add(word);
            }

            System.out.println("✅ 词库加载成功，共加载 " + wordList.size() + " 个单词");

        } catch (IOException e) {
            System.err.println("❌ 词库读取失败：" + e.getMessage());
        }

        return wordList;
    }
    /**
     * 读取文件内容
     * @param filePath 文件路径
     * @return 文件内容字符串，文件不存在/读取失败返回空字符串
     */
    public static String readFile(String filePath) {
        File file = new File(filePath);
        // 文件不存在，先创建空文件
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        // 读取文件内容
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 写入内容到文件
     * @param filePath 文件路径
     * @param content 要写入的内容
     */
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析文本内容为单词列表
     * @param content 词库文本内容，一行一个单词，制表符分隔
     * @return 解析后的单词列表
     */
    public static List<Word> parseWordList(String content) {
        List<Word> wordList = new ArrayList<>();
        if (content == null || content.isBlank()) {
            return wordList;
        }
        // 按行分割，逐行解析
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.isBlank()) continue;
            // 按制表符\t分割，和你的词库格式保持一致
            String[] parts = line.split("\t");
            // 确保字段数量足够，避免数组越界
            if (parts.length < 6) continue;
            Word word = new Word(
                    parts[0].trim(), // 单词
                    parts[1].trim(), // 音标
                    parts[2].trim(), // 词性
                    parts[3].trim(), // 释义
                    parts[4].trim(), // 英文例句
                    parts[5].trim()  // 中文例句
            );
            wordList.add(word);
        }
        return wordList;
    }
    /**
     * 保存已学单词数量到本地（零报错，自动创建目录）
     */
    public static void saveLearnedCount(int count) {
        try {
            // 自动创建数据目录
            Path statFolderPath = Paths.get(STAT_FOLDER);
            if (!Files.exists(statFolderPath)) {
                Files.createDirectories(statFolderPath);
            }
            // 写入统计数据
            Files.writeString(Paths.get(STAT_FILE), String.valueOf(count), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("❌ 学习数据保存失败：" + e.getMessage());
        }
    }

    /**
     * 从本地文件读取已学单词数量（零报错，默认返回0）
     */
    public static int loadLearnedCount() {
        try {
            Path statFilePath = Paths.get(STAT_FILE);
            if (Files.exists(statFilePath)) {
                String content = Files.readString(statFilePath, StandardCharsets.UTF_8).trim();
                return Integer.parseInt(content);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("⚠️ 学习数据读取失败，使用默认值 0：" + e.getMessage());
        }
        return 0;
    }
}