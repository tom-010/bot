
def activatorRegexes() {
    return ["echo .*"];
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {
    return ["${incomingMessage}!", context]
}