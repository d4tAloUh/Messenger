import React from "react";
import styles from "./Input.module.sass";

interface IOwnProps {
    value: string;
    label?: string;
    onChange: (value: string) => void;
    name: string;
    type?: "password" | "number";
}

class Input extends React.Component<IOwnProps> {
    render() {
        const {value, onChange, label, type, name} = this.props;

        return (
            <>
                {label && <div className={styles.label}>{label}</div>}
                <input
                    className={styles.input}
                    value={value}
                    onChange={e => onChange(e.target.value)}
                    type={type || "text"}
                    name={name}
                />
            </>
        );
    }
}

export default Input;
