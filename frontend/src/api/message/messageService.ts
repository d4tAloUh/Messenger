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
                text: faker.lorem.paragraph(5),
            }));
    },

};

export default messageService;
