import React, { useState, useEffect } from "react";
import { getFollowing } from "../../services/FollowService";
import { getUserId } from "../../storage/SessionStorage";
import FollowComponent from "./FollowComponent";
import "./FollowingComponent.css";

const FollowingComponent = () => {
  const id = getUserId();
  const [following, setFollowing] = useState([]);

  useEffect(() => {
    fetchFollowing();
  }, []);

  const fetchFollowing = async () => {
    try {
      const response = await getFollowing(id);
      setFollowing(response.data);
    } catch (error) {
      console.error(error);
      // You can add additional error handling logic here.
    }
  };

  // Function to toggle the follow status
  const toggleFollowing = (id) => {
    // Implement logic to change following status
  };

  return (
    <div className="following-list">
      {following.map((follow) => (
        <FollowComponent
          key={follow.id}
          followOne={follow}
          onToggleFollow={toggleFollowing}
        />
      ))}
    </div>
  );
};

export default FollowingComponent;
