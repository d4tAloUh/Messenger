import React from "react";
import styles from "./PersonalChatDetails.module.sass";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import {IPersonalChatInfo} from "../../api/chat/personal/personalChatModels";
import personalChatService from "../../api/chat/personal/personalChatService";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import UserCard from "../UserCard/UserCard";

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
                {info && (
                    <UserCard user={info?.companion} />
                )}
            </LoaderWrapper>
        );
    }
}

export default PersonalChatDetails;
