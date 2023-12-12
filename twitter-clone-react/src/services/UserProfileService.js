import customAxios from "./axiosConfig";
import { Config } from "../utils/Config";

export const getAllUserProfiles = () => customAxios.get(Config.USER_PROFILES_REST_API_URL);

export const saveUserProfile = (profile) => customAxios.post(Config.USER_PROFILES_REST_API_URL, profile);

export const getUserProfile = (id) => customAxios.get(`${Config.USER_PROFILES_REST_API_URL}/${id}`);

export const updateUserProfile = (profile) => customAxios.put(`${Config.USER_PROFILES_REST_API_URL}/${profile.id}`, profile);

export const deleteUserProfile = (id) => customAxios.delete(`${Config.USER_PROFILES_REST_API_URL}/${id}`);

export const patchUserProfileAvatar = (id, formData) => customAxios.patch(`${Config.USER_PROFILES_REST_API_URL}/${id}/avatar`, formData, {
  headers: {
    "Content-Type": "multipart/form-data",
  }
});