import React from "react";
import {Link} from "react-router-dom";
import Form from "../FormComponents/Form/Form";

class RegistrationForm extends React.Component {
    render() {
        return (
            <Form>
                TODO
                <div className="center">
                    Already registered?
                    <br />
                    <Link to="/auth/login">to login</Link></div>
            </Form>
        );
    }
}

export default RegistrationForm;
