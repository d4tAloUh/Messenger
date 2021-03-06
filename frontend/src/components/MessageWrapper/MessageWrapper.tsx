import React from "react";
import styles from "./MessageWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";
import Message from "../Message/Message";
import {ICurrentUser} from "../../api/auth/authModels";
import classnames from "classnames";

interface IOwnProps {
    message: IMessage;
    currentUser?: ICurrentUser;
}

class MessageWrapper extends React.Component<IOwnProps> {
    render() {
        const {message, currentUser} = this.props;
        const ownMessage = message.senderId === currentUser?.id;
        const classes = classnames(
            styles.messageWrapper,
            ownMessage ? styles.messageWrapperRight : styles.messageWrapperLeft
        );

        return (
            <div className={classes}>
                <Message message={message} />
            </div>
        );
    }
}

export default MessageWrapper;
