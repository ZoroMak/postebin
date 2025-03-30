import React from "react";

class SearchForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      hash: "",
    };
  }
  render() {
    return (
      <form className="search-bar" ref={(el) => (this.myForm = el)}>
        <input
          type="text"
          placeholder="Поиск по hash url"
          onChange={(e) => this.setState({ hash: e.target.value })}
        />
        <button
          type="button"
          onClick={() => {
            this.myForm.reset();
            this.props.onFind(this.state.hash);
            this.setState({ hash: "" });
          }}
        >
          Поиск
        </button>
      </form>
    );
  }
}

export default SearchForm;
