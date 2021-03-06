import React from "react";
import styles from "./MessagesListWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";

interface IOwnProps {
    messages?: IMessage[];
}

class MessagesListWrapper extends React.Component<IOwnProps> {
    render() {
        const {messages} = this.props;
        return (
            <div className={styles.wrapper}>
                <LoaderWrapper loading={!messages}>
                    {messages?.map(message => (
                        <div>{message.text}</div>
                    ))}
                </LoaderWrapper>
            </div>
        );
    }
}

export default MessagesListWrapper;
