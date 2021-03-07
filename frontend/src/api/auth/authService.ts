import {ICurrentUser, ILoginRequest} from "./authModels";
import tokenService from "../token/tokenService";

const authService = {

    login: async (loginDto: ILoginRequest): Promise<void> => {
        await new Promise(resolve => setTimeout(resolve, 500));
        tokenService.setTokens("a", "r");
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
