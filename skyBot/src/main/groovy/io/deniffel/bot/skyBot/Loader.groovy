package io.deniffel.bot.skyBot

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

class FileSystem {
    List<Path> filesInFolder(String path) {
        return Files.walk(Paths.get(path)).collect()
    }
}

class PluginManager {

    protected Map<String, String> scripts = [:]
    private FileSystem fs

    private PluginManager(FileSystem fs = new FileSystem()) {
        this.fs = fs
    }

    static PluginManager build() {
        def manager = new PluginManager()
        manager.init()
        return manager
    }

    def filename() {
        return new File(getClass().protectionDomain.codeSource.location.path).getName();
    }

    def init() {
        new FileSystem().filesInFolder('.').forEach{
            if(it.fileName.toString() == filename() || Files.isDirectory(it))
                return

            def regexes = null;
            new GroovyShell().parse(it.toFile()).with {
                 regexes = activatorRegexes()
            }

            if(regexes != null) {
                regexes.forEach { regex ->
                    scripts[regex] = it.toAbsolutePath().toString()
                }
            }
        }
    }

    String answer(String message, Map<String, String> ctx = [:]) {

        def result, context

        scripts.forEach { activatorRegex, file ->
            if(!message.matches(activatorRegex))
                return

            new GroovyShell().parse(new File(file)).with {
                (result, context) = answer(message)
            }
        }

        return result
    }

}
println PluginManager.build().answer("Hello Tom");
