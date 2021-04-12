import React from "react";
import styles from "./Message.module.sass";
import {IMessageWrapper} from "../../reducers/chatsList/reducer";
import classnames from "classnames";
import moment from "moment";

interface IOwnProps {
    message: IMessageWrapper;
    isVisibleName?: boolean;
}

class Message extends React.Component<IOwnProps> {
    render() {
        const {message, isVisibleName} = this.props;
        const classes = classnames(styles.message, message.loading && styles.loading);
        const text = message.info?.text || message.loading?.text;
        const momentCreatedAt = moment(message.info?.createdAt);

        return (
            <div className={classes}>
                {isVisibleName && (
                    <div className={styles.name}>
                        {message.info?.senderName}
                    </div>
                )}
                <div className={styles.text}>
                    {text}
                </div>
                <div className={styles.datetime}>
                    {momentCreatedAt.format("DD MMM HH:mm")}
                </div>
            </div>
        );
    }
}

export default Message;
