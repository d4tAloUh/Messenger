import {ICurrentUser} from "./reducer";
import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {SET_CURRENT_USER} from "./actionTypes";

export const authActions = {
    setCurrentUser: (currentUser: ICurrentUser) => createAction(SET_CURRENT_USER, currentUser)
};

export type AuthActions = ActionsUnion<typeof authActions>;
