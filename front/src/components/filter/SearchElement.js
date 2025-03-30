import React from "react";

class SearchElement extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      title: "",
      nickname: "",
    };
  }
  render() {
    return (
      <form className="search-bar" ref={(el) => (this.myForm = el)}>
        <input
          type="text"
          placeholder="Поиск по названию"
          onChange={(e) => this.setState({ title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Поиск по автору"
          onChange={(e) => this.setState({ nickname: e.target.value })}
        />
        <button
          type="button"
          onClick={() => {
            this.myForm.reset();
            this.findInfo = {
              title: this.state.title,
              nickname: this.state.nickname,
            };
            this.props.findPosts(this.findInfo);
            this.setState({ title: "", nickname: "" });
          }}
        >
          Поиск
        </button>
      </form>
    );
  }
}

export default SearchElement;
