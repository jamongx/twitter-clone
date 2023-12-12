import React, { useState, useEffect } from "react";
import { getFollowers } from "../../services/FollowService";
import { getUserId } from "../../storage/SessionStorage";
import FollowComponent from "./FollowComponent";
import "./FollowersComponent.css";

const FollowersComponent = () => {
  const id = getUserId();
  const [followers, setFollowers] = useState([]);

  useEffect(() => {
    fetchFollowers();
  }, []);

  const fetchFollowers = async () => {
    try {
      const response = await getFollowers(id);
      setFollowers(response.data);
      console.log(response.data);
    } catch (error) {
      console.error(error);
      // Additional error handling logic can be added here.
    }
  };

  // Assuming a similar toggle follow functionality as in FollowingComponent
  const toggleFollowers = (id) => {
    // Implement the follow/unfollow logic here
  };

  return (
    <div className="followers-list">
      {followers.map((follow) => (
        <FollowComponent
          key={follow.id}
          followOne={follow}
          onToggleFollow={toggleFollowers}
        />
      ))}
    </div>
  );
};

export default FollowersComponent;
