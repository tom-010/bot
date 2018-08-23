package io.deniffel.bot

import io.deniffel.bot.skyBot.PluginManager
import org.junit.Before
import org.junit.Test

import java.nio.file.Paths

import static org.junit.Assert.*;

class PluginManagerTest {

    PluginManager uut;
    FSMock fs;

    @Before
    void setUp() {
        fs = new FSMock();
        uut = PluginManager.build(".", fs);
        uut.refresh()
    }

    @Test
    void emptyScript_scriptIgnored() {
        nextScript("") // empty script
        uut.answer("message")
        assertEquals(0, uut.scripts.size() )
    }

    @Test
    void noRegexMethod_scriptIgnored() {
        nextScript('''
        def answer(String incomingMessage, Map<String, Object> context = [:]) {}
        ''')

        assertEquals(0, uut.scripts.size() )
    }

    @Test
    void noAnwerMethod_scriptIgnored() {
        nextScript('''
        def activatorRegexes() { return [".*"] }
        ''')

        assertEquals(0, uut.scripts.size() );
    }

    @Test
    void answerAndRegexMethodPresent_scriptAdded() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return [m, ctx] }
        def activatorRegexes() { return ["Hello.*"]; }
        ''')

        assertEquals(1, uut.scripts.size())
    }

    @Test
    void skipsDirectory() {
        fs.nextFileReadResult = '''
        def answer(String m, Map<String, Object> ctx = [:]) { return [m, ctx] }
        def activatorRegexes() { return ["Hello.*"]; }
        '''
        fs.nextFilesResult.add(Paths.get("./directory"))
        fs.nextIsDirectory = true
        uut.refresh()

        assertEquals(0, uut.scripts.size())
    }

    @Test
    void groovyCodeNotValid_skipFile() {
        nextScript('''
        invalid groovy code here
        ''')

        assertEquals(0, uut.scripts.size() );
    }

    @Test
    void invalidRegexResult_skipFile() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return [m, ctx] }
        def activatorRegexes() { return "string is invalid" }
        ''')

        assertEquals(0, uut.scripts.size())
    }

    @Test
    void nullAsRegexResult_skipFile() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return [m, ctx] }
        def activatorRegexes() { return null }
        ''')

        assertEquals(0, uut.scripts.size())
    }

    @Test
    public void wrongDatatypeInRegexList_skip() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return [m, ctx] }
        def activatorRegexes() { return [1, 2, 3] }
        ''')
        assertEquals(0, uut.scripts.size())
    }

    @Test
    public void noFilePresent_skip() {
        nextScript(null)
        assertEquals(0, uut.scripts.size())
    }

    @Test
    void answerIsNull_nullMessageEmptyContext() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return null }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertNull(res)
        assertNotNull(ctx)
    }

    @Test
    void stringAsAnswer_nullMessageEmptyContext() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return "string" }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertNull(res)
        assertNotNull(ctx)
    }

    @Test
    void intAsAnswer_nullMessageEmptyContext() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return "string" }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertNull(res)
        assertNotNull(ctx)
    }

    @Test
    void intAsAnswerMessage_works() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return [1, ctx] }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertEquals("1", res);
        assertNotNull(ctx)
    }

    @Test
    void returnedContextIsNoMap_setToEmptyMap() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return ["message", 123] }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertEquals(null, res);
        assertTrue(ctx instanceof Map<String, String>)
    }

    @Test
    void noScriptWasRegistered_anwerIsNull() {
        // no scripts gets registered here
        def (res, ctx) = uut.answer("message")
        assertEquals(null, res);
        assertTrue(ctx instanceof Map<String, String>)
    }

    @Test
    void regexOfRegisteredScriptDoesNotMatch_anwerIsNull() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return ["message", ctx] }
        def activatorRegexes() { return ["regex_not_match.*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("message")
        assertEquals(null, res);
        assertTrue(ctx instanceof Map<String, String>)
    }

    @Test
    void regexOfRegisteredScriptMatches_answerIsFilled() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return ["message", ctx] }
        def activatorRegexes() { return ["match.*"] } // accept everything
        ''')

        def (res, ctx) = uut.answer("match message")
        assertEquals("message", res);
        assertTrue(ctx instanceof Map<String, String>)
    }

    @Test
    void givenContextWithoutScript_isNotDeleted() {
        nextScript('''
        def answer(String m, Map<String, Object> ctx = [:]) { return ["message", ctx] }
        def activatorRegexes() { return [".*"] } // accept everything
        ''')

        def contextInput = [key: "value"]
        def (_, ctx) = uut.answer("message", contextInput)

        assertEquals("value", ctx.get("key"))
    }

    @Test
    void scriptHasAnError_resultingContextIsTheGivenOne() {
        nextScript('''
        invalid groovy code
        ''')

        def contextInput = [key: "value"]
        def (_, ctx) = uut.answer("message", contextInput)

        assertEquals("value", ctx.get("key"))
    }

    @Test
    void buildWithDefaultFileSystemWorks() {
        PluginManager.build(".")
        // no exception expected here
    }

    @Test
    void constructWithDefaultFileSystemWorks() {
        new PluginManager(".")
        // no exception expected here
    }

    @Test
    void basePathDoesNotExist_emptyFilesListButNoError() {
        PluginManager.build("/this/does/not/exists/adlkfjaldsfj98afd9asvx")
    }

    private void nextScript(String content) {
        fs.nextFileReadResult = content
        fs.nextFilesResult.add(Paths.get("./empty_script.groovy"))
        uut.refresh() // reload
    }
}
