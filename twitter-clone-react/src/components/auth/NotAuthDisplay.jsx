import React from "react";
import { isUserLoggedIn } from "../../storage/SessionStorage";

const NotAuthDisplay = ({ children }) => {
  const isAuth = isUserLoggedIn();
  if (!isAuth) {
    return children;
  }
  return <></>;
};

export default NotAuthDisplay;
