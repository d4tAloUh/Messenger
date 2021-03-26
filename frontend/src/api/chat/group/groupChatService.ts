import apiClient from "../../apiClient";
import {IChatDetails} from "../general/generalChatModels";

const groupChatService = {

    create: async (chatName: string): Promise<IChatDetails> => {
        const response = await apiClient.post(`/api/chat/group/create`, {chatName});
        return response.data.data;
    },
};

export default groupChatService;
