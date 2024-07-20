package ru.panov;

public class BroadcastMessageRequest {
    private static final String TYPE = "broadcast";
    private String message;

    public String getType() {
        return TYPE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
