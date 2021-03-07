import React from "react";
import styles from "./Chat.module.sass";
import {IChatCache} from "../../reducers/chatsList/reducer";
import classnames from "classnames";
import ChatHeader from "../ChatHeader/ChatHeader";
import MessagesListWrapper from "../MessagesListWrapper/MessagesListWrapper";
import {ICurrentUser} from "../../api/auth/authModels";
import ChatSender from "../ChatSender/ChatSender";

interface IOwnProps {
    chatsDetailsCached: IChatCache[];
    loadChatDetails: (id: string) => Promise<void>;
    loadChatMessages: (id: string) => Promise<void>;
    selectedChatId?: string;
    currentUser?: ICurrentUser;
    sendMessage: (text: string) => Promise<void>;
}

class Chat extends React.Component<IOwnProps> {

    async componentDidUpdate(prevProps: Readonly<IOwnProps>, prevState: Readonly<{}>, snapshot?: any) {
        const {selectedChatId, chatsDetailsCached} = this.props;
        if (
            selectedChatId &&
            prevProps.selectedChatId !== selectedChatId &&
            !chatsDetailsCached.find(c => c.details.id === selectedChatId)
        ) {
            await this.props.loadChatDetails(selectedChatId);
            await this.props.loadChatMessages(selectedChatId);
        }
    }

    render() {
        const {chatsDetailsCached, selectedChatId, currentUser, sendMessage} = this.props;
        const chatInfo = chatsDetailsCached.find(c => c.details.id === selectedChatId);

        if (!selectedChatId) {
            return (
                <div className={classnames(styles.wrapper, styles.flexWide)}>
                    Choose your chat
                </div>
            );
        }

        return (
            <div className={styles.wrapper}>
                 <ChatHeader chatDetails={chatInfo?.details}/>
                 <MessagesListWrapper
                     messages={chatInfo?.messages}
                     currentUser={currentUser}
                 />
                 <ChatSender sendMessage={sendMessage} />
            </div>
        );
    }
}

export default Chat;
