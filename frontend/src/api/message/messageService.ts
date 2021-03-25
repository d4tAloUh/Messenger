import {IMessage} from "./messageModels";
import apiClient from "../apiClient";

const messageService = {

    getMessagesByChatId: async (chatId: string): Promise<IMessage[]> => {
        return apiClient.get(`/api/messages/chat/${chatId}`);
    },

    sendMessage: async (chatId: string, text: string): Promise<IMessage> => {
        return apiClient.post(`/api/messages/chat`, {chatId, text});
    },

};

export default messageService;
