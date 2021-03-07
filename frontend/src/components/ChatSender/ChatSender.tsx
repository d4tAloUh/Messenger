import React from "react";
import styles from "./ChatSender.module.sass";
import Button from "../FormComponents/Button/Button";
import Textarea from "../FormComponents/Texarea/Textarea";

interface IOwnProps {
    sendMessage: (text: string) => Promise<void>;
}

interface IState {
    text: string;
}

class ChatSender extends React.Component<IOwnProps, IState> {
    state = {
        text: ''
    } as IState;

    isValid = () => {
        const {text} = this.state;
        return !!text.trim();
    }

    handleSend = () => {
        const {text} = this.state;
        this.props.sendMessage(text).then();
        this.setState({text: ''});
    }

    render() {
        const {text} = this.state;

        return (
            <div className={styles.wrapper}>
                <div className={styles.textAreaWrapper}>
                    <Textarea
                        value={text}
                        onChange={e => this.setState({text: e.target.value})}
                        name="text"
                        className={styles.textarea}
                    />
                </div>
                <div className={styles.buttonsWrapper}>
                    <Button
                        text="Send"
                        onClick={this.handleSend}
                        disabled={!this.isValid()}
                    />
                </div>
            </div>
        );
    }
}

export default ChatSender;
