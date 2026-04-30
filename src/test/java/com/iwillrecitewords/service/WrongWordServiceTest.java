package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.model.WrongWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WrongWordServiceTest {

    private WrongWordService wrongWordService;
    private Word validWord;
    private Word invalidWord;

    @BeforeEach
    void setUp() {
        // 每个测试前创建新实例，清空错词本
        wrongWordService = new WrongWordService();
        validWord = new Word("test", "/test/", "v.", "测试", "This is a test.", "这是一个测试");
        invalidWord = new Word("", "", "", "", "", ""); // isValid()返回false
    }

    @Test
    void testAddValidWord_ShouldSucceed() {
        // 验证：添加有效单词返回成功，数量+1
        String result = wrongWordService.addWrongWord(validWord);
        assertEquals("添加错词成功", result);
        assertEquals(1, wrongWordService.getWrongWordCount());
    }

    @Test
    void testAddNullWord_ShouldFail() {
        // 验证：添加null返回失败，数量不变
        String result = wrongWordService.addWrongWord((Word) null);
        assertEquals("单词无效，添加失败", result);
        assertEquals(0, wrongWordService.getWrongWordCount());
    }

    @Test
    void testAddInvalidWord_ShouldFail() {
        // 验证：添加无效单词返回失败，数量不变
        String result = wrongWordService.addWrongWord(invalidWord);
        assertEquals("单词无效，添加失败", result);
        assertEquals(0, wrongWordService.getWrongWordCount());
    }

    @Test
    void testAddDuplicateWord_ShouldReturnAlreadyExists() {
        // 验证：重复添加同一单词返回已存在，数量不变
        wrongWordService.addWrongWord(validWord);
        String result = wrongWordService.addWrongWord(validWord);
        assertEquals("单词已在错词本中，无需重复添加", result);
        assertEquals(1, wrongWordService.getWrongWordCount());
    }

    @Test
    void testAddWrongWordObject_ShouldSucceed() {
        // 验证：添加WrongWord类型对象返回成功，数量+1
        WrongWord wrongWord = new WrongWord(1L, 1L, "apple", "苹果", "2026-04-30");
        String result = wrongWordService.addWrongWord(wrongWord);
        assertEquals("添加错词成功", result);
        assertEquals(1, wrongWordService.getWrongWordCount());
    }

    @Test
    void testGetWrongWordList_ShouldReturnCorrectWords() {
        // 验证：获取的错词列表内容正确
        wrongWordService.addWrongWord(validWord);
        assertEquals(1, wrongWordService.getWrongWordList().size());
        Word returnedWord = wrongWordService.getWrongWordList().get(0);
        assertEquals(validWord.getWord(), returnedWord.getWord());
        assertEquals(validWord.getMeaning(), returnedWord.getMeaning());
    }

    @Test
    void testDeleteWrongWord_ShouldSucceed() {
        // 验证：删除存在的错词返回成功，数量-1
        wrongWordService.addWrongWord(validWord);
        Long id = wrongWordService.getWrongWordsByUserId(1L).get(0).getId();

        String result = wrongWordService.deleteWrongWord(id);
        assertEquals("删除错词成功", result);
        assertEquals(0, wrongWordService.getWrongWordCount());
    }

    @Test
    void testClearWrongWords_ShouldEmptyList() {
        // 验证：清空错词本返回成功，数量为0
        wrongWordService.addWrongWord(validWord);
        wrongWordService.addWrongWord(new Word("apple", "/ˈæpl/", "n.", "苹果", "", ""));

        String result = wrongWordService.clearWrongWords(1L);
        assertEquals("清空错词本成功", result);
        assertEquals(0, wrongWordService.getWrongWordCount());
    }

    @Test
    void testGetWrongWordById_ShouldReturnCorrectWord() {
        // 验证：根据ID查询错词返回正确结果
        wrongWordService.addWrongWord(validWord);
        Long id = wrongWordService.getWrongWordsByUserId(1L).get(0).getId();

        WrongWord found = wrongWordService.getWrongWordById(id);
        assertNotNull(found);
        assertEquals(validWord.getWord(), found.getWord());
        assertNull(wrongWordService.getWrongWordById(999L), "不存在的ID应返回null");
    }

    @Test
    void testGetInstance_ShouldReturnSingleton() {
        // 验证：单例模式正常工作
        WrongWordService instance1 = WrongWordService.getInstance();
        WrongWordService instance2 = WrongWordService.getInstance();
        assertSame(instance1, instance2, "单例应返回同一实例");
    }
}