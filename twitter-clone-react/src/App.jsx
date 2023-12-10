import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavigationComponent from './components/navigation/NavigationComponent';
import LoginComponent from './components/auth/LoginComponent';
import ExploreComponent from './components/explore/ExploreComponent';
import NotificationsComponent from './components/notifications/NotificationsComponent';
import MessagesComponent from './components/messages/MessagesComponent';
import NotAuthRoute from './components/routing/NotAuthRoute';
import AuthRoute from './components/routing/AuthRoute';
import ProfileComponent from './components/profile/ProfileComponent';
import FollowTabsComponent from './components/followers/FollowHomeComponent';
import TweetHomeComponent from './components/tweet/TweetHomeComponent';

function App() {

  return (
    <BrowserRouter>
      <div className="app">
        <NavigationComponent />
        <div className="main-content">
          <Routes>
            <Route path="/" element={ <AuthRoute><TweetHomeComponent /></AuthRoute> } />
            <Route path="/home" element={ <AuthRoute><TweetHomeComponent /></AuthRoute> } />
            <Route path="/explore" element={ <AuthRoute><ExploreComponent /></AuthRoute> } />
            <Route path="/notifications" element={ <AuthRoute> <NotificationsComponent /> </AuthRoute> } />
            <Route path="/messages" element={<AuthRoute> <MessagesComponent /> </AuthRoute> } />
            <Route path="/profile" element={<AuthRoute> <ProfileComponent /> </AuthRoute> } />
            <Route path="/follows" element={ <AuthRoute> <FollowTabsComponent /> </AuthRoute> } />
            <Route path="/login" element={ <NotAuthRoute> <LoginComponent /> </NotAuthRoute> } />
            {/* You can add other routes */}
          </Routes>
        </div>
        {/* You can place the sidebar or additional components here. */}
      </div>
    </BrowserRouter>
  );
}

export default App;
