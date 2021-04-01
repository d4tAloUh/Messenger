import React from "react";
import styles from "./Header.module.sass";
import {ICurrentUser} from "../../api/auth/authModels";

interface IOwnProps {
    logout: () => Promise<void>;
    openModal: () => void;
    currentUser?: ICurrentUser;
}

class Header extends React.Component<IOwnProps> {
    render() {
        const {logout, openModal, currentUser} = this.props;

        return (
            <div className={styles.header}>
                <h1>Messenger</h1>
                <div className={styles.links}>
                    <span onClick={openModal}>{currentUser?.fullName}</span>
                    <span className={styles.link} onClick={logout}>Logout</span>
                </div>
            </div>
        );
    }
}

export default Header;
