import React from 'react';

class AgreementForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isAgreed: false,
        };
    }

    handleCheckboxChange = (event) => {
        this.setState({ isAgreed: event.target.checked });
    };

    handleSubmit = (event) => {
        event.preventDefault();
        if (this.state.isAgreed) {
            // Здесь обрабатывайте отправку формы, например, отправку данных на сервер
            alert('Соглашение принято!');
        } else {
            alert('Пожалуйста, примите соглашение.');
        }
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div>
                    <input
                        type="checkbox"
                        id="agreementCheckbox"
                        checked={this.state.isAgreed}
                        onChange={this.handleCheckboxChange}
                    />
                    <label htmlFor="agreementCheckbox">
                        Я принимаю пользовательское соглашение.
                    </label>
                </div>
                <button type="submit" disabled={!this.state.isAgreed}>
                    Подтвердить
                </button>
            </form>
        );
    }
}

export default AgreementForm;