import React from "react";
import {Redirect, Route, RouteComponentProps, Switch, withRouter} from "react-router-dom";
import authService from "../../api/auth/authService";
import "./Auth.sass";
import LoginForm from "../../components/LoginForm/LoginForm";
import RegistrationForm from "../../components/RegistrationForm/LoginForm";

class Auth extends React.Component<RouteComponentProps> {

    login = async () => {
        await authService.login({username: "user", password: "pass"});
        this.props.history.push("/home");
    }

    render() {
        if (authService.isLoggedIn()) {
            return <Redirect to="/home" />;
        }

        return (
            <div className="wrapper">
                <div className="auth-form">
                    <Switch>
                        <Route exact path="/auth/login" render={() => <LoginForm login={this.login} />} />
                        <Route exact path="/auth/register" render={() => <RegistrationForm />} />
                        <Route path="/auth" render={() => <Redirect to="/auth/login" />} />
                    </Switch>
                </div>
            </div>
        );
    }
}

export default withRouter(Auth);
