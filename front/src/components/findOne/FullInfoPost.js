import React from "react";

class FullInfoPost extends React.Component {
  render() {
    const info = this.props.post;
    const createdAtDate = new Date(
      info.createdAt[0],
      info.createdAt[1] - 1,
      info.createdAt[2]
    );

    const options = { day: "numeric", month: "long", year: "numeric" };
    const formattedDate = createdAtDate.toLocaleDateString("ru-RU", options);

    return (
      <div className="post">
        <h2>
          <strong>Название:</strong>
          {info.title}
        </h2>
        <h3>{info.description}</h3>
        <h3>
          <strong>Автор:</strong> {info.authorNickname}
        </h3>
        <h3>
          <strong>Дата создания:</strong> {formattedDate}
        </h3>
      </div>
    );
  }
}

export default FullInfoPost;
