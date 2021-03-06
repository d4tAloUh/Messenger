import React from "react";
import styles from "./ChatsList.module.sass";
import ChatListElement from "../ChatListElement/ChatListElement";

class ChatsList extends React.Component {
    render() {
        const array = new Array(30).fill(null);
        return (
            <div className={styles.wrapper}>
                {array.map((_, i) => <ChatListElement key={i} />)}
            </div>
        );
    }
}

export default ChatsList;
