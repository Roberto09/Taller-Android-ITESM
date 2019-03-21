package roberto.app.messengertallerdemo.Objects;

public class ChatMessage {
    private String senderId;

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    public ChatMessage(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }
}