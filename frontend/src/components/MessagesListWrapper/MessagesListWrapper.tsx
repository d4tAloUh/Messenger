import React from "react";
import styles from "./MessagesListWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import MessageWrapper from "../MessageWrapper/MessageWrapper";

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
                       <MessageWrapper message={message} />
                    ))}
                </LoaderWrapper>
            </div>
        );
    }
}

export default MessagesListWrapper;
