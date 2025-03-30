import React from "react";

class Post extends React.Component {
  render() {
    const post = this.props.post;
    const createdAtDate = new Date(
      post.createdAt[0],
      post.createdAt[1] - 1,
      post.createdAt[2]
    );

    const options = { day: "numeric", month: "long", year: "numeric" };
    const formattedDate = createdAtDate.toLocaleDateString("ru-RU", options);

    return (
      <div className="post">
        <h3>{post.title}</h3>
        <p>
          <b>Автор: </b>
          {post.authorNickname}
        </p>
        <p>
          <b>Дата создания: </b>
          {formattedDate}
        </p>
      </div>
    );
  }
}

export default Post;
