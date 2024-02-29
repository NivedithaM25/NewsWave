import React, { Component, useState } from "react";
import "./Navbar.css";
import { Link, Navigate, useNavigate } from "react-router-dom";

class Navbar extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoggedIn: false,
      searchQuery: "",
      searchResults: [],
    };
  }

  componentDidMount() {
    // Check session storage for user login status
    const userName = sessionStorage.getItem("userName");
    const token = sessionStorage.getItem("token");
    if (userName && token) {
      // If user data exists in session storage, consider the user as logged in
      this.setState({ isLoggedIn: true });
    }
  }

  handleLogout = () => {
    sessionStorage.removeItem("userName");
    sessionStorage.removeItem("token");
    localStorage.clear();
    this.setState({ isLoggedIn: false }); // Update isLoggedIn state after logout
    return <Navigate to={"/"} />;
  };

  handleSearchChange = (event) => {
    this.setState({ searchQuery: event.target.value });
  };

  performSearch = () => {
    // Handle the search action here using this.state.searchQuery
    console.log("Performing search:", this.state.searchQuery);
    // You can add logic to fetch search results, etc.
  };

  callAPI = (category) => {
    console.log(category);
    //navigate("/error", { state: { category: category } });
    //<Navigate to={"/"} category={category} />
  };

  render() {
    const { login, sign } = this.state;
    const date = new Date();
    const options = {
      weekday: "short",
      year: "numeric",
      month: "short",
      day: "numeric",
    };
    const { isLoggedIn } = this.state;

    return (
      <>
        <div className="navbar_top">
          <div className="top1">
            <p>EDITION</p>
            <p>
              <img
                className="inflag"
                src="https://cdn.britannica.com/97/1597-004-05816F4E/Flag-India.jpg"
                alt="IN"
              />{" "}
              IN
            </p>
            <div className="navbar_dateTime">
              <p>
                {date.toLocaleDateString("en-US", options).toUpperCase()} |
                UPDATED {date.toLocaleTimeString()} IST
              </p>
            </div>
            <div className="navbar_cityTemp">
              <p>
                BANGALORE
                <span className="navbar_cityTemp_span" role={"img"}>
                  24°
                </span>
                C
              </p>
            </div>
          </div>
          <div className="top2">
            {!isLoggedIn && ( // Conditionally render Register and Login if user is not logged in
              <>
                <button className="btn " id="register">
                  <Link className="link" to="/Register">
                    <span className="material-symbols-outlined">&#xe174;</span>
                  </Link>
                </button>
                <button className="btn login" id="login">
                  <Link className="link" to="/Login">
                    <span className="material-symbols-outlined">&#xea77;</span>
                  </Link>
                </button>
              </>
            )}
            {isLoggedIn && ( // Conditionally render Logout and Wishlist if user is logged in
              <>
                <button className="btn" onClick={this.handleLogout} id="logout">
                  <Link className="link">
                    <span className="material-symbols-outlined">&#xe9ba;</span>
                  </Link>
                </button>
                <button className="btn" id="wishlist">
                  <Link className="link" to="/Wishlist">
                    <span className="material-symbols-outlined">&#xe87d;</span>
                  </Link>
                </button>
              </>
            )}
            {/* <button className="btn">
              <Link className="link" to="/Register">
                Register
              </Link>
            </button>
            <button className="btn">
              <Link className="link" to="/Login">
                Login
              </Link>
            </button>
            <button className="btn" onClick={() => this.handleLogout()}>
              <Link className="link">Logout</Link>
            </button>
            <button className="btn">
              <Link className="link" to="/Wishlist">
                WishList
              </Link>
            </button> */}
          </div>
        </div>
        <div className="main_heading">
          <h1 className="mainhead">THE NEWS WAVE</h1>
        </div>
        <div className="lower_navbar">
          <ul className="navbar_lower_ul">
            <Link to="/">
              <li className="NWplus" onClick={() => <Navigate to={"/"} />}>
                TNW +
              </li>{" "}
            </Link>
            <li onClick={() => this.callAPI("Business")}>Business</li>
            <li onClick={() => this.callAPI("Entertainment")}>Entertainment</li>
            <li onClick={() => this.callAPI("General")}>General</li>
            <li onClick={() => this.callAPI("Health")}>Health</li>
            <li onClick={() => this.callAPI("Science")}>Science</li>
            <li onClick={() => this.callAPI("Sports")}>Sports</li>
            <li onClick={() => this.callAPI("Technology")}>Technology</li>
            {/* <li>
              <img
                src="https://cdn2.iconfinder.com/data/icons/bold-ui/100/search-512.png"
                alt="🔍"
                className="searchlogo searchlogoleft"
              />
            </li>
            <li>
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7oY3cyemDvx6JZjyy-q-pQxoZD9ea0O5Lsg&usqp=CAU"
                alt="☰"
                className="searchlogo"
              />
            </li> */}
            <li>
              <input
                type="text"
                placeholder="Search..."
                className="searchInput"
                // Handle the search functionality here (e.g., onChange event)
                // onChange={(e) => handleSearch(e.target.value)}
              />
            </li>
            <li>
              <button onClick={this.performSearch}>Search</button>
            </li>
          </ul>
        </div>
      </>
    );
  }
}

export default Navbar;
