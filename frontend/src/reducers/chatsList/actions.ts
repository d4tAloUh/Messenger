import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatListElement} from "../../api/chat/chatModels";
import {REMOVE_CHATS_LIST, SET_CHATS_LIST} from "./actionTypes";

export const chatsListActions = {
    setChatsList: (list: IChatListElement[]) => createAction(SET_CHATS_LIST, list),
    removeChatsList: () => createAction(REMOVE_CHATS_LIST),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
