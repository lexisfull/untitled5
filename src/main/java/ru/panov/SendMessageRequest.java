package ru.panov;

public class SendMessageRequest {
    public static final String TYPE = "sendMessage";

    private String recipient;
    private String message;

    public SendMessageRequest() {
        setType(TYPE);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
