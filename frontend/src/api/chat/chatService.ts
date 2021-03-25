import faker from "faker";
import {IChatDetails, IChatListElement} from "./chatModels";
import apiClient from "../apiClient";

const chatService = {

    getChatsList: async (): Promise<IChatListElement[]> => {
        return await apiClient.get('/api/chat/general/all');
    },

    getChatDetailsById: async (id: string): Promise<IChatDetails> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        return {
            id,
            isGroup: faker.random.boolean(),
            title: faker.lorem.sentence(5),
            info: faker.lorem.paragraph(5),
        };
    },

};

export default chatService;
