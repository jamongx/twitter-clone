import customAxios from "./axiosConfig";
import { Config } from "../utils/Config";

export const getFollowers = (id) => customAxios.get(Config.FOLLOWERS_REST_API_URL + '/' + id)

export const getFollowing = (id) => customAxios.get(Config.FOLLOWING_REST_API_URL + '/' + id)

export const saveFollowers = (follow) => customAxios.post(Config.FOLLOWS_REST_API_URL, follow)

export const deleteFollowers = (id) => customAxios.delete(Config.FOLLOWS_REST_API_URL + '/' + id)