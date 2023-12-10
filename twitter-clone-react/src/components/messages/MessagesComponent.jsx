import React from "react";
import "./MessagesComponent.css";

const MessagesComponent = () => {
  const handleSendMessage = () => {
    // logic to send messages
  };

  return (
    <div className="messages-container">
      <div className="messages-content">
        <h2>Welcome to your inbox!</h2>
        <p>
          Drop a line, share posts and more with private conversations between
          you and others on X.{" "}
        </p>
        <button className="messages-button" onClick={handleSendMessage}>
          Write a message
        </button>
      </div>
    </div>
  );
};

export default MessagesComponent;
