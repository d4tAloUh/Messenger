import {ChatsListActions} from "./actions";
import {IChatListElement} from "../../api/chat/chatModels";
import {SET_CHATS_LIST, SET_SELECTED} from "./actionTypes";

export interface IChatsListState {
    chatsList?: IChatListElement[];
    selectedId?: string;
}

const initialState: IChatsListState = {};

export const authReducer = (
    state: IChatsListState = initialState,
    action: ChatsListActions
): IChatsListState => {
    switch (action.type) {
        case SET_CHATS_LIST:
            return {
                ...state,
                chatsList: action.payload,
            };
        case SET_SELECTED:
            return {
                ...state,
                selectedId: action.payload,
            };
        default:
            return state;
    }
};

export default authReducer;

