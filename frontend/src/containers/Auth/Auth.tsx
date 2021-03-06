import React from "react";
import { Link } from "react-router-dom";

class Auth extends React.Component {
    render() {
        return (
            <div>
                Auth
                <Link to="/home">TO HOME</Link>
            </div>
        );
    }
}

export default Auth;
