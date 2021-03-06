import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatDetails, IChatListElement} from "../../api/chat/chatModels";
import {
    APPEND_CHAT_DETAILS_CACHED,
    REMOVE_CHATS_LIST,
    SET_CHAT_MESSAGES,
    SET_CHATS_LIST,
    SET_SELECTED
} from "./actionTypes";
import {IMessage} from "../../api/message/messageModels";

export const chatsListActions = {
    setChatsList: (list: IChatListElement[]) => createAction(SET_CHATS_LIST, list),
    removeChatsList: () => createAction(REMOVE_CHATS_LIST),
    setSelected: (id: string) => createAction(SET_SELECTED, id),
    removeSelected: () => createAction(SET_SELECTED, undefined),
    appendDetailsCached: (details: IChatDetails) => createAction(APPEND_CHAT_DETAILS_CACHED, details),
    setChatMessages:
        (chatId: string, messages: IMessage[]) => createAction(SET_CHAT_MESSAGES, {chatId, messages}),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
