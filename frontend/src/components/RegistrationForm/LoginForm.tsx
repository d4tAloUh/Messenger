import React from "react";
import {Link} from "react-router-dom";
import FormWrapper from "../FormComponents/FormWrapper/FormWrapper";

class RegistrationForm extends React.Component {
    render() {
        return (
            <FormWrapper>
                TODO
                <div className="center">
                    Already registered?
                    <br />
                    <Link to="/auth/login">to login</Link></div>
            </FormWrapper>
        );
    }
}

export default RegistrationForm;
