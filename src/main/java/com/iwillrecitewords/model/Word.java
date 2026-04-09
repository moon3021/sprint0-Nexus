package com.iwillrecitewords.model;

import java.util.Objects;

/**
 * 单词实体类：包含单词属性与业务行为，告别贫血模型
 */
public class Word {
    // 私有属性，封装数据，禁止外部直接修改
    private final String word;        // 单词（不可变，构造后不能修改）
    private final String phonetic;    // 音标
    private final String partOfSpeech;// 词性
    private final String meaning;     // 释义
    private final String exampleEn;   // 英文例句
    private final String exampleCn;   // 中文例句

    // 全参构造函数，创建时必须传入完整数据
    public Word(String word, String phonetic, String partOfSpeech, String meaning, String exampleEn, String exampleCn) {
        // 构造时数据校验，保证数据合法性
        this.word = word == null ? "" : word.trim();
        this.phonetic = phonetic == null ? "" : phonetic.trim();
        this.partOfSpeech = partOfSpeech == null ? "" : partOfSpeech.trim();
        this.meaning = meaning == null ? "" : meaning.trim();
        this.exampleEn = exampleEn == null ? "" : exampleEn.trim();
        this.exampleCn = exampleCn == null ? "" : exampleCn.trim();
    }

    // ====================== 核心业务行为方法 ======================
    /**
     * 验证单词数据是否有效
     */
    public boolean isValid() {
        return !word.isBlank() && !meaning.isBlank();
    }

    /**
     * 验证音标是否有效
     */
    public boolean hasPhonetic() {
        return !phonetic.isBlank();
    }

    /**
     * 验证是否有例句
     */
    public boolean hasExample() {
        return !exampleEn.isBlank() && !exampleCn.isBlank();
    }

    /**
     * 获取单词完整显示信息（用于UI展示）
     */
    public String getFullDisplayText() {
        StringBuilder sb = new StringBuilder();
        sb.append(word);
        if (hasPhonetic()) {
            sb.append(" ").append(phonetic);
        }
        sb.append("\n").append(partOfSpeech).append(" ").append(meaning);
        if (hasExample()) {
            sb.append("\n\n例句：").append(exampleEn).append("\n").append(exampleCn);
        }
        return sb.toString();
    }

    /**
     * 获取单词的纯文本格式（用于文件持久化）
     */
    public String toFileLine() {
        return word + "\t" + phonetic + "\t" + partOfSpeech + "\t" + meaning + "\t" + exampleEn + "\t" + exampleCn;
    }

    // ====================== 重写equals和hashCode（用于错词本去重） ======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    // ====================== 仅提供getter，不提供setter，保证数据不可变（线程安全） ======================
    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExampleEn() {
        return exampleEn;
    }

    public String getExampleCn() {
        return exampleCn;
    }

    @Override
    public String toString() {
        return getFullDisplayText();
    }
}