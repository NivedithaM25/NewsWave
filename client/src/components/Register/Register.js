import Form from "react-bootstrap/Form";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Header from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import "./Register.css";
import NewsService from "../service/NewsService";

export default function Register() {
  const navigate = useNavigate();
  const [errorMessage, seterrorMessage] = useState("");
  const [isError, setisError] = useState(false);
  const [register, setRegister] = useState({
    userName: "",
    email: "",
    password: "",
  });

  //console.log(register);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setRegister({ ...register, [name]: value });
  };

  const checkConstraints = () => {
    const usernamePattern = /^[A-Z](?=.*\d)[A-Za-z\d]{5,10}$/;
    if (!usernamePattern.test(register.userName)) {
      const constraintsMessage =
        "Username should follow these constraints:\n" +
        "- Start with a capital letter.\n" +
        "- Contain at least one numeric value.\n" +
        "- Be between 5 and 10 characters in length.";

      seterrorMessage(constraintsMessage);
      setisError(true);
      return false;
    }
    if (register.userName === null || register.userName === undefined) {
      seterrorMessage("UserName should not be empty!");
      setisError(true);
      return false;
    }
    return true;
  };

  const handleRegister = (event) => {
    event.preventDefault();
    if (checkConstraints()) {
      NewsService.register(register)
        .then((response) => {
          //console.log("inside successful reg" + response.data);
          seterrorMessage("Successfully Registered!");
          setisError(false); // Setting isError to false upon successful registration
          navigate("/login");
        })
        .catch((error) => {
          console.log("Error:" + error); // Check the error object in the console
          seterrorMessage(error);
          setisError(true);
        });
    }
  };

  return (
    <>
      <Header />
      <div className="container mt-1">
        <div className="row justify-content-center">
          <div className="col-md-4 shadow p-4 ">
            <h2 className="text-center mb-4">Sign Up</h2>
            {isError && (
              <p className="alert alert-danger text-center">{errorMessage}</p>
            )}
            <form onSubmit={handleRegister}>
              <div className="mb-3">
                <label htmlFor="userName" className="form-label">
                  User Name
                </label>
                <input
                  type="text"
                  id="userName"
                  name="userName"
                  required
                  className="form-control"
                  value={register.userName}
                  onChange={handleChange}
                  onInvalid={(e) =>
                    e.target.setCustomValidity("Username should not be empty.")
                  }
                  onInput={(e) => e.target.setCustomValidity("")}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Email
                </label>
                <input
                  type="email"
                  id="email"
                  name="email"
                  autoComplete="email"
                  required
                  className="form-control"
                  value={register.email}
                  onChange={handleChange}
                  onInvalid={(e) =>
                    e.target.setCustomValidity("Email should not be empty.")
                  }
                  onInput={(e) => e.target.setCustomValidity("")}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  required
                  className="form-control"
                  value={register.password}
                  onChange={handleChange}
                  onInvalid={(e) =>
                    e.target.setCustomValidity("Password should not be empty.")
                  }
                  onInput={(e) => e.target.setCustomValidity("")}
                />
              </div>
              <p>
                By creating an account, you agree with our{" "}
                <a href="" target="_blank">
                  Terms & Conditions and Privacy Policy.
                </a>
              </p>
              <div className="items-center justify-center h-14 w-full">
                <button type="submit" className="btn btn-danger m-4">
                  Sign Up
                </button>
                <button
                  type="button"
                  onClick={() => navigate("/")}
                  className="btn btn-secondary m-4"
                >
                  Back
                </button>

                <p>
                  Alreay have an account? <Link to={"/login"}>Sign In</Link>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}
