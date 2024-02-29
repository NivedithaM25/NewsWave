import { useState, useEffect } from "react";
import "./Article.css";
import NewsService from "../service/NewsService";
import { useNavigate } from "react-router-dom";

export default function Article({
  id,
  // content,
  title,
  description,
  category,
  // source,

  url,
  imageUrl,
  home,
}) {
  const [article, setArticle] = useState({
    id: id,
    title: title,
    description: description,
    url: url,
    imageUrl: imageUrl,
    category: category,
    //content: content,
  });

  const navigate = useNavigate();
  const [isWishlisted, setIsWishlisted] = useState(
    localStorage.getItem(title) === "true" ? true : false
  );
  const [isLoggedIn, setIsLoggedIn] = useState(
    sessionStorage.getItem("userName") != null ? true : false
  );

  useEffect(() => {
    localStorage.setItem(title, isWishlisted);
  }, [isWishlisted, title]);

  const addToWishList = (e) => {
    e.preventDefault();
    if (sessionStorage.getItem("userName") === null) {
      navigate("/login");
      return;
    }
    if (!isLoggedIn) {
      navigate("/login");
      return;
    }
    setIsLoggedIn(true);
    NewsService.addToWishList(article)
      .then((response) => {
        setIsWishlisted(!isWishlisted);
      })
      .catch((error) => {
        // console.log(error);
        // console.log(error.response.status);
        if (error.response.status === 409) {
          alert(error.response.data.message);
        } else {
          navigate("/error", { state: { errorDetails: error.message } });
        }

        //console.log("error:" + error.response.data.message);
      });
  };

  const deleteFromWishList = (e) => {
    e.preventDefault();
    NewsService.deleteFromWishList(article.id, article.category)
      .then((response) => {
        //console.log(response);
        setIsWishlisted(!isWishlisted);
        localStorage.removeItem(title);
        window.location.reload();
      })
      .catch((error) => {
        navigate("/error", { state: { errorDetails: error.message } });
        //console.log(error);
      });
  };

  return (
    <div>
      <div className="article">
        <div className="wrapper">
          <img
            src={
              imageUrl ||
              "https://thumbs.dreamstime.com/b/news-newspapers-folded-stacked-word-wooden-block-puzzle-dice-concept-newspaper-media-press-release-42301371.jpg"
            }
            alt="News 1"
          />
          <div className="delete-div">
            {!home && (
              <div
                className="delete-btn"
                onClick={(e) => deleteFromWishList(e)}
              >
                <span className="material-symbols-outlined">&#xe5cd;</span>
              </div>
            )}
          </div>
          <div className="text">
            <p>
              <strong>
                <a href="{url}" target="_blank">
                  {title}
                </a>
              </strong>
            </p>
            <p>{description}</p>

            {home && (
              <button
                className="btn"
                id="addToWishlist"
                onClick={(e) => addToWishList(e)}
              >
                <>
                  <span
                    className="material-symbols-outlined"
                    style={{ color: isWishlisted ? "red" : "black" }}
                  >
                    &#xe87d;
                  </span>
                </>
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
