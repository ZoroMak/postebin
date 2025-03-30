import React from "react";

class Form extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      description: "",
      name: "",
      surname: "",
      nickname: "",
      deletedAfterSomeDays: 0,
    };
  }
  render() {
    return (
      <form ref={(el) => (this.myForm = el)}>
        <input
          placeholder="Заголовок"
          onChange={(e) => this.setState({ title: e.target.value })}
        />
        <textarea
          placeholder="Текст"
          onChange={(e) => this.setState({ description: e.target.value })}
        />
        <div className="infoAboutAuthor">
          <input
            placeholder="Фамилия"
            onChange={(e) => this.setState({ name: e.target.value })}
          />
          <input
            placeholder="Имя"
            onChange={(e) => this.setState({ surname: e.target.value })}
          />
          <input
            placeholder="Псевдоним"
            onChange={(e) => this.setState({ nickname: e.target.value })}
          />
          <input
            placeholder="Срок действия (дни)"
            onChange={(e) =>
              this.setState({ deletedAfterSomeDays: e.target.value })
            }
          />
        </div>
        <button
          type="button"
          onClick={() => {
            this.myForm.reset();
            this.postAdd = {
              title: this.state.title,
              description: this.state.description,
              surname: this.state.surname,
              name: this.state.name,
              nickname: this.state.nickname,
              deletedAfterSomeDays: this.state.deletedAfterSomeDays,
            };
            this.props.onAdd(this.postAdd);
          }}
        >
          Добавить
        </button>
      </form>
    );
  }
}

export default Form;
