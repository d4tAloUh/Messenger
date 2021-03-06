import React from "react";
import styles from "./MessagesListWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import MessageWrapper from "../MessageWrapper/MessageWrapper";
import {ICurrentUser} from "../../api/auth/authModels";
import Chat from "../Chat/Chat";

interface IOwnProps {
    messages?: IMessage[];
    currentUser?: ICurrentUser;
}

class MessagesListWrapper extends React.Component<IOwnProps> {
    render() {
        const {messages, currentUser} = this.props;
        return (
            <div className={styles.wrapper}>
                <LoaderWrapper loading={!messages}>
                    {messages?.map(message => (
                       <MessageWrapper
                           message={message}
                           currentUser={currentUser}
                       />
                    ))}
                </LoaderWrapper>
            </div>
        );
    }
}

export default MessagesListWrapper;
