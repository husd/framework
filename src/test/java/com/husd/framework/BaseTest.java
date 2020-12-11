package com.husd.framework;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BaseTest {

    public List<String> loadResource(String path) {

        String fileName = this.getClass().getClassLoader().getResource(path).getPath();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }
}
