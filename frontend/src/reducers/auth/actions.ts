import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {REMOVE_CURRENT_USER, SET_CURRENT_USER} from "./actionTypes";
import {ICurrentUser} from "../../api/auth/authModels";

export const authActions = {
    setCurrentUser: (currentUser?: ICurrentUser) => createAction(SET_CURRENT_USER, currentUser),
    removeCurrentUser: () => createAction(REMOVE_CURRENT_USER),
};

export type AuthActions = ActionsUnion<typeof authActions>;
