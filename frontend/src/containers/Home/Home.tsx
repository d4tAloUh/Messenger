import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import {bindActionCreators} from "redux";
import {IAppState} from "../../reducers";
import {authActions} from "../../reducers/auth/actions";
import {connect} from "react-redux";
import LoaderWrapper from "../../components/LoaderWrapper/LoaderWrapper";
import {ICurrentUser} from "../../api/auth/authModels";
import authService from "../../api/auth/authService";
import Header from "../../components/Header/Header";
import ChatsList from "../../components/ChatsList/ChatsList";
import styles from "./Home.module.sass";
import Chat from "../../components/Chat/Chat";
import {chatsListActions} from "../../reducers/chatsList/actions";
import {IChatListElement} from "../../api/chat/chatModels";
import chatService from "../../api/chat/chatService";

interface IPropsFromDispatch {
    actions: {
        removeCurrentUser: typeof authActions.removeCurrentUser;
        setCurrentUser: typeof authActions.setCurrentUser;
        setChatsList: typeof chatsListActions.setChatsList;
        removeChatsList: typeof chatsListActions.removeChatsList;
    };
}

interface IPropsFromState {
    currentUser?: ICurrentUser;
    chatsList?: IChatListElement[];
}

interface IState {
    loading: boolean;
}

class Home extends React.Component<RouteComponentProps & IPropsFromDispatch & IPropsFromState, IState> {

    state = {
        loading: false,
    } as IState;

    async componentDidMount() {
        if (authService.isLoggedIn()) {
            const currentUser = await authService.me();
            this.props.actions.setCurrentUser(currentUser);
        }
    }

    logout = async () => {
        this.setState({loading: true});
        await authService.logout();
        this.props.actions.removeCurrentUser();
        this.setState({loading: false});
        this.props.history.push("/auth");
    }

    loadChatsList = async () => {
        this.props.actions.removeChatsList();
        const list = await chatService.getChatsList();
        this.props.actions.setChatsList(list);
    }

    render() {
        if (!authService.isLoggedIn()) {
            return <Redirect to="/auth" />;
        }

        const {chatsList, currentUser} = this.props;
        const {loading} = this.state;

        return (
            <LoaderWrapper loading={!currentUser || loading}>
                <Header logout={this.logout} />
                <div className={styles.content}>
                    <ChatsList chatsList={chatsList} loadChatsList={this.loadChatsList} />
                    <Chat />
                </div>
            </LoaderWrapper>
        );
    }
}

const mapStateToProps = (state: IAppState) => ({
    currentUser: state.auth.currentUser,
    chatsList: state.chatsList.chatsList,
});

const mapDispatchToProps = (dispatch: any) => ({
    actions:
        bindActionCreators<any,
            {
                removeCurrentUser: typeof authActions.removeCurrentUser,
                setCurrentUser: typeof authActions.setCurrentUser,
                setChatsList: typeof chatsListActions.setChatsList,
                removeChatsList: typeof chatsListActions.removeChatsList,
            }>(
            {
                removeCurrentUser: authActions.removeCurrentUser,
                setCurrentUser: authActions.setCurrentUser,
                setChatsList: chatsListActions.setChatsList,
                removeChatsList: chatsListActions.removeChatsList,
            }, dispatch),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));
