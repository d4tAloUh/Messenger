import React from "react";
import styles from "./ChatListElement.module.sass";
import {IChatListElement} from "../../api/chat/chatModels";

interface IOwnProps {
    elementData: IChatListElement;
}

class ChatListElement extends React.Component<IOwnProps> {
    render() {
        const {elementData} = this.props;
        return (
            <div className={styles.wrapper}>{elementData.title}</div>
        );
    }
}

export default ChatListElement;
