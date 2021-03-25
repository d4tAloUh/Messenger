import faker from "faker";
import {IMessage} from "./messageModels";
import apiClient from "../apiClient";

const messageService = {

    getMessagesByChatId: async (chatId: string): Promise<IMessage[]> => {
        return apiClient.get(`/api/messages/chat/${chatId}`);
    },

    sendMessage: async (chatId: string, text: string): Promise<IMessage> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        return {
            id: faker.random.uuid(),
            senderId: "id",
            senderName: `${faker.name.firstName()} ${faker.name.lastName()}`,
            datetime: faker.date.past(1).toDateString(),
            text
        };
    },

};

export default messageService;
