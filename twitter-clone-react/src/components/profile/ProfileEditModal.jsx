import React, { useState, useEffect } from "react";
import DateSelectComponent from "../ui/DateSelectComponent";
import FileUploadComponent from "../ui/FileUploadComponent";
import Modal from "../ui/Modal";
import { storeProfileDataToSessionStorage } from "../../storage/SessionStorage";
import { updateUserProfile } from "../../services/UserProfileService";
import ErrorMessage from "../ui/ErrorMessage";
import "./ProfileEditModal.css";
import { HttpStatus } from "../../utils/HttpStatus";

const ProfileEditModal = ({ isOpen, onClose, profileData, onChange }) => {
  const [error, setError] = useState("");
  const [editedProfileData, setEditedProfileData] = useState(profileData);

  // When modal open, reset error that could be set previously.
  useEffect(() => {
    if (isOpen) {
      setError("");
    }
  }, [isOpen]);

  const validateProfileData = () => {
    if (!editedProfileData.displayName.trim())
      return "Display name is required.";
    return ""; // No errors
  };

  const handleChange = (e) => {
    setEditedProfileData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }));
  };

  const handleChangeBool = (e) => {
    setEditedProfileData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value === "true",
    }));
  };

  const handleChangeDate = (date) => {
    console.log("handleChangeDate, new birthDate=" + date);
    setEditedProfileData((prevState) => ({
      ...prevState,
      birthDate: date,
    }));
  };

  const handleChangeUrl = (url) => {
    console.log("handleChangeUrl, new avatarUrl=" + url);
    setEditedProfileData((prevState) => ({
      ...prevState,
      avatarUrl: url,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    const validationError = validateProfileData();
    if (validationError) {
      setError(validationError);
      return;
    }

    try {
      const response = await updateUserProfile(editedProfileData);
      if (response.status == HttpStatus.OK) {
        storeProfileDataToSessionStorage(editedProfileData);
        console.log(
          "handleSubmit, storeProfileDataToSession=" +
            JSON.stringify(editedProfileData)
        );
        onChange(editedProfileData);
        onClose(); // Close ProfileEdit Modal
      }
    } catch (error) {
      setError("profile edit failure, " + error.message);
    }
  };

  if (!isOpen) return null;

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <div className="profile-edit-modal">
        <div className="modal-header">
          <button className="close-button" onClick={onClose}>
            ×
          </button>
          <h2>Edit Your Profile</h2>
        </div>
        <form className="modal-content" onSubmit={handleSubmit}>
          <FileUploadComponent
            id={editedProfileData.id}
            value={editedProfileData.avatarUrl}
            onChange={handleChangeUrl}
          />

          <input
            type="text"
            name="displayName"
            value={editedProfileData.displayName}
            onChange={handleChange}
            placeholder="Enter your Display name"
          />

          <textarea
            name="bio"
            value={editedProfileData.bio}
            onChange={handleChange}
            placeholder="Enter your Bio"
          />

          <DateSelectComponent
            value={editedProfileData.birthDate}
            onChange={handleChangeDate}
          />

          <select
            name="active"
            value={String(editedProfileData.active)}
            onChange={handleChangeBool}
          >
            <option value="true">Active</option>
            <option value="false">Deactive</option>
          </select>

          <button type="submit" className="save-changes-button">
            Save Changes
          </button>

          <ErrorMessage error={error} />
        </form>
      </div>
    </Modal>
  );
};

export default ProfileEditModal;
