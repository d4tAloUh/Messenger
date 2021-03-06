import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import {bindActionCreators} from "redux";
import {IAppState} from "../../reducers";
import {authActions} from "../../reducers/auth/actions";
import {connect} from "react-redux";
import tokenService from "../../services/tokenService";
import {ICurrentUser} from "../../reducers/auth/reducer";

interface IPropsFromDispatch {
    actions: {
        removeCurrentUser: typeof authActions.removeCurrentUser;
    };
}

interface IPropsFromState {
    currentUser?: ICurrentUser;
}

class Home extends React.Component<RouteComponentProps & IPropsFromDispatch & IPropsFromState> {
    logout = () => {
        tokenService.removeTokens();
        this.props.actions.removeCurrentUser();
        this.props.history.push("/auth");
    }

    render() {
        if (!tokenService.isLoggedIn()) {
            return <Redirect to="/auth" />;
        }

        return (
            <div>
                Home
                <button onClick={this.logout}>Logout</button>
            </div>
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
                removeCurrentUser: typeof authActions.removeCurrentUser
            }>(
            {
                removeCurrentUser: authActions.removeCurrentUser
            }, dispatch),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Home));
