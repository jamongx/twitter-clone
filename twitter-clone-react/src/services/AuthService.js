import axios from "axios";
import { Config } from "../utils/Config";

export const registerAPICall = (registerObj) => axios.post(Config.AUTH_REST_API_URL + '/register', registerObj);

export const loginAPICall = (loginObj) => axios.post(Config.AUTH_REST_API_URL + '/login', loginObj);

export const updateProfile = (formData) => axios.post(Config.AUTH_REST_API_URL + '/profile', formData, {
    headers: {
        'Content-Type': 'multipart/form-data'
    }
});

export const storeToken = (token) => localStorage.setItem("token", token);

export const getToken = () => localStorage.getItem("token");

export const getProfileDataFromSession = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    return profileData;
}

export const storeProfileDataToSession = (profileData) => {
    sessionStorage.setItem("profileData", JSON.stringify(profileData));
}

export const isUserLoggedIn = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    if (profileData == null) {
        return false;
    }
    else {
        return true;
    }
}

export const isAdminUser = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    if (profileData != null) {
        if (profileData.roles[0].role === "ROLE_ADMIN") {
            return true;
        }
    }
    return false;
}

export const getUsername = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    if (profileData != null) {
        return profileData.username;
    }
    return null;

}

export const getUserId = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    if (profileData != null) {
        return profileData.id;
    }
    return null;
}

export const getBirthDate = () => {
    const profileData = JSON.parse(sessionStorage.getItem("profileData"));
    if (profileData != null) {
        return profileData.birthDate;
    }
    return null;
}

export const logout = () => {
    localStorage.clear();
    sessionStorage.clear();
}