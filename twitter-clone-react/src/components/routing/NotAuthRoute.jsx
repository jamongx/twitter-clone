import React from 'react'
import { Navigate } from 'react-router-dom';
import { isUserLoggedIn } from '../../services/AuthService';

const NotAuthRoute = ({children}) => {
  const isAuth = isUserLoggedIn();
  if (!isAuth) {
    return children;
  }
  else {
    return <Navigate to="/home" />;
  }
}

export default NotAuthRoute