import {ActionsUnion, createAction} from "../../helpers/action.helper";
import {IChatListElement} from "../../api/chat/chatModels";
import {SET_CHATS_LIST} from "./actionTypes";

export const chatsListActions = {
    setChatsList: (list: IChatListElement[]) => createAction(SET_CHATS_LIST, list),
    removeChatsList: () => createAction(SET_CHATS_LIST, undefined),
};

export type ChatsListActions = ActionsUnion<typeof chatsListActions>;
