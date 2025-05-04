import React, { Component } from 'react';
import axios from 'axios';
import './LoginForm.css';

const baseUrl = "http://localhost:8080/api/v1/user/regist";
// const baseUrl = "http://post_backend:8080/api/v1/user/regist";

class RegistrationForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            error: '',
        };
    }

    handleChange = (event) => {
        this.setState({ [event.target.name]: event.target.value });
    };

    handleSubmit = async (event) => {
        event.preventDefault();
        this.setState({ error: '' });

        const { username, password } = this.state;


        axios
            .post(baseUrl, {
                username: username,
                password: password,
            })
            .then((response) => {
                console.log(response);
                alert('Регистрация прошла успешно!');
                //window.location.replace(window.location.origin + '/login');
                //window.location.href = window.location.origin + '/login'
            })
            .catch((error) => {
                console.log(error);
            });
    };

    render() {
        return (
            <div className="registration-container">
                <h1>Регистрация</h1>
                {this.state.error && <div className="error-message">{this.state.error}</div>}
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            name="username"
                            value={this.state.username}
                            onChange={this.handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={this.state.password}
                            onChange={this.handleChange}
                            required
                        />
                    </div>
                    <button type="submit">Регистрация</button>
                </form>
            </div>
        );
    }
}

export default RegistrationForm;