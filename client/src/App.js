import "./App.css";
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./components/Register/Register";
import Wishlist from "./components/WishList/WishList";
import ErrorPage from "./components/ErrorPage/ErrorPage";

function App() {
  return (
    <>
      {/* <BrowserRouter>
        <Navbar />

        <div>Content</div>
        <Footer />
      </BrowserRouter> */}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Register" element={<Register />} />
          <Route path="/Wishlist" element={<Wishlist />} />
          <Route path="/error" element={<ErrorPage />} />
        </Routes>
      </BrowserRouter>
    </>

    // <div className="App">
    //   <Navbar/>
    //   <div>
    //     DashBoard Here!!!!!!
    //   </div>
    //   <Footer/>
    // </div>
  );
}

export default App;
