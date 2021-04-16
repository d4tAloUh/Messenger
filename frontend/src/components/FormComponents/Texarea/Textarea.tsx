import React, {ChangeEventHandler} from "react";
import styles from "./Textarea.module.sass";
import classnames from "classnames";

interface IOwnProps {
    value: string;
    onChange: ChangeEventHandler<HTMLTextAreaElement>;
    onBlur?: any;
    name: string;
    className?: string;
    onKeyDown?: any;
}

class Textarea extends React.Component<IOwnProps> {
    render() {
        const {value, onChange, onBlur, name, className, onKeyDown} = this.props;
        const classes = classnames(styles.textarea, className);

        return (
            <div className={styles.wrapper}>
                <textarea
                    className={classes}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    name={name}
                    onKeyDown={onKeyDown}
                />
            </div>
        );
    }
}

export default Textarea;
