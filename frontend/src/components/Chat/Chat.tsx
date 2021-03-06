import React from "react";
import styles from "./Chat.module.sass";

class Chat extends React.Component {
    render() {
        return (
            <div className={styles.wrapper}>
                Messages
            </div>
        );
    }
}

export default Chat;
