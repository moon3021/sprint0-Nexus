package com.iwillrecitewords.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 首页：背单词页面
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 错词本页面
    @GetMapping("/wrong-words")
    public String wrongWords() {
        return "wrong-words";
    }
}