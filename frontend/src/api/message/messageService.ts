import faker from "faker";
import {IMessage} from "./messageModels";

const messageService = {

    getMessagesByChatId: async (chatId: string): Promise<IMessage[]> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
        return new Array(50)
            .fill(null)
            .map(_ => ({
                id: faker.random.uuid(),
                senderId: faker.random.boolean() ? "id" : faker.random.uuid(),
                senderName: `${faker.name.firstName()} ${faker.name.lastName()}`,
                datetime: faker.date.past(1).toDateString(),
                text: faker.lorem.paragraph(2),
            }));
    },

    sendMessage: async (chatId: string, text: string): Promise<IMessage> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
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
