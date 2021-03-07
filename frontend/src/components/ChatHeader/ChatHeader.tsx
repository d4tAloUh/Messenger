import React from "react";
import styles from "./ChatHeader.module.sass";
import {IChatDetails} from "../../api/chat/chatModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";

interface IOwnProps {
    chatDetails?: IChatDetails;
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
