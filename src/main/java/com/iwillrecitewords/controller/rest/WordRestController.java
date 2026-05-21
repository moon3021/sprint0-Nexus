package com.iwillrecitewords.controller.rest;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/word")
public class WordRestController {

    // 直接复用你原有单例，和桌面端共享同一个服务实例
    private final WordService wordService = MainUI.WORD_SERVICE;

    // 获取随机单词（背单词核心接口）
    @GetMapping("/random")
    public Word getRandomWord() {
        return wordService.getRandomWord();
    }

    // 获取词库总单词数
    @GetMapping("/count")
    public int getTotalWordCount() {
        return wordService.getTotalWordCount();
    }
}