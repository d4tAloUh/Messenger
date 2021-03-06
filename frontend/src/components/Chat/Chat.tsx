import React from "react";
import styles from "./Chat.module.sass";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import {IChatCache} from "../../reducers/chatsList/reducer";
import classnames from "classnames";

interface IOwnProps {
    chatsDetailsCached: IChatCache[];
    loadChatDetails: (id: string) => Promise<void>;
    loadChatMessages: (id: string) => Promise<void>;
    selectedChatId?: string;
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
        const {chatsDetailsCached, selectedChatId} = this.props;
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
                <LoaderWrapper loading={!chatInfo}>
                    <div>{chatInfo?.details?.title}</div>
                    <br/>
                    <LoaderWrapper loading={!chatInfo?.messages}>
                        <div className={styles.messagesWrapper}>
                            {chatInfo?.messages?.map(message => (
                                <div>{message.text}</div>
                            ))}
                        </div>
                    </LoaderWrapper>
                </LoaderWrapper>
            </div>
        );
    }
}

export default Chat;
