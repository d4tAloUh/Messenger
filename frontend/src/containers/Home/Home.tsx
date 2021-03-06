import React from "react";
import {RouteComponentProps, withRouter} from "react-router-dom";

class Home extends React.Component<RouteComponentProps> {
    logout = () => {
        this.props.history.push("/auth");
    }

    render() {
        return (
            <div>
                Home
                <button onClick={this.logout}>Logout</button>
            </div>
        );
    }
}

export default withRouter(Home);
