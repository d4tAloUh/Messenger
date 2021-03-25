import React from "react";
import styles from "./ChatHeader.module.sass";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import {IChatListElement} from "../../api/chat/chatModels";

interface IOwnProps {
    chatDetails?: IChatListElement;
}

class ChatHeader extends React.Component<IOwnProps> {
    render() {
        const {chatDetails} = this.props;
        return (
            <div className={styles.wrapper}>
                <LoaderWrapper loading={!chatDetails}>
                    {chatDetails?.title || "-"}
                </LoaderWrapper>
            </div>
        );
    }
}

export default ChatHeader;
