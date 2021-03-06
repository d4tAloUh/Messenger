import {ChatsListActions} from "./actions";
import {IChatListElement} from "../../api/chat/chatModels";
import {REMOVE_CHATS_LIST, SET_CHATS_LIST} from "./actionTypes";

export interface IChatsListState {
    chatsList?: IChatListElement[];
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
        case REMOVE_CHATS_LIST:
            return {
                ...state,
                chatsList: undefined,
            };
        default:
            return state;
    }
};

export default authReducer;

