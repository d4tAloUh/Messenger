package messenger.backend.sockets;

public enum SubscribedOn {
    MESSAGE("/topic/messages/"),
    READ_CHAT("/topic/chats/read/"),
    CREATE_CHAT("/topic/chats/create/"),
    DELETE_CHAT("/topic/chats/delete/"),
    UPDATE_CHAT("/topic/chats/update/"),
    ;

    private final String text;

    SubscribedOn(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
