import React from 'react'
import { isUserLoggedIn } from '../../services/AuthService';

const NotAuthDisplay = ({children}) => {
  const isAuth = isUserLoggedIn();
  if (!isAuth) {
    return children;
  }
    return <></>;

}

export default NotAuthDisplay