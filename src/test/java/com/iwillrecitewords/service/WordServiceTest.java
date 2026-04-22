package com.iwillrecitewords.service;

// 必须导入你项目里的Word实体类！这是解决报错的核心
import com.iwillrecitewords.model.Word;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordServiceTest {

    @Test
    public void testLoadWordLibrary_WordFieldsShouldBeValid() {
        // 1. 初始化服务
        WordService service = new WordService();

        // 2. 获取随机单词
        Word randomWord = service.getRandomWord();

        // 3. 验证核心字段不为空
        assertNotNull(randomWord, "随机单词不应为null");
        assertNotNull(randomWord.getWord(), "单词内容不应为null");
        assertFalse(randomWord.getWord().isBlank(), "单词内容不应为空字符串");
    }
}