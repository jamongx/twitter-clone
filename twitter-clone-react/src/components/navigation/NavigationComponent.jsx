import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { logout } from "../../services/AuthService";
import "./NavigationComponent.css";
import AuthDisplay from "../auth/AuthDisplay";
import NotAuthDisplay from "../auth/NotAuthDisplay";

const NavigationComponent = () => {
  const navigator = useNavigate();

  const handleLogout = () => {
    logout();
    navigator("/login");
  };

  return (
    <div className="navigation">
      <div className="nav-logo">
        <Link to="/">
          <i className="fab fa-twitter"></i>
        </Link>
      </div>
      <ul className="nav-links">
        <AuthDisplay>
          <li>
            <Link to="/home">
              <i className="fas fa-home"></i> Home
            </Link>
          </li>
          <li>
            <Link to="/explore">
              <i className="fas fa-hashtag"></i> Explore
            </Link>
          </li>
          <li>
            <Link to="/notifications">
              <i className="fas fa-bell"></i> Notifications
            </Link>
          </li>
          <li>
            <Link to="/messages">
              <i className="fas fa-envelope"></i> Messages
            </Link>
          </li>
          <li>
            <Link to="/follows">
              <i className="fas fa-user-plus"></i> Follows
            </Link>
          </li>
          <li>
            <Link to="/profile">
              <i className="fas fa-user"></i> Profile
            </Link>
          </li>
          <li>
            <Link to="/login" onClick={handleLogout}>
              <i className="fas fa-sign-out-alt"></i> Logout
            </Link>
          </li>
        </AuthDisplay>
        <NotAuthDisplay>
          <li>
            <Link to="/login">
              <i className="fas fa-sign-in-alt"></i> Login
            </Link>
          </li>
        </NotAuthDisplay>
      </ul>
      {/* You can add a tweet button or the user's profile image here. */}
    </div>
  );
};

export default NavigationComponent;
