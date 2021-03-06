import {ChatsListActions} from "./actions";
import {IChatDetails, IChatListElement} from "../../api/chat/chatModels";
import {APPEND_CHAT_DETAILS_CACHED, SET_CHATS_LIST, SET_SELECTED} from "./actionTypes";

export interface IChatsListState {
    chatsList?: IChatListElement[];
    selectedId?: string;
    chatsDetailsCached: IChatDetails[];
}

const initialState: IChatsListState = {
    chatsDetailsCached: [],
};

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
        case APPEND_CHAT_DETAILS_CACHED:
            return {
                ...state,
                chatsDetailsCached: [...state.chatsDetailsCached, action.payload],
            };
        default:
            return state;
    }
};

export default authReducer;

