import React from "react";
import styles from "./MessagesListWrapper.module.sass";
import {IMessage} from "../../api/message/messageModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import MessageWrapper from "../MessageWrapper/MessageWrapper";
import {ICurrentUser} from "../../api/auth/authModels";

interface IOwnProps {
    messages?: IMessage[];
    currentUser?: ICurrentUser;
}

class MessagesListWrapper extends React.Component<IOwnProps> {
    componentDidUpdate(prevProps: Readonly<IOwnProps>, prevState: Readonly<{}>, snapshot?: any) {
        if (this.listBottom) {
            this.scrollToBottom();
        }
    }

    scrollToBottom = () => {
        this.listBottom.scrollIntoView({ behavior: "smooth" });
    }

    listBottom = null as any;

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
                    <div className={styles.listBottom} ref={el => this.listBottom = el}/>
                </LoaderWrapper>
            </div>
        );
    }
}

export default MessagesListWrapper;
