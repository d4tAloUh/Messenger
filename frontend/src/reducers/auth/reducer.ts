import {AuthActions} from "./actions";
import {REMOVE_CURRENT_USER, SET_CURRENT_USER} from "./actionTypes";

export interface ICurrentUser {
    id: string;
}

export interface IAuthState {
    currentUser?: ICurrentUser;
}

export const authReducer = (state: IAuthState = {}, action: AuthActions): IAuthState => {
    switch (action.type) {
        case SET_CURRENT_USER:
            return {
                ...state,
                currentUser: action.payload,
            };
        case REMOVE_CURRENT_USER:
            return {
                ...state,
                currentUser: undefined,
            };
        default:
            return state;
    }
};

export default authReducer;

