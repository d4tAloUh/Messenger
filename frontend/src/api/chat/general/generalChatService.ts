import {IChatDetails, ILastSeen} from "./generalChatModels";
import apiClient from "../../apiClient";

const generalChatService = {

    getChatsList: async (): Promise<IChatDetails[]> => {
        const response = await apiClient.get('/api/chat/general/all');
        return response.data.data;
    },

    getSeenAt: async (): Promise<ILastSeen[]> => {
        const response = await apiClient.get('/api/chat/general/seen');
        return response.data.data;
    },

    readChat: async (chatId: string): Promise<number> => {
        const response = await apiClient.post(`/api/chat/general/read/${chatId}`);
        return response.data.data;
    },
};

export default generalChatService;
