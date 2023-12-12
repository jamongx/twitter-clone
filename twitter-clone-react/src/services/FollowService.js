import customAxios from "./axiosConfig";
import { Config } from "../utils/Config";

export const getFollowers = (id) => customAxios.get(`${Config.FOLLOWS_REST_API_URL}/${id}/followers`);

export const getFollowing = (id) => customAxios.get(`${Config.FOLLOWS_REST_API_URL}/${id}/following`);

export const saveFollowers = (follow) => customAxios.post(Config.FOLLOWS_REST_API_URL, follow);

export const deleteFollowers = (id) => customAxios.delete(`${Config.FOLLOWS_REST_API_URL}/${id}`);
