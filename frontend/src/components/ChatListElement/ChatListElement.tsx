import React from "react";
import styles from "./ChatListElement.module.sass";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import classNames from "classnames";

interface IOwnProps {
    elementData: IChatDetails;
    onClick: () => void;
    selected: boolean;
}

class ChatListElement extends React.Component<IOwnProps> {
    render() {
        const {elementData, onClick, selected} = this.props;
        const classes = classNames(styles.wrapper, selected && styles.selected);

        return (
            <div className={classes} onClick={onClick}>{elementData.title} ({elementData.type})</div>
        );
    }
}

export default ChatListElement;
