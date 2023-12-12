import React, { useState } from "react";
//import React, { useState, useEffect } from "react";
import ProfileEditModal from "./ProfileEditModal";
import { getProfileDataFromSessionStorage } from "../../storage/SessionStorage";

import "./ProfileComponent.css";

const ProfileComponent = () => {
  const followingCount = 0;
  const followersCount = 0;

  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [profileData, setProfileData] = useState(
    getProfileDataFromSessionStorage()
  );

  // // For debug
  // useEffect(() => {
  //   console.log(
  //     "original profileData(JSON.parse)=" + JSON.stringify(profileData)
  //   );
  // }, [profileData]);

  const handleChange = (updatedProfileData) => {
    setProfileData(updatedProfileData);
  };

  const openEditModal = () => setIsEditModalOpen(true);
  const closeEditModal = () => setIsEditModalOpen(false);

  return (
    <div className="profile-container">
      <div className="profile-header">{/* profile header image */}</div>
      <div className="profile-info">
        <img
          src={profileData.avatarUrl}
          alt="avatarUrl"
          className="profile-avatar"
        />
        <h2 className="profile-name">{profileData.displayName}</h2>
        <p className="profile-username">@{profileData.username}</p>
        <p className="profile-bio">Bio: {profileData.bio}</p>
        <p className="profile-details">
          <span className="profile-birthdate">
            Birth Date: {profileData.birthDate}
          </span>
          {" · "}
          <span className="profile-joindate">
            Join Date: {profileData.createdAt.split("T")[0]}
          </span>
        </p>
        <p className="profile-stats">
          {followingCount} Following {" · "} {followersCount} Followers
        </p>
        <p className="profile-stats">
          Account Status: {profileData.active ? "Active" : "Deactive"}
        </p>
        <button className="profile-edit-button" onClick={openEditModal}>
          Edit Profile
        </button>
        {isEditModalOpen && (
          <ProfileEditModal
            isOpen={isEditModalOpen}
            onClose={closeEditModal}
            profileData={profileData}
            onChange={handleChange}
          />
        )}
      </div>
    </div>
  );
};

export default ProfileComponent;
