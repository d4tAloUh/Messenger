import React from 'react';
import {Provider} from 'react-redux';
import {store} from "../../store";
import ReduxToastr from "react-redux-toastr";
import {Router} from 'react-router-dom';
import {history} from '../../helpers/history.helper';
import Routing from "../Routing/Routing";

class App extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <ReduxToastr
                    timeOut={4000}
                    newestOnTop={false}
                    preventDuplicates
                    position="bottom-right"
                    transitionIn="fadeIn"
                    transitionOut="fadeOut"
                    closeOnToastrClick
                />
                <Router history={history}>
                    <Routing />
                </Router>
            </Provider>
        );
    }
}

export default App;
