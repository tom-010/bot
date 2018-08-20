
def activatorRegex() {
    return "Hello.*";
}

def answer(String incomingMessage, Map<String, Object> context = [:]) {
    
    context.put("Name", "Thomas")
    context.put("Alter", "24")
    
    return ["${incomingMessage}!", context]
}
