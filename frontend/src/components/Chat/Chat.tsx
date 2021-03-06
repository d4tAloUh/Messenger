import React from "react";
import styles from "./Chat.module.sass";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import {IChatCache} from "../../reducers/chatsList/reducer";

interface IOwnProps {
    chatsDetailsCached: IChatCache[];
    loadChatDetails: (id: string) => Promise<void>;
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
        }
    }

    render() {
        const {chatsDetailsCached, selectedChatId} = this.props;
        const chatInfo = chatsDetailsCached.find(c => c.details.id === selectedChatId);

        return (
            <div className={styles.wrapper}>
                {selectedChatId
                    ? (
                        <LoaderWrapper loading={!chatInfo}>
                            Messages
                        </LoaderWrapper>
                    )
                    : (
                        "Choose your chat"
                    )
                }
            </div>
        );
    }
}

export default Chat;
