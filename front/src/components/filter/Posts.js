import React from "react";
import Post from "./Post";
import SearchElement from "./SearchElement";
import axios from "axios";

const baseUrl = 'http://localhost:8080/api/v1/post';
// const baseUrl = 'http://post_backend:8080/api/v1/post';

class Posts extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      posts: [],
      currentPage: 0,
      fetching: false,
      totalCount: 0,
      pageSize: 8,
      request: "",
    };

    this.findNeededPosts = this.findNeededPosts.bind(this);
  }
  render() {
    return (
      <div>
        <SearchElement findPosts={this.findNeededPosts} />
        {this.state.posts.length > 0 ? (
          this.state.posts.map((el) => <Post key={el.id} post={el} />)
        ) : (
          <div className="post">
            <h3>Введите данные для поиска поста</h3>
          </div>
        )}
      </div>
    );
  }

  unpackParams(req) {
    return {
      page: this.state.currentPage,
      size: this.state.pageSize,
      ...req,
    };
  }

  findNeededPosts(req) {
    this.setState({ currentPage: 0, request: req }, () => {
      const params = this.unpackParams(req);
      this.setState({posts: []})
      console.log(localStorage.getItem("token"))

      axios
        .get(baseUrl, {
          params,
          headers: {
          'Authorization': 'Bearer ' + localStorage.getItem("token"),
        }
        })
        .then((response) => {
          console.log(response);
          this.setState({
            totalCount: response.data.totalCount,
            posts: response.data.objects,
            currentPage: this.state.currentPage + 1,
          });
        })
        .catch((error) => {
          console.log(error);
        });
    });
  }

  refreshPage() {
    const params = this.unpackParams(this.state.request);

    if (!this.state.fetching) {
      this.setState({ fetching: true });

      axios
        .get(baseUrl, { params, headers: {
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
          } })
        .then((response) => {
          this.setState((prevState) => ({
            totalCount: response.data.totalCount,
            posts: [...prevState.posts, ...response.data.objects],
            currentPage: prevState.currentPage + 1,
          }));
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.setState({ fetching: false });
        });
    }
  }

  componentDidUpdate() {
    document.addEventListener("scroll", this.scrollHandler);

    return () => {
      document.removeEventListener("scroll", this.scrollHandler);
    };
  }

  scrollHandler = (e) => {
    if (
      e.target.documentElement.scrollHeight -
        (e.target.documentElement.scrollTop + window.innerHeight) <
        100 &&
      this.state.posts.length < this.state.totalCount
    ) {
      this.refreshPage();
    }
  };
}

export default Posts;
