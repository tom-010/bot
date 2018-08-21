
def activatorRegexes() {
    return ["Hello.*", "Hi.*", "Howdy.*"];
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {
    return ["Hello Back :)", context]
}