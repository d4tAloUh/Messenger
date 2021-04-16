import React from "react";
import styles from "./ChatsList.module.sass";
import ChatListElement from "../ChatListElement/ChatListElement";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import Input from "../FormComponents/Input/Input";

interface IOwnProps {
    loadChatsList: () => Promise<void>;
    chatsList?: IChatDetails[];
    selectChat: (el: IChatDetails) => void;
    selectedChatId?: string;
}

interface IState {
    filter: string;
}

class ChatsList extends React.Component<IOwnProps, IState> {

    state = {
        filter: ''
    } as IState;

    async componentDidMount() {
        await this.props.loadChatsList();
    }

    render() {
        const {chatsList, selectChat, selectedChatId} = this.props;
        const {filter} = this.state;
        const filteredChatsList = chatsList
            ?.filter(chat => chat.title.toLowerCase().includes(filter.toLowerCase()));

        return (
            <div className={styles.wrapper}>
                <LoaderWrapper loading={!chatsList}>
                    <div className={styles.searchWrapper}>
                        <Input
                            value={filter}
                            onChange={(e: any) => this.setState({filter: e.target.value})}
                            placeholder="Search"
                            className={styles.search}
                        />
                    </div>
                    {filteredChatsList
                        ?.map(chat => (
                            <ChatListElement
                                key={chat.id}
                                elementData={chat}
                                onClick={() => selectChat(chat)}
                                selected={selectedChatId === chat.id}
                            />
                        ))
                    }
                    {!filteredChatsList?.length && (
                        <div className={styles.empty}>
                            No chats found.
                        </div>
                    ) }
                </LoaderWrapper>
            </div>
        );
    }
}

export default ChatsList;
