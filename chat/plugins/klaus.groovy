
def activatorRegexes() {
    return ["Klaus.*"];
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {
    return ["Klaus Deniffel", context]
}