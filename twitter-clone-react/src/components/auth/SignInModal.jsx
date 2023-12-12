import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { loginAPICall } from "../../services/AuthService";
import { storeTokenToLocalStorage } from "../../storage/LocalStorage";
import { storeProfileDataToSessionStorage } from "../../storage/SessionStorage";
import "./SignInModal.css";
import Modal from "../ui/Modal";
import ErrorMessage from "../ui/ErrorMessage";
import { HttpStatus } from "../../utils/HttpStatus";

const SignInModal = ({ isOpen, onClose, onSignUpClick }) => {
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    usernameOrEmail: "",
    password: "",
  });

  const navigate = useNavigate();

  // When modal open, reset error that could be set previously.
  useEffect(() => {
    if (isOpen) {
      setFormData({
        usernameOrEmail: "",
        password: "",
      });
      setError("");
    }
  }, [isOpen]);

  const validateForm = () => {
    if (!formData.usernameOrEmail.trim()) return "usernameOrEmail is required.";
    if (!formData.password.trim()) return "Password is required.";
    return ""; // No errors
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
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
      const response = await loginAPICall(formData);
      if (response.status == HttpStatus.OK) {
        const profileData = response.data;
        const token = profileData.tokenType + " " + profileData.accessToken;
        storeTokenToLocalStorage(token);
        storeProfileDataToSessionStorage(profileData);
        onClose();
        navigate("/home");
      }
    } catch (error) {
      setError("login failure, " + error.message);
    }
  };

  const handleSignUpClick = (e) => {
    e.preventDefault();
    onSignUpClick(); // CreateAccountModal 열기
  };

  if (!isOpen) return null;

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <div className="sign-in-modal">
        <div className="modal-header">
          <button className="close-button" onClick={onClose}>
            x
          </button>
          <h2>Sign in Twitter (Clone)</h2>
        </div>
        <button className="sign-in-button google">Sign in with Google</button>
        <button className="sign-in-button apple">Sign in with Apple</button>
        <div className="divider">or</div>
        <form className="modal-content" onSubmit={handleSubmit}>
          <input
            type="text"
            name="usernameOrEmail"
            placeholder="Email or Username"
            value={formData.usernameOrEmail}
            onChange={handleChange}
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
          />

          <button type="submit" className="next-button">
            Submit
          </button>

          <ErrorMessage error={error} />

          <a href="#" className="forgot-password">
            Forgot password?
          </a>
        </form>
        <div className="modal-footer">
          Don't have an account?{" "}
          <a href="#" className="sign-up-link" onClick={handleSignUpClick}>
            Sign up
          </a>
        </div>
      </div>
    </Modal>
  );
};

export default SignInModal;
