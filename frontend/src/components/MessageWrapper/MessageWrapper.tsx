import React from "react";
import styles from "./MessageWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";

interface IOwnProps {
    message: IMessage;
}

class MessageWrapper extends React.Component<IOwnProps> {
    render() {
        const {message} = this.props;

        return (
            <div className={styles.messageWrapper}>{message.text}</div>
        );
    }
}

export default MessageWrapper;
