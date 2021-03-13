import React from "react";
import {Redirect, Route, RouteComponentProps, Switch, withRouter} from "react-router-dom";
import authService from "../../api/auth/authService";
import styles from "./Auth.module.sass";
import LoginForm from "../../components/LoginForm/LoginForm";
import RegistrationForm from "../../components/RegistrationForm/RegistrationForm";

class Auth extends React.Component<RouteComponentProps> {

    login = async () => {
        await authService.login({username: "user", password: "pass"});
        this.props.history.push("/home");
    }

    register = async () => {
        await authService.register({username: "user", password: "pass"});
        this.props.history.push("/auth/login");
    }

    render() {
        if (authService.isLoggedIn()) {
            return <Redirect to="/home" />;
        }

        return (
            <div className={styles.wrapper}>
                <h1 className={styles.header}>Welcome to Ch@t</h1>
                <div className={styles.authForm}>
                    <Switch>
                        <Route exact path="/auth/login" render={() => <LoginForm login={this.login} />} />
                        <Route exact path="/auth/register" render={() =>
                            <RegistrationForm register={this.register}/>} />
                        <Route path="/auth" render={() => <Redirect to="/auth/login" />} />
                    </Switch>
                </div>
            </div>
        );
    }
}

export default withRouter(Auth);
