import axios from "axios";
import { getToken } from "./AuthService";

const customAxios = axios.create();

// Add a request interceptor
customAxios.interceptors.request.use(function (config) {
    config.headers['Authorization'] = getToken();
    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

export default customAxios;