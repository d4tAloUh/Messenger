import faker from "faker";
import {IChatListElement} from "./chatModels";
import apiClient from "../apiClient";

const chatService = {

    getChatsList: async (): Promise<IChatListElement[]> => {
        return await apiClient.get('/api/chat/general/all');
    },
};

export default chatService;
