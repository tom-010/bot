package io.deniffel.bot.chat.acceptance

import io.deniffel.bot.skyBot.Filesystem

import java.nio.file.Path

public class FSMock extends Filesystem {

    public List<Path> nextFilesResult = new LinkedList<>();
    public String nextFileReadResult = "";
    public boolean nextIsDirectory = false
    public boolean nextFileIsNotFound = false

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
