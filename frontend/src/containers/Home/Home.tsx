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

    async componentDidMount() {
        if (authService.isLoggedIn()) {
            this.setState({loadingUser: true});
            const currentUser = await authService.me();
            this.props.actions.setCurrentUser(currentUser);
            this.setState({loadingUser: false});
        }
    }

    logout = async () => {
        this.setState({loadingUser: true});
        await authService.logout();
        this.props.actions.removeCurrentUser();
        this.setState({loadingUser: false});
        this.props.history.push("/auth");
    }

    render() {
        if (!authService.isLoggedIn()) {
            return <Redirect to="/auth" />;
        }

        const {loadingUser} = this.state;
        const {currentUser} = this.props;

        return (
            <LoaderWrapper loading={loadingUser}>
                <Header logout={this.logout} />
                Home
                <br />
                I am {currentUser?.fullName} ({currentUser?.username})
                <br />
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
