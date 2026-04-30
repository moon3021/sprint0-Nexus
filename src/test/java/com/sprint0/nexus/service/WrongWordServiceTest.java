package com.sprint0.nexus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WrongWordService;
import static org.junit.jupiter.api.Assertions.*;

class WrongWordServiceTest {
    
    private WrongWordService wrongWordService;
    private Word testWord;
    
    @BeforeEach
    void setUp() {
        wrongWordService = new WrongWordService();
        testWord = new Word("apple", "/ˈæpl/", "n.", "苹果", 
            "I eat an apple", "我吃一个苹果");
    }
    
    @Test
    void testAddWrongWord_WithValidWord_AddsSuccessfully() {
        int initialCount = wrongWordService.getWrongWordCount();
        
        wrongWordService.addWrongWord(testWord);
        
        assertEquals(initialCount + 1, wrongWordService.getWrongWordCount());
    }
    
    @Test
    void testAddWrongWord_WithNullWord_DoesNotAdd() {
        int initialCount = wrongWordService.getWrongWordCount();
        
        wrongWordService.addWrongWord(null);
        
        assertEquals(initialCount, wrongWordService.getWrongWordCount());
    }
    
    @Test
    void testGetWrongWordList_ReturnsList() {
        assertNotNull(wrongWordService.getWrongWordList());
    }
}