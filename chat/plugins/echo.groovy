
def activatorRegexes() { return ["echo .*"]; }
def answer(String incomingMessage, Map<String, Object> context = [:]) { return [incomingMessage.substring(5), context] }