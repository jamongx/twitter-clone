import React, { useState } from 'react';
import FollowersComponent from './FollowersComponent';
import FollowingComponent from './FollowingComponent';
import './FollowHomeComponent.css';

const FollowTabsComponent = () => {
  const [activeTab, setActiveTab] = useState('following');

  return (
    <div className="follow-tabs-container">
      <div className="tabs">
        <div
          className={`tab ${activeTab === 'followers' ? 'active' : ''}`}
          onClick={() => setActiveTab('followers')}
        >
          Followers
        </div>
        <div
          className={`tab ${activeTab === 'following' ? 'active' : ''}`}
          onClick={() => setActiveTab('following')}
        >
          Following
        </div>
      </div>
      <div className="tab-content">
        {activeTab === 'following' && <FollowingComponent />}
        {activeTab === 'followers' && <FollowersComponent />}
      </div>
    </div>
  );
};

export default FollowTabsComponent;