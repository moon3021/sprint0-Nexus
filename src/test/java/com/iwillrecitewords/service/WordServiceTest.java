package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordServiceTest {

    private WordService wordService;

    @BeforeEach
    void setUp() {
        // 每个测试前创建新实例，保证测试独立性
        wordService = new WordService();
    }

    @Test
    void testGetRandomWord_ShouldReturnValidWord() {
        // 验证：随机返回的单词所有核心字段都有效
        Word word = wordService.getRandomWord();
        assertNotNull(word);
        assertTrue(word.isValid());
        assertNotNull(word.getWord());
        assertFalse(word.getWord().isBlank());
        assertNotNull(word.getMeaning());
        assertFalse(word.getMeaning().isBlank());
    }

    @Test
    void testGetTotalWordCount_ShouldBePositive() {
        // 验证：红宝书词库总数大于0
        int count = wordService.getTotalWordCount();
        assertTrue(count > 0, "词库总数应大于0");
        assertEquals(count, wordService.getWordLibrary().size(), "总数应与词库列表大小一致");
    }

    @Test
    void testSequentialLearningFlow_ShouldWorkCorrectly() {
        // 验证：顺序学习完整流程
        wordService.resetCurrentIndex();

        // 初始状态：有下一个单词，当前单词是第一个
        assertTrue(wordService.hasNextWord());
        Word firstWord = wordService.getCurrentWord();
        assertNotNull(firstWord);

        // 切换到下一个单词
        Word secondWord = wordService.nextWord();
        assertNotNull(secondWord);
        assertNotEquals(firstWord.getWord(), secondWord.getWord(), "下一个单词应与当前不同");

        // 遍历到最后一个单词
        Word lastWord = null;
        while (wordService.hasNextWord()) {
            lastWord = wordService.nextWord();
        }
        assertNotNull(lastWord);
        assertNull(wordService.nextWord(), "遍历完成后应返回null");
    }

    @Test
    void testGetWordLibrary_ShouldReturnUnmodifiableView() {
        // 验证：获取的词库列表与内部列表一致
        assertEquals(wordService.getTotalWordCount(), wordService.getWordLibrary().size());
        // 验证：列表中的每个单词都有效
        for (Word word : wordService.getWordLibrary()) {
            assertTrue(word.isValid());
        }
    }
}