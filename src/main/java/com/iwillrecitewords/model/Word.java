package com.iwillrecitewords.model;

/**
 * 单词实体类，完整匹配红宝书词库字段
 */
public class Word {
    private final String word;          // 单词
    private final String phonetic;      // 音标
    private final String partOfSpeech;  // 词性
    private final String meaning;       // 中文释义
    private final String exampleEn;     // 英文例句
    private final String exampleCn;     // 例句翻译

    public Word(String word, String phonetic, String partOfSpeech, String meaning, String exampleEn, String exampleCn) {
        this.word = word;
        this.phonetic = phonetic;
        this.partOfSpeech = partOfSpeech;
        this.meaning = meaning;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
    }
    public boolean isValid(){
        return word != null && !word.isBlank()
                && meaning != null && !meaning.isBlank();
    }

    // Getter 方法
    public String getWord() { return word; }
    public String getPhonetic() { return phonetic; }
    public String getPartOfSpeech() { return partOfSpeech; }
    public String getMeaning() { return meaning; }
    public String getExampleEn() { return exampleEn; }
    public String getExampleCn() { return exampleCn; }

    // 🔥 新增：重写equals和hashCode，实现按单词内容去重
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        // 以单词内容作为唯一判断标准
        return word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}