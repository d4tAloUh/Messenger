import {AuthActions} from "./actions";
import {SET_CURRENT_USER} from "./actionTypes";

export interface ICurrentUser {

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
        default:
            return state;
    }
};

export default authReducer;

