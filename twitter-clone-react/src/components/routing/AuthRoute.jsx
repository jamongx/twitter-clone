import React from 'react'
import { Navigate } from 'react-router-dom';
import { isUserLoggedIn } from '../../services/AuthService';

const AuthRoute = ({children}) => {
  const isAuth = isUserLoggedIn();
  if (isAuth) {
    return children;
  }
  else {
    return <Navigate to="/login" />;
  }

}

export default AuthRoute