import React from "react";
import styles from "./PersonalChatDetails.module.sass";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import {IPersonalChatInfo} from "../../api/chat/personal/personalChatModels";
import personalChatService from "../../api/chat/personal/personalChatService";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";

interface IOwnProps {
    chatDetails: IChatDetails;
}

interface IState {
    info?: IPersonalChatInfo;
}

class PersonalChatDetails extends React.Component<IOwnProps, IState> {

    state = {} as IState;

    async componentDidMount() {
        const {chatDetails} = this.props;
        const info: IPersonalChatInfo = await personalChatService.getById(chatDetails.id);
        this.setState({info});
    }

    render() {
        const {info} = this.state;

        return (
            <LoaderWrapper loading={!info}>
                {info?.companion.fullName}
            </LoaderWrapper>
        );
    }
}

export default PersonalChatDetails;
