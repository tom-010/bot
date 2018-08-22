package io.deniffel.bot.skyBot

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

class PluginManager {

    Map<String, String> scripts = [:]
    private Filesystem fs
    private String basePath
    private Long lastModifiedStamp = -1

    PluginManager(String basePath, Filesystem fs = new Filesystem()) {
        this.fs = fs
        this.basePath = basePath
    }

    static PluginManager build(String basePath, Filesystem fs = new Filesystem()) {
        def manager = new PluginManager(basePath, fs)
        manager.init()
        return manager
    }

    boolean methodExists(obj, String method) {
        return obj.metaClass.respondsTo(obj, method).size() > 0
    }

    boolean hasPluginDirectoryChanged() {
        boolean result = lastModifiedStamp != Paths.get(basePath).toFile().lastModified();
        if(result)
            lastModifiedStamp = Paths.get(basePath).toFile().lastModified()
        return result
    }

    def init() {

        fs.filesInFolder(basePath).forEach {
            try {
                if(fs.isDirectory(it))
                    return

                def regexes = null;

                String script = fs.read(it);
                if(script == null)
                    return

                new GroovyShell().parse(script).with {
                    if(!methodExists(it, "answer") || !methodExists(it, "activatorRegexes"))
                        return

                    regexes = activatorRegexes()
                }

                if(regexes != null) {

                    try {
                        ((List<String>) regexes).forEach { regex ->
                            if(!(regex instanceof String))
                                return
                            scripts[regex] = it.toAbsolutePath().toString()
                        }
                    } catch (Exception ignored) {
                        // in the case the script does not return
                        // a valid list of regexes, skip the file
                    }

                }

        }
        catch(Exception e) {
            println e.message
        }

        }
    }

    def answer(String message, Map<String, String> ctx = [:]) {
        def result = null, context = null

        scripts.forEach { activatorRegex, file ->
            if(!message.matches(activatorRegex))
                return

            try {
                def script = fs.read(Paths.get(file))

                new GroovyShell().parse(script).with {
                    (result, context) = answer(message, ctx)

                    if(!(context instanceof Map)) {
                        result = null
                        context = ctx
                    }
                }
            } catch (Exception e) {
                // was not able to parse the return values
                println e.message
                result = null
                context = ctx
            }
        }
        if(context == null)
            context = ctx

        if(result != null)
            result = result.toString()

        return [result, context]
    }
}
