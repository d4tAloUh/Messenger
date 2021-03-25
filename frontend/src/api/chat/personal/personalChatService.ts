import {IPersonalChatInfo} from "./personalChatModels";
import apiClient from "../../apiClient";

const personalChatService = {

    getById: async (chatId: string): Promise<IPersonalChatInfo> => {
        const response = await apiClient.get(`/api/chat/personal/${chatId}`);
        return response.data.data;
    },

    deleteById: async (chatId: string): Promise<void> => {
        await apiClient.post(`/api/chat/personal/delete`, {chatId});
    },
};

export default personalChatService;
