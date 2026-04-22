package com.iwillrecitewords.service;

import com.iwillrecitewords.model.Word;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WrongWordServiceTest {

    @Test
    public void testAddDuplicateWrongWord_ShouldOnlyKeepOne() {
        // 1. 准备测试数据
        WrongWordService service = new WrongWordService();
        Word testWord = new Word("test", "/test/", "v.", "测试", "This is a test.", "这是一个测试。");

        // 2. 执行重复添加
        service.addWrongWord(testWord);
        service.addWrongWord(testWord);

        // 3. 验证结果：错词数应为1，而非2
        assertEquals(1, service.getWrongWordCount(), "重复添加同一单词时，错词本应仅保留一条记录");
    }

    @Test
    public void testLoadEmptyWrongWordFile_ShouldInitializeEmptyList() {
        WrongWordService service = new WrongWordService();

        // 验证空列表初始化正常
        assertNotNull(service.getWrongWordList(), "错词列表不应为null");
        assertTrue(service.getWrongWordList().isEmpty(), "空文件加载后，错词列表应为空");
    }
}