import React from "react";
import {Redirect, RouteComponentProps, withRouter} from "react-router-dom";
import tokenService from "../../services/tokenService";

class Auth extends React.Component<RouteComponentProps> {
    login = () => {
        tokenService.setTokens("aaa", "rrr");
        this.props.history.push("/home");
    }

    render() {
        if (tokenService.isLoggedIn()) {
            return <Redirect to="/home" />;
        }

        return (
            <div>
                Auth
                <button onClick={this.login}>Login</button>
            </div>
        );
    }
}

export default withRouter(Auth);
