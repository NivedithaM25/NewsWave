import Form from "react-bootstrap/Form";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Footer from "../Footer/Footer";
import "./Login.css";
import NewsService from "../service/NewsService";
import Navbar from "../Navbar/Navbar";

export default function Login() {
  const navigate = useNavigate();
  const [errorMessage, seterrorMessage] = useState("");
  const [isError, setisError] = useState(false);
  const [login, setLogin] = useState({
    userName: "",
    password: "",
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setLogin({ ...login, [name]: value });
  };

  const handleLogin = (event) => {
    event.preventDefault();
    NewsService.login(login)
      .then((response) => {
        sessionStorage.setItem("token", "Bearer " + response.data.token);
        sessionStorage.setItem("userName", login.userName);
        navigate("/");
      })
      .catch((error) => {
        // alert(error.response.data);
        seterrorMessage("Password incorrect!");
        setisError(true);
        // console.log(error);
        // console.log(error.response.data);
      });
  };

  return (
    <>
      <Navbar />
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-4 shadow p-4">
            <h2 className="text-center mb-4">SIGN IN</h2>
            {isError && (
              <p className="alert alert-danger text-center">{errorMessage}</p>
            )}
            <form>
              <div className="mb-3">
                <label htmlFor="firstName" className="form-label">
                  UserName
                </label>
                <input
                  id="userName"
                  name="userName"
                  type="text"
                  required
                  // placeholder='ex: manoj@gmail.com'
                  className="form-control"
                  value={login.userName}
                  onChange={(e) => handleChange(e)}
                />
              </div>

              <div className="flex mb-4">
                <label htmlFor="password" className="form-label">
                  Password
                </label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  required
                  // placeholder="Enter your password"
                  value={login.password}
                  onChange={(e) => handleChange(e)}
                  className="form-control"
                />
              </div>

              <div className="items-center justify-center h-14 w-full my-4 space-x-4 pt-4">
                <button onClick={handleLogin} className="btn btn-primary m-4">
                  Sign In
                </button>
                <button
                  onClick={() => navigate("/")}
                  className="btn btn-primary"
                >
                  Back
                </button>
                <p>
                  Don't have an account yet?{" "}
                  <Link to={"/register"}>Sign Up</Link>
                </p>
                {/* <botton className="btn btn-primary">Test</botton> */}
              </div>
            </form>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}
