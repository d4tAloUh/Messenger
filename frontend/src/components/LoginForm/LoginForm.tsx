import React from "react";
import {Link} from "react-router-dom";
import Form from "../FormComponents/Form/Form";
import Input from "../FormComponents/Input/Input";
import Button from "../FormComponents/Button/Button";
import {ILoginRequest} from "../../api/auth/authModels";

interface IOwnProps {
    login: (request: ILoginRequest) => Promise<void>;
}

interface IState {
    username: string;
    password: string;
    loading: boolean;
}

class LoginForm extends React.Component<IOwnProps, IState> {
    state = {
        username: '',
        password: '',
        loading: false,
    } as IState;

    isValid = () => {
        const {username, password} = this.state;

        return username.length > 4 && password.length > 4;
    }

    handleLogin = async () => {
        const {username, password} = this.state;
        const {login} = this.props;
        this.setState({loading: true});
        await login({username, password});
        this.setState({loading: false});
    }

    render() {
        const {username, password, loading} = this.state;

        return (
            <div>
                <Form>
                    <Input
                        label="Username"
                        value={username}
                        name="username"
                        onChange={v => this.setState({username: v})}
                    />
                    <Input
                        label="Password"
                        value={password}
                        name="password"
                        type="password"
                        onChange={v => this.setState({password: v})}
                    />
                    <Button
                        text="Log in"
                        loading={loading}
                        disabled={!this.isValid()}
                        onClick={this.handleLogin}
                    />
                </Form>
                <div className="center">
                    Not registered yet?
                    <br />
                    <Link to="/auth/register">Sign up</Link>
                </div>
            </div>
        );
    }
}

export default LoginForm;
