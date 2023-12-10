import React, { useState } from "react";
import TweetFollowingComponent from "./TweetFollowingComponent";
import TweetForYouComponent from "./TweetForYouComponent";
import "./TweetHomeComponent.css";

function TweetHomeComponent() {
  const [activeTab, setActiveTab] = useState("forYou");

  return (
    <div className="tweet-home-component">
      <div className="tabs">
        <button
          className={`tab ${activeTab === "forYou" ? "active" : ""}`}
          onClick={() => setActiveTab("forYou")}
        >
          For you
        </button>
        <button
          className={`tab ${activeTab === "following" ? "active" : ""}`}
          onClick={() => setActiveTab("following")}
        >
          Following
        </button>
      </div>
      <div className="content">
        {activeTab === "forYou" ? (
          <TweetForYouComponent />
        ) : (
          <TweetFollowingComponent />
        )}
      </div>
    </div>
  );
}

export default TweetHomeComponent;
