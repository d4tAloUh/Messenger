import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatDetails} from "../../api/chat/general/generalChatModels";
import {
    ADD_CHAT_TO_LIST,
    APPEND_CHAT_DETAILS_CACHED, APPEND_LOADING_MESSAGE, REMOVE_CHAT_FROM_LIST,
    REMOVE_CHATS_LIST,
    SET_CHAT_MESSAGES,
    SET_CHATS_LIST, SET_MESSAGE_LOADED,
    SET_SELECTED
} from "./actionTypes";
import {IMessage} from "../../api/message/messageModels";
import {IMessageLoading} from "./reducer";

export const chatsListActions = {
    setChatsList: (list: IChatDetails[]) => createAction(SET_CHATS_LIST, list),
    addChatToList: (chat: IChatDetails) => createAction(ADD_CHAT_TO_LIST, chat),
    removeChatFromList: (chatId: string) => createAction(REMOVE_CHAT_FROM_LIST, chatId),
    removeChatsList: () => createAction(REMOVE_CHATS_LIST),
    setSelected: (id: string) => createAction(SET_SELECTED, id),
    removeSelected: () => createAction(SET_SELECTED, undefined),
    appendDetailsCached: (details: IChatDetails) => createAction(APPEND_CHAT_DETAILS_CACHED, details),
    setChatMessages: (chatId: string, messages: IMessage[]) => createAction(
        SET_CHAT_MESSAGES, {chatId, messages}
    ),
    appendLoadingMessage: (chatId: string, message: IMessageLoading) => createAction(
        APPEND_LOADING_MESSAGE, {chatId, message}
    ),
    setMessageLoaded: (chatId: string, loadingId: string, message: IMessage) => createAction(
        SET_MESSAGE_LOADED, {chatId, loadingId, message}
    ),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
