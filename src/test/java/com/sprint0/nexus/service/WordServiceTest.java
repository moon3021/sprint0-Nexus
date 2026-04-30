package com.sprint0.nexus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.service.WordService;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class WordServiceTest {
    
    private WordService wordService;
    
    @BeforeEach
    void setUp() {
        wordService = new WordService();
    }
    
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
}