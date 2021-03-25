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
        const refreshToken = tokenService.getRefreshToken();
        await apiClient.post('/api/auth/logout', {refreshToken});
        tokenService.removeTokens();
    },

    me: async (): Promise<ICurrentUser> => {
        return await apiClient.get('/api/auth/me');
    },

    isLoggedIn: (): boolean => {
        return tokenService.isLoggedIn();
    }

};

export default authService;
