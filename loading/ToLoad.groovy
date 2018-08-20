
def answer(String incomingMessage, Map<String, Object> context = [:]) {
    context.put("Name", "Thomas")
    context.put("Alter", "24")
    return ["Hello ${incomingMessage}!", context]
}
