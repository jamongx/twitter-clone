
export const clearSessionStorage = () => {
  sessionStorage.clear();
}

export const storeProfileDataToSessionStorage = (profileData) => {
  sessionStorage.setItem("profileData", JSON.stringify(profileData));
}

export const getProfileDataFromSessionStorage = () => {
  const profileData = JSON.parse(sessionStorage.getItem("profileData"));
  return profileData;
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

