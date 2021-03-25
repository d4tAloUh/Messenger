import {IChatListElement} from "./chatModels";
import apiClient from "../apiClient";

const chatService = {

    getChatsList: async (): Promise<IChatListElement[]> => {
        const response = await apiClient.get('/api/chat/general/all');
        return response.data.data;
    },
};

export default chatService;
