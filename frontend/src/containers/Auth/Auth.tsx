import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import authService from "../../api/auth/authService";
import LoaderWrapper from "../../components/LoaderWrapper/LoaderWrapper";

interface IState {
    loading: boolean;
}

class Auth extends React.Component<RouteComponentProps> {
    state = {
        loading: false,
    } as IState;

    login = async () => {
        this.setState({loading: true});
        await authService.login({username: "user", password: "pass"});
        this.setState({loading: false});
        this.props.history.push("/home");
    }

    render() {
        if (authService.isLoggedIn()) {
            return <Redirect to="/home" />;
        }

        const {loading} = this.state;

        return (
            <LoaderWrapper loading={loading}>
                Auth
                <button onClick={this.login}>Login</button>
            </LoaderWrapper>
        );
    }
}

export default withRouter(Auth);
