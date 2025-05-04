import React from 'react';
import './LoginForm.css';
import axios from "axios";
import {generateAuthorizationUrl} from "../authorization/utils";
import {Link} from "react-router-dom";
import {TOKEN_ENDPOINT} from "../authorization/constants";

const baseUrl = "http://localhost:8080/api/v1/user/login";
// const baseUrl = 'http://post_backend:8080/api/v1/user/login';

class LoginForm extends React.Component {
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

    handleSubmit = (event) => {
        event.preventDefault();
        this.setState({ error: '' });

        const { username, password } = this.state;
        localStorage.setItem("username", this.state.username)

        if (username === '' || password === '') {
            this.setState({ error: 'Пожалуйста, заполните все поля.' });
            return;
        }

        generateAuthorizationUrl()

        axios
            .post(baseUrl, {
                username: this.state.username,
                password: this.state.password,
                codeChallenge: localStorage.getItem('code_challenge'),
                codeChallengeMethode: localStorage.getItem('code_challenge_method')
            })
            .then((response) => {
                this.getToken(response.data.accessCode);
            })
            .catch((error) => {
                //console.log(error);
                this.setState({ error: 'Неверный логин или пароль.' });
            });

        //console.log(localStorage.getItem('code_challenge'))
    };

    getToken = (accessCode) => {
        console.log("code")
        axios
            .post(TOKEN_ENDPOINT, {
                username: this.state.username,
                password: this.state.password,
                accessCode: accessCode,
                codeVerifier: localStorage.getItem('code_verifier')
            })
            .then((response) => {

                console.log(response.data.tokenJWT);
                localStorage.setItem("token", response.data.tokenJWT)
                alert('Вход выполнен успешно!');
                //window.location.href = window.location.origin + '/'
            })
            .catch((error) => {
                //console.log(error);
                this.setState({ error: 'Неверный логин или пароль.' });
            });
    }

    render() {
        return (
            <div className="login-container">
                <h1>Вход</h1>
                {this.state.error && (
                    <div className="error-message">{this.state.error}</div>
                )}
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username">Логин:</label>
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
                        <label htmlFor="password">Пароль:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={this.state.password}
                            onChange={this.handleChange}
                            required
                        />
                    </div>
                    <button type="submit">Войти</button>
                </form>

                <Link to="/regist" className="regist">
                    Нет аккаунта? Регисрация
                </Link>
            </div>
        );
    }
}

export default LoginForm;