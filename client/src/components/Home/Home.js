// import Article from "./Article";
import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import Header from "../Navbar/Navbar";
import Article from "../Article/Article";
import NewsService from "../service/NewsService";
import { useNavigate } from "react-router-dom";
import Navbar from "../Navbar/Navbar";

export default function Home() {
  const [articles, setArticles] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const home = true;
  useEffect(() => {
    // NewsService.getBusinessHeadlines("in", "business")
    NewsService.getBusinessHeadlines()
      .then((response) => {
        setArticles(response.data);
        setLoading(false);
        //console.log(response.data);
      })
      .catch((error) => {
        navigate("/error", { state: { errorDetails: error.message } });
        //console.log("error:" + error);
        // console.log(error.response.data);
      });
  }, [navigate]);

  return (
    <>
      <Navbar />
      <div className="home_container">
        {!loading && (
          <div className="articles">
            {articles.map((article) => (
              <Article
                id={article.id}
                category={article.category}
                description={article.description}
                title={article.title}
                url={article.url}
                imageUrl={article.imageUrl}
                home={home}
                key={article.id}
              />
            ))}
          </div>
        )}
      </div>
      <Footer />
    </>
  );
}
