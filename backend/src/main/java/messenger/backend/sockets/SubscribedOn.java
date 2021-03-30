package messenger.backend.sockets;

public enum SubscribedOn {
    MESSAGE("/topic/messages/"),
    CREATE_CHAT("/topic/chats/create/"),
    DELETE_CHAT("/topic/chats/delete/"),
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
