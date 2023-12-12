
export const clearLocalStorage = () => {
  localStorage.clear();
}

export const storeTokenToLocalStorage = (token) => localStorage.setItem("token", token);

export const getTokenFromLocalStorage = () => localStorage.getItem("token");
