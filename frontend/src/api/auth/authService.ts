import {ICurrentUser, ILoginRequest, ILoginResponse, IRegisterRequest} from "./authModels";
import tokenService from "../token/tokenService";
import apiClient from "../apiClient";

const authService = {

    login: async (loginDto: ILoginRequest): Promise<void> => {
        const response: ILoginResponse = await apiClient.post('/api/auth/login', loginDto);
        tokenService.setTokens(response.accessToken, response.refreshToken);
    },

    register: async (registerDto: IRegisterRequest): Promise<void> => {
        await apiClient.post('/api/auth/register', registerDto);
    },

    logout: async (): Promise<void> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        tokenService.removeTokens();
    },

    me: async (): Promise<ICurrentUser> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        return {id: "id", username: "username", fullName: "Full Name"};
    },

    isLoggedIn: (): boolean => {
        return tokenService.isLoggedIn();
    }

};

export default authService;
