import {IPersonalChatInfo} from "./personalChatModels";
import apiClient from "../../apiClient";

const personalChatService = {

    getById: async (chatId: string): Promise<IPersonalChatInfo> => {
        const response = await apiClient.get(`/api/chat/personal/${chatId}`);
        return response.data.data;
    },
};

export default personalChatService;
