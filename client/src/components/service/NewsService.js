import axios from "axios";

const New_Service_API_BASE_URL = "";

// const userName = sessionStorage.getItem("userName");
// const token = sessionStorage.getItem("token");
class NewsService {
  login(credentials) {
    return axios.post(
      "https://jmj6xkdgjj.execute-api.ca-central-1.amazonaws.com/newswave/generatetoken",
      credentials
    );
  }

  register(register) {
    console.log("News Service:register...");
    return axios.post(
      `https://jmj6xkdgjj.execute-api.ca-central-1.amazonaws.com/newswave/user/register`,
      register
    );
  }
  //getBusinessHeadlines(country, category) {
  getBusinessHeadlines() {
    return axios.get(
      `https://jmj6xkdgjj.execute-api.ca-central-1.amazonaws.com/newswave/newsservice/headlines`
      // `https://jmj6xkdgjj.execute-api.ca-central-1.amazonaws.com/newswave/newsservice/headlines/${country}/${category}`
    );
  }
  addToWishList(article) {
    const userName = sessionStorage.getItem("userName");
    const token = sessionStorage.getItem("token");
    console.log("News Service addWishList for..." + userName);
    return axios.post(
      "http://localhost:8084/wishlist/addNewsToFavs/" + userName,
      article,
      {
        headers: {
          authorization: token,
        },
      }
    );
  }

  getAllWishList() {
    const userName = sessionStorage.getItem("userName");
    const token = sessionStorage.getItem("token");

    console.log("News Service getWishList for..." + userName);
    return axios.get(
      "http://localhost:8084/wishlist/getAllFavorites/" + userName,
      {
        headers: {
          authorization: token,
        },
      }
    );
  }

  deleteFromWishList(id, category) {
    const userName = sessionStorage.getItem("userName");
    const token = sessionStorage.getItem("token");
    console.log("News Service deleteWishList for..." + userName);
    return axios.delete(
      "http://localhost:8084/wishlist/deleteFavorites/" +
        id +
        "/" +
        category +
        "/" +
        userName,
      {
        headers: {
          authorization: token,
        },
      }
    );
  }
}
export default new NewsService();
