import {ChatsListActions} from "./actions";
import {IChatDetails, IChatListElement} from "../../api/chat/chatModels";
import {
    APPEND_CHAT_DETAILS_CACHED,
    REMOVE_CHATS_LIST,
    SET_CHAT_MESSAGES,
    SET_CHATS_LIST,
    SET_SELECTED
} from "./actionTypes";
import {IMessage} from "../../api/message/messageModels";

export interface IChatCache {
    details: IChatDetails;
    messages?: IMessage[];
}

export interface IChatsListState {
    chatsList?: IChatListElement[];
    selectedId?: string;
    chatsDetailsCached: IChatCache[];
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
        case REMOVE_CHATS_LIST:
            return {
                ...state,
                chatsList: undefined,
                selectedId: undefined,
                chatsDetailsCached: [],
            };
        case SET_SELECTED:
            return {
                ...state,
                selectedId: action.payload,
            };
        case APPEND_CHAT_DETAILS_CACHED:
            return {
                ...state,
                chatsDetailsCached: [...state.chatsDetailsCached, {details: action.payload}],
            };
        case SET_CHAT_MESSAGES:
            return {
                ...state,
                chatsDetailsCached: state.chatsDetailsCached.map(
                    c => c.details.id === action.payload.chatId
                        ? {...c, messages: action.payload.messages}
                        : c
                ),
            };
        default:
            return state;
    }
};

export default authReducer;

