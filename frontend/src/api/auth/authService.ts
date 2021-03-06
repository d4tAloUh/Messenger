import {ICurrentUser, ILoginRequest} from "./authModels";
import tokenService from "../token/tokenService";

const authService = {

    login: async (loginDto: ILoginRequest): Promise<void> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
        tokenService.setTokens("a", "r");
    },

    logout: async (): Promise<void> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
        tokenService.removeTokens();
    },

    me: async (): Promise<ICurrentUser> => {
        await new Promise(resolve => setTimeout(resolve, 1000));
        return {id: "id"};
    },

    isLoggedIn: (): boolean => {
        return tokenService.isLoggedIn();
    }

};

export default authService;
