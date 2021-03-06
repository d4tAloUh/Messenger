import React from "react";
import styles from "./Chat.module.sass";
import {IChatDetails} from "../../api/chat/chatModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";

interface IOwnProps {
    chatsDetailsCached: IChatDetails[];
    loadChatDetails: (id: string) => Promise<void>;
    selectedChatId?: string;
}

class Chat extends React.Component<IOwnProps> {

    async componentDidUpdate(prevProps: Readonly<IOwnProps>, prevState: Readonly<{}>, snapshot?: any) {
        const {selectedChatId, chatsDetailsCached} = this.props;
        if (
            selectedChatId &&
            prevProps.selectedChatId !== selectedChatId &&
            !chatsDetailsCached.find(c => c.id === selectedChatId)
        ) {
            await this.props.loadChatDetails(selectedChatId);
        }
    }

    render() {
        const {chatsDetailsCached, selectedChatId} = this.props;
        const chatInfo = chatsDetailsCached.find(c => c.id === selectedChatId);

        return (
            <div className={styles.wrapper}>
                {selectedChatId
                    ? (
                        <LoaderWrapper loading={!chatInfo}>
                            Messages
                        </LoaderWrapper>
                    )
                    : (
                        "Messages"
                    )
                }
            </div>
        );
    }
}

export default Chat;
