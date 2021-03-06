import faker from "faker";
import {IChatListElement} from "./chatModels";

const chatService = {

    getChatsList: async (): Promise<IChatListElement[]> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
        return new Array(30)
            .fill(null)
            .map(_ => ({
                id: faker.random.uuid(),
                isGroup: faker.random.boolean(),
                title: faker.lorem.sentence(5),
            }));
    },

};

export default chatService;
