import React from "react";
import { isUserLoggedIn } from "../../storage/SessionStorage";

const AuthDisplay = ({ children }) => {
  const isAuth = isUserLoggedIn();
  if (isAuth) {
    return children;
  }
  return <></>;
};

export default AuthDisplay;
