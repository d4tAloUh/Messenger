import React from "react";
import {Link} from "react-router-dom";

class Home extends React.Component {
    render() {
        return (
            <div>
                Home
                <Link to="/auth">TO AUTH</Link>
            </div>
        );
    }
}

export default Home;
