import axios from 'axios';
import tokenService from "./token/tokenService";

const apiClient = axios.create();

const responseErrorHandler = (e: any) => {
    console.log(e.response.data.message);
    return Promise.reject(new Error(e.response.data.message));
    // const status = e.response.status;
    // const originalRequest = e.config;

    // if (status !== 403) return Promise.reject(e);

    // if (originalRequest._retry) {
    //     window.location.assign('/auth');
    //     return Promise.reject(e);
    // }
    //
    // originalRequest._retry = true;

    // return apiClient.post('/api/auth/renovate', {refresh: tokenService.getRefreshToken()}
    //     .then(res => {
    //         if (res.status !== 201) {
    //             history.push('/auth');
    //             return Promise.reject(e);
    //         }
    //
    //         tokenProvider.setToken(res.data.data);
    //         return apiClient(originalRequest);
    //     });
};

apiClient.interceptors.request.use(request => {
    const token = tokenService.getAccessToken();
    if (token) {
        request.headers.Authorization = token;
    }
    request.url = 'http://localhost:8080' + request.url;
    return request;
});

apiClient.interceptors.response.use(undefined, responseErrorHandler);

export default apiClient;
