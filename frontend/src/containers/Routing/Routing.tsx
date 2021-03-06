import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import Auth from "../Auth/Auth";
import Home from "../Home/Home";

class Routing extends React.Component {
    render() {
        return (
            <Switch>
                 <Route exact path="/auth" render={() => <Auth/>}/>
                 <Route exact path="/home" render={() => <Home/>}/>
                 <Route path="/">
                     <Redirect to="/home" />
                 </Route>
            </Switch>
        );
    }
}

export default Routing;
