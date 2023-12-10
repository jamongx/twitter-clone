import React from 'react'
import { isUserLoggedIn } from '../../services/AuthService';

const AuthDisplay = ({children}) => {
  const isAuth = isUserLoggedIn();
  if (isAuth) {
    return children;
  }
    return <></>;

}

export default AuthDisplay