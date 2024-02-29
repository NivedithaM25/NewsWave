import { useEffect, useState } from "react";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import Article from "../Article/Article";
import NewsService from "../service/NewsService";
import { Link, Navigate, useNavigate } from "react-router-dom";
import "./WishList.css";

export default function Wishlist() {
  const navigate = useNavigate();
  const [articles, setArticles] = useState(null);
  const [loading, setLoading] = useState(true);
  let home = false;

  useEffect(() => {
    setLoading(true);
    // console.log("IN WISHLIST");
    NewsService.getAllWishList()
      .then((response) => {
        //console.log(response);
        setArticles(response.data);
        setLoading(false);
      })
      .catch((error) => {
        // console.log("In Catch");
        // console.log(error);
        navigate("/error", { state: { errorDetails: error.message } });
      });
  }, [navigate]);

  return (
    <div>
      <Navbar />
      <div className="wishlist">
        {!loading && (
          <div className="articles">
            {articles.length > 0 ? (
              articles.map((article, id) => (
                <Article
                  key={id}
                  id={article.id}
                  description={article.description}
                  title={article.title}
                  url={article.url}
                  imageUrl={article.imageUrl}
                  category={article.category}
                  home={home}
                />
              ))
            ) : (
              <div className="emptywishlist">
                <h1 className="orangeText">Oops...!</h1>
                <p>Your Wish list is currently empty</p>
                <Link to="/">
                  <p className="NWplus" onClick={() => <Navigate to={"/"} />}>
                    Add to Wish list
                  </p>{" "}
                </Link>
              </div>
            )}
          </div>
        )}
      </div>
      <Footer />
    </div>
  );
}
