import React from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";

class Auth extends React.Component<RouteComponentProps> {
    login = () => {
        this.props.history.push("/home");
    }

    render() {
        return (
            <div>
                Auth
                <button onClick={this.login}>Login</button>
            </div>
        );
    }
}

export default withRouter(Auth);
