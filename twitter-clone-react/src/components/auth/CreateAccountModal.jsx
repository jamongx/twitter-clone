import React, { useState, useEffect } from "react";
import "./CreateAccountModal.css";
import { useNavigate } from "react-router-dom";
import { registerAPICall } from "../../services/AuthService";
import DateSelectComponent from "../ui/DateSelectComponent";
import ErrorMessage from "../ui/ErrorMessage";
import Modal from "../ui/Modal";
import { HttpStatus } from "../../utils/HttpStatus";
import { Constants } from "../../utils/Constants";

const CreateAccountModal = ({ isOpen, onClose }) => {
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    displayName: "",
    email: "",
    bio: "",
    birthDate: "",
  });

  const navigate = useNavigate();

  // When modal open, reset error that could be set previously.
  useEffect(() => {
    if (isOpen) {
      setFormData({
        username: "",
        password: "",
        displayName: "",
        email: "",
        bio: "",
        birthDate: "",
      });
      setError("");
    }
  }, [isOpen]);

  const validateForm = () => {
    if (!formData.username.trim()) return "Username is required.";
    if (!formData.password.trim()) return "Password is required.";
    if (formData.password.length < Constants.MAX_PASSWORD)
      return `Password must be at least ${Constants.MAX_PASSWORD} characters.`;
    if (!formData.displayName.trim()) return "Display Name is required.";
    if (!formData.email.trim()) return "Email is required.";
    if (!/\S+@\S+\.\S+/.test(formData.email)) return "Email is invalid.";

    if (!formData.birthDate.trim()) return "Birthdate is required.";
    return ""; // No errors
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleChangeDate = (date) => {
    console.log("handleChangeDate, new birthDate=" + date);
    setFormData((prevState) => ({
      ...prevState,
      birthDate: date,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    const validationError = validateForm();
    if (validationError) {
      setError(validationError);
      return;
    }

    try {
      const response = await registerAPICall(formData);
      console.log("handleSubmit, response.status=" + response.status);
      if (response.status == HttpStatus.CREATED) {
        onClose();
        navigate("/login");
      }
    } catch (error) {
      setError("create account failure, " + error.message);
    }
  };

  if (!isOpen) return null;

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <div className="create-account-modal">
        <div className="modal-header">
          <button className="close-button" onClick={onClose}>
            ×
          </button>
          <h2>Create your account</h2>
        </div>
        <form className="modal-content" onSubmit={handleSubmit}>
          <input
            type="text"
            name="username"
            placeholder="Username"
            value={formData.username}
            onChange={handleChange}
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
          />
          <input
            type="text"
            name="displayName"
            placeholder="Diplay Name"
            value={formData.displayName}
            onChange={handleChange}
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
          />
          <textarea
            name="bio"
            placeholder="Introduce yourself"
            value={formData.bio}
            onChange={handleChange}
          />

          <DateSelectComponent
            value={formData.birthDate}
            onChange={handleChangeDate}
          />

          <button type="submit" className="next-button">
            Submit
          </button>

          <ErrorMessage error={error} />
        </form>
        <div className="modal-footer">
          <button className="use-email-button">Use email instead</button>
        </div>
      </div>
    </Modal>
  );
};

export default CreateAccountModal;
