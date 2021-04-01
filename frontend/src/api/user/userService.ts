import {IProfileEdit, IUserSearchDto} from "./userModels";
import apiClient from "../apiClient";

const userService = {

    search: async (username: string): Promise<IUserSearchDto> => {
        const params = new URLSearchParams();
        params.append('username', username);
        const response = await apiClient.get(`/api/users/search?${params.toString()}`);
        return response.data.data;
    },

    editProfile: async (request: IProfileEdit): Promise<void> => {
        await apiClient.post(`/api/users/update-profile`, request);
    },
};

export default userService;
