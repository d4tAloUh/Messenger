import React from "react";
import styles from "./Form.module.sass";

class Form extends React.Component {
    render() {
        return (
            <div className={styles.form}>
                {this.props.children}
            </div>
        );
    }
}

export default Form;
