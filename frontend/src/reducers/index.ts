import { combineReducers } from "redux";
import {reducer as toastr, ToastrState} from 'react-redux-toastr';
import {Action} from'redux';
import authReducer, {IAuthState} from "./auth/reducer";

export interface IAppState {
    toastr: ToastrState;
    auth: IAuthState;
}

export interface IAppAction<T extends string, P> extends Action<T> {
    payload: P;
}

const rootReducers = combineReducers({
    toastr,
    auth: authReducer,
});

export default rootReducers;
