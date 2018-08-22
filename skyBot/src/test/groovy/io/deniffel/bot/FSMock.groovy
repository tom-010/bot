package io.deniffel.bot

import io.deniffel.bot.skyBot.Filesystem

import java.nio.file.Path

class FSMock extends Filesystem {

    List<Path> nextFilesResult = new LinkedList<>();
    public String nextFileReadResult = "";
    boolean nextIsDirectory = false
    boolean nextFileIsNotFound = false

    @Override
    List<Path> filesInFolder(String path) {
        return nextFilesResult;
    }

    @Override
    String read(Path path) {
        if(nextFileIsNotFound)
            throw new FileNotFoundException()
        return nextFileReadResult;
    }

    @Override
    boolean isDirectory(Path path) {
        return nextIsDirectory
    }
}
