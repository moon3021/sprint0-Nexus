package com.iwillrecitewords.repository;

import com.iwillrecitewords.util.FileUtil;

/**
 * 文件存储实现：统计仓储的文件实现类
 */
public class FileStatRepository implements StatRepository {

    @Override
    public void saveLearnedCount(int count) {
        FileUtil.saveLearnedCount(count);
    }

    @Override
    public int loadLearnedCount() {
        return FileUtil.loadLearnedCount();
    }
}