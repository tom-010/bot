import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class PluginManager {

    Map<String, String> scripts = [:];

    private PluginManager() { }

    static PluginManager build() {
        def manager = new PluginManager()
        manager.init()
        return manager
    }

    def filename() {
        return new File(getClass().protectionDomain.codeSource.location.path).getName();
    }

    def init() {
        Files.walk(Paths.get('.')).forEach{
            if(it.fileName.toString() == filename() || Files.isDirectory(it))
                return

            def regex = null;
            new GroovyShell().parse(it.toFile()).with {
                 regex = activatorRegex()
            }

            if(regex != null) {
                scripts[it.toAbsolutePath().toString()] = regex
            }
        }
    }

    String answer(String message, Map<String, String> ctx = [:]) {

        def result, context

        scripts.forEach { file, activatorRegex ->
            if(!message.matches(activatorRegex))
                return;

            new GroovyShell().parse(new File(file)).with {
                (result, context) = answer(message)
            }
        }

        return result
    }

}
println PluginManager.build().answer("Hello Tom");
