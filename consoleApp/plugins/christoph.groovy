
def activatorRegexes() {
    return ["Christoph.*", "Felix.*", "Chris.*toph"];
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {

    return ["Wölfle", context]
}