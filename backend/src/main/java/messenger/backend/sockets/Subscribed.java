package messenger.backend.sockets;

public enum Subscribed {
    MESSAGE("/topic/messages/"),
    CREATE_PERSONAL_CHAT("/topic/chats/personal/create/"),
    DELETE_PERSONAL_CHAT("/topic/chats/personal/delete/"),
    ;

    private final String text;

    Subscribed(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
