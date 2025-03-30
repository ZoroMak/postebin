import {TOKEN_ENDPOINT} from "./constants.js";
import axios from 'axios';

const express = require('express');
const app = express();

//app.use(express.json()); // Для парсинга JSON тела запроса

app.post(TOKEN_ENDPOINT, (req, res) => {
    try {
        const accessCode = req.body.accessCode;
        const codeVerifier = req.body.code_verifier;
        const username = localStorage.getItem("username")
        console.log("req")

        // Валидация
        if (!accessCode || !codeVerifier || !username) {
            return res.status(400).json({ error: 'Missing required parameters' });
        }

        // Формируем тело ответа
        const responseBody = {
            accessCode: accessCode,
            codeVerifier: localStorage.getItem('code_verifier'),
            username: localStorage.getItem("username")
        };

        res.json(responseBody);
    } catch (error) {
        console.error('Error processing request:', error);
        res.status(500).json({ error: 'Internal server error' });
    }
});

async function getToken() {
     // Замените на ваш URL
    const user = { accessCode: 'yourAccessCode', codeVerifier: 'yourCodeVerifier' }; // Замените на данные пользователя

    try {
        const response = await axios.post(TOKEN_ENDPOINT, user);
        const tokenData = response.data;

        // Используем полученный токен
        console.log('Полученный токен:', tokenData);
    } catch (error) {
        console.error('Ошибка при получении токена:', error);
        // Обработка ошибки, например, отображение сообщения об ошибке пользователю.  Обратите внимание на error.response
        if (error.response) {
            console.error('Ошибка от сервера:', error.response.data);
            console.error('Статус ответа:', error.response.status);
            console.error('Заголовки ответа:', error.response.headers);
        }
    }
}

getToken();