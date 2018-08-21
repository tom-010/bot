package io.deniffel.bot.skyBot

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Filesystem {

    List<Path> filesInFolder(String path) {
        try {
            return Files.walk(Paths.get(path)).collect()
        } catch (Exception e) {
            println e.message
            return []
        }
    }

    String read(Path path) {
        String result = "";
        Files.readAllLines(path).forEach{ result += it + "\n" }
        return result
    }

    boolean isDirectory(Path path) {
        return Files.isDirectory(path)
    }
}