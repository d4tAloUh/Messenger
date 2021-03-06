import React from "react";
import "./Form.sass";

class Form extends React.Component {
    render() {
        return (
            <div className="form">
                {this.props.children}
            </div>
        );
    }
}

export default Form;
