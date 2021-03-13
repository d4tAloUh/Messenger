import React from "react";
import {Link} from "react-router-dom";
import FormWrapper from "../FormComponents/FormWrapper/FormWrapper";
import {ILoginRequest} from "../../api/auth/authModels";
import * as Yup from "yup";
import {Form, Formik} from "formik";
import Input from "../FormComponents/Input/Input";
import Button from "../FormComponents/Button/Button";

interface IOwnProps {
    register: (request: ILoginRequest) => Promise<void>;
}

interface IState {
    loading: boolean;
}

const validationSchema = Yup.object().shape({
    username: Yup.string()
        .min(4, 'Too Short!')
        .max(16, 'Too Long!')
        .required('Required'),
    password: Yup.string()
        .min(4, 'Too Short!')
        .max(16, 'Too Long!')
        .required('Required'),

});

class RegistrationForm extends React.Component<IOwnProps, IState> {

    state = {
        loading: false,
    } as IState;

    handleRegistration = async (values: any) => {
        const {register} = this.props;
        const {username, password} = values;
        this.setState({loading: true});
        await register({username, password});
        this.setState({loading: false});
    };

    render() {
        const {loading} = this.state;
        return (
            <div>
                <Formik
                    onSubmit={this.handleRegistration}
                    initialValues={{username: '', password: ''}}
                    validationSchema={validationSchema}
                    render={({
                                 errors,
                                 touched,
                                 handleChange,
                                 handleBlur,
                                 values
                             }) => {
                        const valid = !errors.username && !errors.password;
                        return (
                            <Form>
                                <FormWrapper>
                                    <Input
                                        label="Username"
                                        value={values.username}
                                        name="username"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        error={errors.username}
                                        touched={touched.username}
                                    />
                                    <Input
                                        label="Password"
                                        value={values.password}
                                        name="password"
                                        type="password"
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        error={errors.password}
                                        touched={touched.password}
                                    />
                                    <Button
                                        text="Sign up"
                                        loading={loading}
                                        disabled={!valid}
                                        submit
                                    />
                                </FormWrapper>
                            </Form>
                        );
                    }}
                />
                <div className="center">
                    Already registered?
                    <br/>
                    <Link to="/auth/login">Login</Link>
                </div>
            </div>
        );
    }
}

export default RegistrationForm;