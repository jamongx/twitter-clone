import React from "react";
import "./TweetComponent.css";

function TweetComponent({ tweetData }) {
  const {
    id,
    avatarUrl,
    displayName,
    username,
    content,
    imageUrl,
    replys,
    likes,
  } = tweetData;

  // Functions to handle reply, repost, and like buttons (actual implementation needed)
  const handleReply = () => {
    /* Logic for reply */
  };
  const handleRepost = () => {
    /* Logic for repost */
  };
  const handleLike = () => {
    /* Logic for like */
  };

  return (
    <div className="tweet-component">
      <img
        src={avatarUrl}
        alt={`${username}'s avatar`}
        className="tweet-avatar"
      />
      <div className="tweet-content">
        <strong>{displayName}</strong>
        <br />
        <strong>@{username}</strong>
        <br />
        <p>{content}</p>
        <br />
        <img src={imageUrl}></img>
        <div className="tweet-actions">
          <button onClick={handleReply}>Reply {replys}</button>
          <button onClick={handleRepost}>Repost</button>
          <button onClick={handleLike}>Like {likes}</button>
        </div>
      </div>
    </div>
  );
}

export default TweetComponent;
