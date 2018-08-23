
def activatorRegexes() {
    return ["Slack.*"];
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {
    return ["Slack is an frontend", context]
}