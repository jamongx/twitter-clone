import React from 'react';
import './FollowComponent.css';

const FollowComponent = ({ followOne, onToggleFollow }) => {
  return (
    <div className="follow-item">
      <img src={followOne.avatarUrl} alt={followOne.username} className="avatar" />
      {/* <img src={'/images/twitter-default-avatar.jpg'} alt={followOne.username} className="avatar" /> */}
      <div className="user-info">
        <h3 className="displayName">{followOne.displayName}</h3>
        <p className="username">@{followOne.username}</p>
        <p className="bio">{followOne.bio}</p>
      </div>
      <button onClick={() => onToggleFollow(followOne.id)} className="follow-button">
        {followOne.isFollowing ? 'Following' : 'Follow'}
      </button>
    </div>
  );
};

export default FollowComponent;
