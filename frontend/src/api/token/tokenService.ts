const tokenService = {

    isLoggedIn: (): boolean => !!localStorage.getItem('accessToken'),

    setTokens: (accessToken: string, refreshToken: string): void => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    },

    removeTokens: (): void => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
    },

};

export default tokenService;
