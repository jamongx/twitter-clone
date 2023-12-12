import customAxios from "./axiosConfig";
import { Config } from "../utils/Config";

export const getAllUsers = () => customAxios.get(Config.USERS_REST_API_URL);

export const saveUser = (user) => customAxios.post(Config.USERS_REST_API_URL, user);

export const getUser = (id) => customAxios.get(`${Config.USERS_REST_API_URL}/${id}`);

export const updateUser = (id, user) => customAxios.put(`${Config.USERS_REST_API_URL}/${id}`, user);

export const deleteUser = (id) => customAxios.delete(`${Config.USERS_REST_API_URL}/${id}`);

export const activeUser = (id) => customAxios.patch(`${Config.USERS_REST_API_URL}/${id}/active`);

export const deactiveUser = (id) => customAxios.patch(`${Config.USERS_REST_API_URL}/${id}/deactive`);
