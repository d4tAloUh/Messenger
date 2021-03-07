import React from "react";
import styles from "./Message.module.sass";
import {IMessageWrapper} from "../../reducers/chatsList/reducer";
import classnames from "classnames";

interface IOwnProps {
    message: IMessageWrapper;
}

class Message extends React.Component<IOwnProps> {
    render() {
        const {message} = this.props;
        const classes = classnames(styles.message, message.loading && styles.loading);
        const text = message.info?.text || message.loading?.text;

        return (
            <div className={classes}>{text}</div>
        );
    }
}

export default Message;
