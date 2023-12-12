import { useState } from "react";
import { patchUserProfileAvatar } from "../../services/UserProfileService";
import "./FileUploadComponent.css";
import ErrorMessage from "../ui/ErrorMessage";
import { HttpStatus } from "../../utils/HttpStatus";

const FileUploadComponent = ({ id, value, onChange }) => {
  const [error, setError] = useState("");
  const [file, setFile] = useState(null);
  const [isUploaded, setIsUploaded] = useState(false);

  const handleChoose = (e) => {
    setIsUploaded(false);
    const file = e.target.files[0];
    if (file) {
      setFile(file);
      console.log("handleChoose=" + file.name);
    }
  };

  const handleUpload = async (e) => {
    setError("");
    if (file && !isUploaded) {
      try {
        const formData = new FormData();
        formData.append("file", file);
        const response = await patchUserProfileAvatar(id, formData);
        if (response.status == HttpStatus.OK) {
          console.log(
            "handleUpload(response.data.avatarUrl)=" + response.data.avatarUrl
          );
          onChange(response.data.avatarUrl);
          setIsUploaded(true);
        }
      } catch (error) {
        setError("uploading image failure, " + error.message);
      }
    }
    console.log("handleUpload(file.name)=" + file.name);
  };

  return (
    <div className="image-upload-section">
      <input type="file" onChange={handleChoose} accept="image/*" />
      <img
        className="image-preview"
        src={file ? URL.createObjectURL(file) : value}
        alt={file ? "Preview" : "avatarUrl"}
      />
      <br />
      <button
        type="button" // no submit event
        className="image-upload-button"
        onClick={handleUpload}
        disabled={!file || isUploaded}
      >
        {isUploaded ? "Uploaded" : "Upload Image"}
      </button>
      <ErrorMessage error={error} />
    </div>
  );
};

export default FileUploadComponent;
