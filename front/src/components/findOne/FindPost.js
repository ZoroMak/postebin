import React from "react";
import SearchForm from "./SearchForm";
import FullInfoPost from "./FullInfoPost";
import axios from "axios";

import ModalWindow from "../window/ModalWindow"; //Убрать

const baseUrl = 'http://localhost:8080/api/v1/post';
// const baseUrl = 'http://post_backend:8080/api/v1/post';

class FindPost extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isFind: false,
      post: "",
    };
    this.findPostByHash = this.findPostByHash.bind(this);
  }

  render() {
    return (
      <div>
        <SearchForm onFind={this.findPostByHash} />
        {this.state.isFind ? (
          <FullInfoPost post={this.state.post} />
        ) : (
          <div className="post">
            <h3>Введите hash url для поиска</h3>
          </div>
        )}

        <ModalWindow text="На этой странице вы можете найти пост по уникальному имени"/>
      </div>
    );
  }

  findPostByHash(hash) {
    if (hash === "") {
      this.setState({ post: "", isFind: false });
      return;
    }
    axios
      .get(baseUrl + hash, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem("token"),
        }})
      .then((response) => {
        this.setState({ post: response.data, isFind: true });
      })
      .catch((error) => {
        alert(
          "Поста с таким hash url нет. Убедитесь что правильно ввели данные."
        );
      });
  }
}

export default FindPost;
