package com.iwillrecitewords.controller.rest;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.model.WrongWord;
import com.iwillrecitewords.service.WrongWordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wrong-word")
public class WrongWordRestController {

    // 直接复用你原有单例，和桌面端共享同一个错词本
    private final WrongWordService wrongWordService = MainUI.WRONG_WORD_SERVICE;
    private final Long DEFAULT_USER_ID = 1L;

    // 添加错词（接收Word对象，和桌面端逻辑完全一致）
    @PostMapping("/add")
    public String addWrongWord(@RequestBody Word word) {
        return wrongWordService.addWrongWord(word);
    }

    // 获取错词列表（返回Word对象，和桌面端格式一致）
    @GetMapping("/list")
    public List<Word> getWrongWordList() {
        return wrongWordService.getWrongWordList();
    }

    // 删除错词（根据ID）
    @DeleteMapping("/delete/{id}")
    public String deleteWrongWord(@PathVariable Long id) {
        return wrongWordService.deleteWrongWord(id);
    }

    // 清空错词本
    @DeleteMapping("/clear")
    public String clearWrongWords() {
        return wrongWordService.clearWrongWords(DEFAULT_USER_ID);
    }

    // 获取错词总数
    @GetMapping("/count")
    public int getWrongWordCount() {
        return wrongWordService.getWrongWordCount();
    }
}