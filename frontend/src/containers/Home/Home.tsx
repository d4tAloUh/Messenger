import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import {bindActionCreators} from "redux";
import {IAppState} from "../../reducers";
import {authActions} from "../../reducers/auth/actions";
import {connect} from "react-redux";
import tokenService from "../../services/tokenService";
import {ICurrentUser} from "../../reducers/auth/reducer";
import LoaderWrapper from "../../components/LoaderWrapper/LoaderWrapper";

interface IPropsFromDispatch {
    actions: {
        removeCurrentUser: typeof authActions.removeCurrentUser;
        setCurrentUser: typeof authActions.setCurrentUser;
    };
}

interface IPropsFromState {
    currentUser?: ICurrentUser;
}

interface IState {
    loadingUser: boolean;
}

class Home extends React.Component<RouteComponentProps & IPropsFromDispatch & IPropsFromState> {

    state = {
        loadingUser: false,
    } as IState;

    logout = () => {
        tokenService.removeTokens();
        this.props.actions.removeCurrentUser();
        this.props.history.push("/auth");
    }

    componentDidMount() {
        if (tokenService.isLoggedIn()) {
            this.setState({loadingUser: true});
            setTimeout(() => {
                this.props.actions.setCurrentUser({id: "id"});
                this.setState({loadingUser: false});
            }, 1000);
        }
    }

    render() {
        if (!tokenService.isLoggedIn()) {
            return <Redirect to="/auth" />;
        }

        const {loadingUser} = this.state;

        return (
            <LoaderWrapper loading={loadingUser}>
                Home
                <button onClick={this.logout}>Logout</button>
            </LoaderWrapper>
        );
    }
}

const mapStateToProps = (state: IAppState) => ({
    currentUser: state.auth.currentUser,
});

const mapDispatchToProps = (dispatch: any) => ({
    actions:
        bindActionCreators<any,
            {
                removeCurrentUser: typeof authActions.removeCurrentUser,
                setCurrentUser: typeof authActions.setCurrentUser,
            }>(
            {
                removeCurrentUser: authActions.removeCurrentUser,
                setCurrentUser: authActions.setCurrentUser,
            }, dispatch),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));
