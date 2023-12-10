import React, { useState } from "react";
import "./LoginComponent.css";
import SignInModal from "./SignInModal";
import CreateAccountModal from "./CreateAccountModal";

const LoginComponent = () => {
  const [isSignInModalOpen, setIsSignInModalOpen] = useState(false);
  const openSignInModal = () => setIsSignInModalOpen(true);
  const closeSignInModal = () => setIsSignInModalOpen(false);

  const [isCreateAccountModalOpen, setIsCreateAccountModalOpen] =
    useState(false);
  const openCreateAccountModal = () => {
    closeSignInModal();
    setIsCreateAccountModalOpen(true);
  };
  const closeCreateAccountModal = () => setIsCreateAccountModalOpen(false);

  return (
    <div className="login-container">
      <div className="login-content">
        <h1>Happening now</h1>
        <h2>Join today.</h2>
        <button className="login-button google">Sign Up with Google</button>
        <button className="login-button apple">Sign Up with Apple</button>
        <div className="divider">or</div>
        <div>
          <button
            className="login-button create-account"
            onClick={openCreateAccountModal}
          >
            Create account
          </button>
          <CreateAccountModal
            isOpen={isCreateAccountModalOpen}
            onClose={closeCreateAccountModal}
          />
        </div>
        <p className="terms">
          By signing up, you agree to the Terms of Service and Privacy Policy,
          including Cookie Use.
        </p>
        <div className="login-footer">
          Already have an account?{" "}
          <button className="sign-in" onClick={openSignInModal}>
            Sign in
          </button>
          <SignInModal
            isOpen={isSignInModalOpen}
            onClose={closeSignInModal}
            onSignUpClick={openCreateAccountModal}
          />
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
