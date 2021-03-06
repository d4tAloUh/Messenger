import React from "react";
import styles from "./Message.module.sass";
import {IMessage} from "../../api/message/messageModels";

interface IOwnProps {
    message: IMessage;
}

class Message extends React.Component<IOwnProps> {
    render() {
        const {message} = this.props;

        return (
            <div className={styles.message}>{message.text}</div>
        );
    }
}

export default Message;
