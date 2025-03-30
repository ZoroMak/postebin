import React from "react";
import { Link } from 'react-router-dom';

class Header extends React.Component {
  render() {
    return (
      <header className="header">
        <h1>{this.props.title}</h1>
        <div className="header-buttons">
            <Link to="/">
                <button>Добавить пост</button>
            </Link>
            <Link to="/search">
                <button>Найти по заголовку</button>
            </Link>
            <Link to="/filter">
                <button>Фильтровать</button>
            </Link>
            <Link to="/login">
                <button>Вход</button>
            </Link>
        </div>
      </header>
    );
  }
}

export default Header;

/*
<button onClick={this.props.аddPostPage}>Добавить пост</button>
          <button onClick={this.props.findByTitlePage}>
            Найти по заголовку
          </button>
          <button onClick={this.props.findByFilterPage}>Фильтровать</button>
 */