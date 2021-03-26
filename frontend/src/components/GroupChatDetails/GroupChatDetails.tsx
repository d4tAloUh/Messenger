import React from "react";
import styles from "./GroupChatDetails.module.sass";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import LoaderWrapper from "../LoaderWrapper/LoaderWrapper";
import Button from "../FormComponents/Button/Button";
import {IGroupChatInfo, RoleEnum} from "../../api/chat/group/groupChatModels";
import groupChatService from "../../api/chat/group/groupChatService";
import UserManager from "../UserManager/UserManager";
import UserFinder from "../UserFinder/UserFinder";
import ErrorMessage from "../FormComponents/ErrorMessage/ErrorMessage";
import {IUserShortDto} from "../../api/user/userModels";

interface IOwnProps {
    chatDetails: IChatDetails;
    deleteChatFromList: (chatId: string) => void;
}

interface IState {
    info?: IGroupChatInfo;
    deleting: boolean;
    adding: boolean;
    toAddUserId?: string;
    error?: string;
}

class GroupChatDetails extends React.Component<IOwnProps, IState> {

    state = {
        deleting: false,
        adding: false,
    } as IState;

    async componentDidMount() {
        await this.loadData();
    }

    loadData = async () => {
        const {chatDetails} = this.props;
        this.setState({info: undefined});
        const info: IGroupChatInfo = await groupChatService.getById(chatDetails.id);
        this.setState({info});
    }

    handleDelete = async () => {
        const id = this.props.chatDetails.id;
        this.setState({deleting: true});
        await groupChatService.deleteById(id);
        this.setState({deleting: false});
        this.props.deleteChatFromList(id);
    }

    handleAddMember = async () => {
        const {info, toAddUserId} = this.state;
        if(!info || ! toAddUserId) {
            return;
        }

        this.setState({error: undefined});
        try {
            this.setState({adding: true});
            await groupChatService.addMember(info.id, toAddUserId);
            await this.loadData();
        } catch (e) {
            this.setState({error: e.message});
        } finally {
            this.setState({adding: false});
        }
    }

    handleDeleteMember = async (userId: string) => {
        const {info} = this.state;
        if(!info) {
            return;
        }

        this.setState({error: undefined});
        try {
            await groupChatService.deleteMember(info.id, userId);
            await this.loadData();
        } catch (e) {
            this.setState({error: e.message});
        }
    }

    handleToggleRole = async (user: IUserShortDto) => {
        const {info} = this.state;
        if(!info) {
            return;
        }

        this.setState({error: undefined});
        try {
            if (user.permissionLevel === RoleEnum.ADMIN) {
                await groupChatService.downgradeMember(info.id, user.id);
            } else {
                await groupChatService.upgradeMember(info.id, user.id);
            }
            await this.loadData();
        } catch (e) {
            this.setState({error: e.message});
        }
    }

    isAdminOrOwner = (permissionLevel: RoleEnum) => {
        return permissionLevel === RoleEnum.ADMIN || permissionLevel === RoleEnum.OWNER;
    }

    render() {
        const {info, deleting, adding, error, toAddUserId} = this.state;

        return (
            <LoaderWrapper loading={!info}>
                {info && (
                    <div>
                        <div className={styles.title}>{info.title}</div>
                        <div className={styles.permission}>{info.permissionLevel}</div>
                    </div>
                )}
                {error && (
                    <ErrorMessage text={error} />
                )}
                {(info?.permissionLevel && this.isAdminOrOwner(info.permissionLevel)) && (
                    <div className={styles.finderWrapper}>
                        <UserFinder setUserId={id => this.setState({toAddUserId: id})} />
                        <div className={styles.buttonWrapper}>
                            <Button
                                text="Add member"
                                onClick={this.handleAddMember}
                                loading={adding}
                                disabled={!toAddUserId}
                            />
                        </div>
                    </div>
                )}
                {info?.members.map(user => (
                    <UserManager
                        user={user}
                        deletable={
                            (info?.permissionLevel !== RoleEnum.MEMBER && user.permissionLevel === RoleEnum.MEMBER) ||
                            (info?.permissionLevel === RoleEnum.OWNER && user.permissionLevel === RoleEnum.ADMIN)
                        }
                        onDelete={() => this.handleDeleteMember(user.id)}
                        upgradable={info.permissionLevel === RoleEnum.OWNER}
                        upgraded={user.permissionLevel === RoleEnum.ADMIN}
                        onToggleUpgrade={() => this.handleToggleRole(user)}
                    />
                ))}
                {info?.permissionLevel === RoleEnum.OWNER && (
                    <div className={styles.buttonWrapper}>
                        <Button
                            text="Delete group chat"
                            onClick={this.handleDelete}
                            loading={deleting}
                        />
                    </div>
                )}
            </LoaderWrapper>
        );
    }
}

export default GroupChatDetails;
