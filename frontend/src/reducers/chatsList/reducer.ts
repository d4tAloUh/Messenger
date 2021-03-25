import {ChatsListActions} from "./actions";
import {IChatListElement} from "../../api/chat/chatModels";
import {
    APPEND_CHAT_DETAILS_CACHED,
    APPEND_LOADING_MESSAGE,
    REMOVE_CHATS_LIST,
    SET_CHAT_MESSAGES,
    SET_CHATS_LIST, SET_MESSAGE_LOADED,
    SET_SELECTED
} from "./actionTypes";
import {IMessage} from "../../api/message/messageModels";

export interface IMessageLoading {
    id: string;
    text: string;
}

export interface IMessageWrapper {
    info?: IMessage;
    loading?: IMessageLoading;
}

export interface IChatCache {
    details: IChatListElement;
    messages?: IMessageWrapper[];
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
                    chat => chat.details.id === action.payload.chatId
                        ? {
                            ...chat,
                            messages: action.payload.messages.map(m => ({info: m}))
                        }
                        : chat
                ),
            };
        case APPEND_LOADING_MESSAGE:
            return {
                ...state,
                chatsDetailsCached: state.chatsDetailsCached.map(
                    chat => chat.details.id === action.payload.chatId
                        ? {
                            ...chat,
                            messages: chat.messages && [...chat.messages, {loading: action.payload.message}],
                        }
                        : chat
                ),
            };
        case SET_MESSAGE_LOADED:
            return {
                ...state,
                chatsDetailsCached: state.chatsDetailsCached.map(
                    chat => chat.details.id === action.payload.chatId
                        ? {
                            ...chat,
                            messages: chat.messages?.map(
                                message => message.loading?.id === action.payload.loadingId
                                ? {info: action.payload.message}
                                : message
                            ),
                        }
                        : chat
                ),
            };
        default:
            return state;
    }
};

export default authReducer;

