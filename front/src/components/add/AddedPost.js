import React from "react";

class AddedPost extends React.Component {
  render() {
    const dateArray = this.props.addedPost.createdAt;
    const createdAtDate = new Date(
      dateArray[0],
      dateArray[1] - 1,
      dateArray[2]
    );

    const options = { day: "numeric", month: "long", year: "numeric" };
    const formattedDate = createdAtDate.toLocaleDateString("ru-RU", options);

    return (
      <div>
        <h1>Ваш пост добавлен</h1>
        <div className="post-card">
          <h2 className="post-title">
            Заголовок: {this.props.addedPost.title}
          </h2>
          <p className="post-author">
            Автор:{" "}
            <span className="author-nickname">
              {this.props.addedPost.authorNickname}
            </span>
          </p>
          <p className="post-date">
            Дата создания: <span className="created-at">{formattedDate}</span>
          </p>
          <p className="post-hash-url">
            Hash URL:{" "}
            <span className="hash-url">{this.props.addedPost.hashUrl}</span>
          </p>
          <p className="post-url">
            URL: <span className="url">{this.props.addedPost.url}</span>
          </p>
        </div>
        <button className="returnButton" onClick={this.props.returnBackPage}>
          Вернуться назад
        </button>

        <p>При возвращении назад доступ к указанным данным будет потерян</p>
      </div>
    );
  }
}

export default AddedPost;
