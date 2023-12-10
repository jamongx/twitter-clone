import React, { useState, useEffect } from "react";
import TweetComponent from "./TweetComponent";
import "./TweetDetailComponent.css";

function TweetDetailComponent({ tweetData }) {
  const { userAvatar, username, tweetContent, likes } = tweetData;
  const [replies, setReplies] = useState([]);
  const [reply, setReply] = useState("");

  useEffect(() => {
    const fetchReplies = async () => {
      try {
        const response = await fetch(
          `https://api.example.com/tweets/${tweetData.id}/replies`
        );
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        setReplies(data);
      } catch (error) {
        console.error("Error fetching replies:", error);
      }
    };

    fetchReplies();
  }, [tweetData.id]);

  // logic to send reply
  const handleSendReply = () => {
    console.log("Reply:", reply);
    // logic to build reply
    setReply(""); // init input field
  };

  return (
    <div className="tweet-detail-component">
      <div className="tweet">
        <img
          src={userAvatar}
          alt={`${username}'s avatar`}
          className="tweet-avatar"
        />
        <div className="tweet-content">
          <strong>{username}</strong>
          <p>{tweetContent}</p>
          <div className="tweet-actions">
            <span>Like {likes}</span>
          </div>
        </div>
      </div>
      <div className="tweet-reply-input">
        <textarea
          value={reply}
          onChange={(e) => setReply(e.target.value)}
          placeholder="Write your reply..."
        />
        <button onClick={handleSendReply}>Send Reply</button>
      </div>
      <div className="tweet-replies">
        {replies.map((replyData) => (
          <TweetComponent key={replyData.id} tweetData={replyData} />
        ))}
      </div>
    </div>
  );
}

export default TweetDetailComponent;
