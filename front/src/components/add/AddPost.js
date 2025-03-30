import React from "react";
import Form from "./Form";
import AddedPost from "./AddedPost";
import axios from "axios";

const baseUrl = "http://localhost:8080/api/v1/post";

class AddPost extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      postIsAdd: false,
      addedPost: "",
    };

    this.addPost = this.addPost.bind(this);
    this.returnBackPage = this.returnBackPage.bind(this);
  }
  render() {
    return (
      <div>
        {!this.state.postIsAdd ? (
          <Form onAdd={this.addPost} />
        ) : (
          <AddedPost
            addedPost={this.state.addedPost}
            returnBackPage={this.returnBackPage}
          />
        )}
      </div>
    );
  }

  addPost(post) {

    axios.post(baseUrl, post, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem("token"),
  }
  })
      .then((response) => {
        console.log("Пост успешно добавлен:", response.data);
        this.setState({ addedPost: response.data });
        this.setState({ postIsAdd: true });
      })
      .catch((error) => {
        console.error("Произошла ошибка при добавлении поста:", error);
      });
  }

  returnBackPage() {
    this.setState({ addedPost: "" });
    this.setState({ postIsAdd: false });
  }
}

export default AddPost;
