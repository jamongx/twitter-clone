import axios from "axios";
import { Config } from "../utils/Config";

export const registerAPICall = (register) => axios.post(`${Config.AUTH_REST_API_URL}/register`, register);

export const loginAPICall = (login) => axios.post(`${Config.AUTH_REST_API_URL}/login`, login);
