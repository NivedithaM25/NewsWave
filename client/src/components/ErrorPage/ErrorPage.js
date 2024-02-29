import React from "react";
import { Link, Navigate, useLocation } from "react-router-dom";
import errorImage from "./errorImage.jpg";

const ErrorPage = () => {
  const location = useLocation();
  const { errorDetails } = location.state || {}; // Access errorDetails from location.state

  return (
    <div
      className="error_container"
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "100vh",
      }}
    >
      <h1>{errorDetails || "Something went wrong!!"}</h1>
      <p>
        {" "}
        Back to{" "}
        <Link to="/">
          <p className="NWplus" onClick={() => <Navigate to={"/"} />}>
            Home
          </p>
        </Link>
      </p>

      <img
        src={errorImage} // Replace with your image URL
        alt="Person reading a newspaper"
        style={{
          maxWidth: "100%",
          height: "auto",
          display: "block",
          margin: "5px auto",
        }}
      />

      {/* Additional error information or custom styling can be added */}
    </div>
  );
};

export default ErrorPage;
