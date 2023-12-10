import React from "react";
import "./ExploreComponent.css";

const ExploreComponent = () => {
  // In a real application, you would need logic here to fetch explore content data.

  return (
    <div className="explore-container">
      <div className="explore-header">
        <h1>Explore</h1>
      </div>
      <div className="explore-content">
        {/* Sample Image */}
        <img src="valorant-game-changers.jpg" alt="VALORANT Game Changers" />
        <h2>VALORANT Game Changers</h2>
        {/* Render list of posts, users, tags, etc. */}
        {/* Example list item */}
        <div className="explore-item">
          <p>Trevor Lawrence</p>
          <p>Bengals, #CINvsJAX</p>
        </div>
        <div className="explore-item">
          <p>Nathan Rourke</p>
          <p>VALORANT</p>
        </div>
        {/* More content items */}
      </div>
    </div>
  );
};

export default ExploreComponent;
