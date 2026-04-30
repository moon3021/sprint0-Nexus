package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.model.WrongWord;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WrongWordService {
    // 单例实例
    private static WrongWordService instance;
    private final List<WrongWord> wrongWordList;
    private Long idCounter;

    // 公共构造方法，兼容MainUI的new调用
    public WrongWordService() {
        wrongWordList = new ArrayList<>();
        idCounter = 1L;
    }

    // 单例获取方法（保留，后续可复用）
    public static WrongWordService getInstance() {
        if (instance == null) {
            instance = new WrongWordService();
        }
        return instance;
    }

    // 🔥 核心修复：添加错词（直接接收Word类，用getter获取字段）
    public String addWrongWord(Word word) {
        // 前置校验1：单词无效直接跳过
        if (word == null || !word.isValid()) {
            return "单词无效，添加失败";
        }

        // 前置校验2：去重判断（核心修复）
        // 遍历当前用户的错词列表，对比单词内容，已存在则直接返回，不添加
        for (WrongWord existing : wrongWordList) {
            if (existing.getUserId().equals(1L) && existing.getWord().equals(word.getWord())) {
                return "单词已在错词本中，无需重复添加";
            }
        }

        // 只有通过校验，才真正添加
        WrongWord wrongWord = new WrongWord(
                idCounter++,
                1L, // 默认用户ID=1
                word.getWord(),
                word.getMeaning(),
                LocalDateTime.now().toString()
        );
        wrongWordList.add(wrongWord);
        return "添加错词成功";
    }

    // 添加错词（原WrongWord类型，用getter获取字段）
    public String addWrongWord(WrongWord wrongWord) {
        wrongWordList.add(new WrongWord(
                idCounter++,
                wrongWord.getUserId(),
                wrongWord.getWord(),
                wrongWord.getMeaning(),
                wrongWord.getCreateTime()
        ));
        return "添加错词成功";
    }

    // 【关键修复】获取错词列表（用getter访问私有字段）
    public List<Word> getWrongWordList() {
        List<Word> wordList = new ArrayList<>();
        // 过滤当前用户错词（默认用户ID=1）
        for (WrongWord wrongWord : wrongWordList) {
            if (wrongWord.getUserId().equals(1L)) {
                // 用Word的全参构造，通过getter获取错词字段
                wordList.add(new Word(
                        wrongWord.getWord(),
                        "", // phonetic（错词表不保存音标）
                        "", // partOfSpeech（错词表不保存词性）
                        wrongWord.getMeaning(), // 正确的meaning位置
                        "",
                        ""
                ));
            }
        }
        return wordList;
    }

    // 根据用户ID查询错词（用getter访问）
    public List<WrongWord> getWrongWordsByUserId(Long userId) {
        List<WrongWord> list = new ArrayList<>();
        for (WrongWord wrongWord : wrongWordList) {
            if (wrongWord.getUserId().equals(userId)) {
                list.add(wrongWord);
            }
        }
        return list;
    }

    // 删除错词（用getter访问id）
    public String deleteWrongWord(Long id) {
        wrongWordList.removeIf(word -> word.getId().equals(id));
        return "删除错词成功";
    }

    // 清空错词本（用getter访问userId）
    public String clearWrongWords(Long userId) {
        wrongWordList.removeIf(word -> word.getUserId().equals(userId));
        return "清空错词本成功";
    }

    // 【关键补全】获取用户错词总数（用getter访问userId）
    public int getWrongWordCount() {
        int count = 0;
        for (WrongWord wrongWord : wrongWordList) {
            if (wrongWord.getUserId().equals(1L)) {
                count++;
            }
        }
        return count;
    }

    // 根据ID查询错词（用getter访问id）
    public WrongWord getWrongWordById(Long id) {
        for (WrongWord wrongWord : wrongWordList) {
            if (wrongWord.getId().equals(id)) {
                return wrongWord;
            }
        }
        return null;
    }
}