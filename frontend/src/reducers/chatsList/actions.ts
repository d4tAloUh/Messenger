import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatDetails, ILastSeen} from "../../api/chat/general/generalChatModels";
import {
    ADD_CHAT_TO_LIST,
    APPEND_CHAT_DETAILS_CACHED, APPEND_LOADING_MESSAGE, APPEND_READY_MESSAGE, REMOVE_CHAT_FROM_LIST,
    REMOVE_CHATS_LIST,
    SET_CHAT_MESSAGES,
    SET_CHATS_LIST, SET_FIRST_CHAT_IN_LIST, SET_MESSAGE_LOADED, SET_SEEN_CHAT, SET_SEEN_LIST,
    SET_SELECTED, UPDATE_CHAT_IN_LIST, UPDATE_SENDER_USERNAME
} from "./actionTypes";
import {IMessage} from "../../api/message/messageModels";
import {IMessageLoading} from "./reducer";
import {IChangeMessagesUsername} from "../../containers/Home/Home";

export const chatsListActions = {
    setChatsList: (list: IChatDetails[]) => createAction(SET_CHATS_LIST, list),
    setSeenList: (list: ILastSeen[]) => createAction(SET_SEEN_LIST, list),
    setSeenChat: (chatId: string, seen: number) => createAction(SET_SEEN_CHAT, {chatId, seen}),
    addChatToList: (chat: IChatDetails) => createAction(ADD_CHAT_TO_LIST, chat),
    updateChatInList: (chat: IChatDetails) => createAction(UPDATE_CHAT_IN_LIST, chat),
    setFirstChatInList: (chatId: string) => createAction(SET_FIRST_CHAT_IN_LIST, chatId),
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
    appendReadyMessage: (chatId: string, message: IMessage, loadingId: string) => createAction(
        APPEND_READY_MESSAGE, {chatId, message, loadingId}
    ),
    updateSenderUsername: ({newUsername, userId}: IChangeMessagesUsername) => createAction(
        UPDATE_SENDER_USERNAME, {newUsername, userId}
    ),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
