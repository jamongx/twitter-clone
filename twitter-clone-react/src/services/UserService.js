import customAxios from "./axiosConfig";
import { Config } from "../utils/Config";


export const getAllUsers = () => customAxios.get(Config.BASE_REST_API_URL);

export const saveUser = (user) => customAxios.post(Config.BASE_REST_API_URL, user);

export const getUser = (id) => customAxios.get(Config.BASE_REST_API_URL + '/' + id);

export const updateUser = (id, user) => customAxios.put(Config.BASE_REST_API_URL + '/' + id, user);

export const deleteUser = (id) => customAxios.delete(Config.BASE_REST_API_URL + '/' + id);

export const activeUser = (id) => customAxios.patch(Config.BASE_REST_API_URL + '/' + id + '/active');

export const deactiveUser = (id) => customAxios.patch(Config.BASE_REST_API_URL + '/' + id + '/deactive');

export const saveAvatar = (id, formData) => customAxios.post(Config.BASE_REST_API_URL + '/' + id + '/avatar', formData, {
  headers: {
    "Content-Type": "multipart/form-data",
  }
});

export const updateProfile = (profile) => customAxios.patch(`${Config.BASE_REST_API_URL}/${profile.id}/profile`, profile);

