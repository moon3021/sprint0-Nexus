package com.iwillrecitewords.repository;

import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.util.FileUtil;
import java.util.List;
import java.util.ArrayList;

public class FileWordRepository implements WordRepository {
    private static final String WRONG_FILE = "wrong_words.txt";

    @Override
    public List<Word> loadAllWords() {
        return FileUtil.loadWordLibrary();
    }

    // ✅ 修复：读取错词（兼容无音标/例句格式）
    @Override
    public List<Word> loadWrongWords() {
        List<Word> list = new ArrayList<>();
        String content = FileUtil.readFile(WRONG_FILE);
        if (content.isBlank()) return list;

        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\t");
            String word = parts.length>0 ? parts[0] : "";
            String pos = parts.length>2 ? parts[2] : "";
            String mean = parts.length>3 ? parts[3] : "";
            list.add(new Word(word, "", pos, mean, "", ""));
        }
        return list;
    }

    // ✅ 修复：保存错词（兼容无音标/例句格式）
    @Override
    public void saveWrongWords(List<Word> wrongWords) {
        StringBuilder sb = new StringBuilder();
        for (Word w : wrongWords) {
            sb.append(w.getWord()).append("\t\t")
                    .append(w.getPartOfSpeech()).append("\t")
                    .append(w.getMeaning()).append("\t\t\n");
        }
        FileUtil.writeFile(WRONG_FILE, sb.toString());
    }

    @Override
    public void addWrongWord(Word word) {
        List<Word> list = loadWrongWords();
        if (!list.contains(word)) {
            list.add(word);
            saveWrongWords(list);
        }
    }
}