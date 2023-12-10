import React, { useState } from "react";
import "./TweetPostComponent.css";

function TweetPostComponent() {
  const [tweet, setTweet] = useState("");

  const handleInputChange = (e) => {
    setTweet(e.target.value);
  };

  const handlePost = () => {
    {
      /* post logic */
    }
    console.log("Tweet Posted:", tweet);
    setTweet("");
  };

  return (
    <div className="tweet-post-component">
      <img
        src="/images/twitter-default-avatar.jpg"
        alt="user avatar"
        className="user-avatar"
      />
      <textarea
        value={tweet}
        onChange={handleInputChange}
        placeholder="What's happening?"
        className="tweet-textarea"
      />
      <div className="tweet-actions">
        <button onClick={() => {}}>📷</button>
        <button onClick={() => {}}>😊</button>
        <button onClick={handlePost}>Post</button>
      </div>
    </div>
  );
}

export default TweetPostComponent;
