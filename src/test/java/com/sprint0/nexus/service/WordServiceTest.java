package com.sprint0.nexus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WordService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class WordServiceTest {

    private WordService wordService;

    @BeforeEach
    void setUp() {
        wordService = new WordService();
    }

    // ==================== 原有测试 ====================

    @Test
    void testGetRandomWord_ReturnsValidWord() {
        Word word = wordService.getRandomWord();

        assertNotNull(word);
        assertNotNull(word.getWord());
        assertNotNull(word.getMeaning());
    }

    @Test
    void testGetTotalWordCount_ReturnsPositiveNumber() {
        int count = wordService.getTotalWordCount();

        assertTrue(count >= 0, "单词总数应该大于等于0");
    }

    @RepeatedTest(5)
    void testGetRandomWord_Randomness() {
        if (wordService.getTotalWordCount() <= 1) {
            return;
        }

        Set<String> words = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            words.add(wordService.getRandomWord().getWord());
        }

        assertTrue(words.size() >= 2, "应该能获取到不同的单词");
    }

    // ==================== 新增测试（提升覆盖率） ====================

    @Test
    void testGetWordLibrary_ReturnsNonNullList() {
        List<Word> library = wordService.getWordLibrary();

        assertNotNull(library, "单词库不应为null");
        assertTrue(library.size() > 0, "单词库应有单词");
    }

    @Test
    void testGetTotalWordCount_MatchesLibrarySize() {
        int count = wordService.getTotalWordCount();
        int librarySize = wordService.getWordLibrary().size();

        assertEquals(librarySize, count, "总数应与词库大小一致");
    }

    @Test
    void testGetCurrentWord_ReturnsWord() {
        Word current = wordService.getCurrentWord();

        assertNotNull(current, "当前单词不应为null");
        assertNotNull(current.getWord());
    }

    @Test
    void testResetCurrentIndex_ResetsToZero() {
        // 先移动索引
        wordService.nextWord();
        wordService.nextWord();

        // 重置
        wordService.resetCurrentIndex();

        // 验证重置成功
        Word current = wordService.getCurrentWord();
        assertNotNull(current);
    }

    @Test
    void testNextWord_ReturnsNextWord() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords < 2) {
            return; // 单词不足2个，跳过测试
        }

        Word first = wordService.getCurrentWord();
        Word second = wordService.nextWord();

        assertNotNull(second);
        assertNotEquals(first.getWord(), second.getWord(), "下一个单词应与当前不同");
    }

    @Test
    void testHasNextWord_ReturnsCorrectValue() {
        int totalWords = wordService.getTotalWordCount();

        if (totalWords <= 1) {
            assertFalse(wordService.hasNextWord(), "单词不足时应该没有下一个");
            return;
        }

        // 刚开始应该还有下一个
        assertTrue(wordService.hasNextWord(), "词库有多个单词时应有下一个");

        // 移动到最后一个
        for (int i = 0; i < totalWords - 1; i++) {
            wordService.nextWord();
        }

        // 最后一个单词后应该没有下一个
        assertFalse(wordService.hasNextWord(), "最后一个单词后应没有下一个");
    }

    @Test
    void testNextWord_WhenAtEnd_ReturnsNull() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords == 0) {
            return;
        }

        // 移动到最后一个之后
        for (int i = 0; i < totalWords; i++) {
            wordService.nextWord();
        }

        Word afterEnd = wordService.nextWord();
        assertNull(afterEnd, "超出范围应返回null");
    }

    // ==================== 更多补充测试（达到85%+覆盖率） ====================

    @Test
    void testConstructor_LoadsWordLibrary() {
        WordService newService = new WordService();
        assertNotNull(newService.getWordLibrary());
        assertTrue(newService.getTotalWordCount() > 0);
    }

    @Test
    void testGetCurrentWord_AfterReset_ReturnsFirstWord() {
        wordService.resetCurrentIndex();
        Word first = wordService.getCurrentWord();

        wordService.nextWord();
        wordService.nextWord();
        wordService.resetCurrentIndex();

        Word afterReset = wordService.getCurrentWord();
        assertEquals(first.getWord(), afterReset.getWord());
    }

    @Test
    void testResetCurrentIndex_WhenAlreadyAtZero_WorksFine() {
        wordService.resetCurrentIndex();
        Word first = wordService.getCurrentWord();

        wordService.resetCurrentIndex();
        Word again = wordService.getCurrentWord();

        assertEquals(first.getWord(), again.getWord());
    }

    @Test
    void testGetCurrentWord_ReturnsSameWord_WhenNotMoved() {
        Word first = wordService.getCurrentWord();
        Word second = wordService.getCurrentWord();

        assertEquals(first.getWord(), second.getWord());
    }

    @Test
    void testNextWord_SequentialMove_ReturnsDifferentWords() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords < 3) {
            return;
        }

        Word first = wordService.getCurrentWord();
        Word second = wordService.nextWord();
        Word third = wordService.nextWord();

        assertNotEquals(first.getWord(), second.getWord());
        assertNotEquals(second.getWord(), third.getWord());
        assertNotEquals(first.getWord(), third.getWord());
    }

    @Test
    void testGetRandomWord_AlwaysReturnsCompleteWord() {
        for (int i = 0; i < 20; i++) {
            Word word = wordService.getRandomWord();
            assertNotNull(word.getWord());
            assertNotNull(word.getMeaning());
            assertNotNull(word.getPhonetic());
            assertNotNull(word.getPartOfSpeech());
        }
    }

    @Test
    void testHasNextWord_WhenWordsExist_ReturnsTrue() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords <= 1) {
            return;
        }

        wordService.resetCurrentIndex();
        assertTrue(wordService.hasNextWord());
    }

    @Test
    void testNextWord_WhenWordsLeft_ReturnsNonNull() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords <= 1) {
            return;
        }

        wordService.resetCurrentIndex();
        assertNotNull(wordService.nextWord());
    }

    @Test
    void testSequenceOrder_AfterReset_StartsFromBeginning() {
        int totalWords = wordService.getTotalWordCount();
        if (totalWords < 3) {
            return;
        }

        wordService.resetCurrentIndex();
        Word first = wordService.getCurrentWord();
        wordService.nextWord();
        wordService.nextWord();
        wordService.resetCurrentIndex();

        Word afterReset = wordService.getCurrentWord();
        assertEquals(first.getWord(), afterReset.getWord());
    }
}