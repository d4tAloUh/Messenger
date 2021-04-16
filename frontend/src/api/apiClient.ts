import axios from 'axios';
import tokenService from "./token/tokenService";
import {env} from "../env";

const apiClient = axios.create();

const responseErrorHandler = (e: any) => {
    const status = e.response.status;
    const originalRequest = e.config;

    if (status !== 403) {
        return Promise.reject(new Error(e.response.data.message));
    }

    const refreshToken = tokenService.getRefreshToken();

    if (e.response.config.url.includes("/api/auth/refresh")) {
        return Promise.reject(e);
    }

    return apiClient.post('/api/auth/refresh', {refreshToken})
        .then(res => {
            tokenService.setTokens(res.data.data.accessToken, res.data.data.refreshToken);
            return apiClient(originalRequest);
        })
        .catch(e => {
            tokenService.removeTokens();
            window.location.replace('/auth');
        });
};

apiClient.interceptors.request.use(request => {
    const token = tokenService.getAccessToken();
    if (token && !request.url?.includes("/api/auth/refresh")) {
        request.headers.Authorization = token;
    }
    const prefix = `${env.backendProtocol}://${env.backendHost}:${env.backendPort}`;
    if (!request.url?.startsWith(prefix)) {
        request.url = prefix + request.url;
    }
    return request;
});

apiClient.interceptors.response.use(undefined, responseErrorHandler);

export default apiClient;
