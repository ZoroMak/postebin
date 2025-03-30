import { TOKEN_ENDPOINT } from "./constants.js";
import CryptoJS from 'crypto-js';

const DEFAULT_LAKE = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

export const generateAuthorizationUrl = () => {
    const state = generateRandomString(16);
    localStorage.clear();
    localStorage.setItem('state', state);

    const codeVerifier = generateCodeVerifier();
    let codeChallenge;
    let codeChallengeMethod = 'plain';
    let base64;

    try {
        codeChallenge = CryptoJS.SHA256(codeVerifier);
        base64 = CryptoJS.enc.Base64.stringify(codeChallenge).replace(/=/g, '');
        // codeChallenge = generaCodeChallenge(codeVerifier); // Используем await
        codeChallengeMethod = 'S256';
    } catch (error) {
        console.error('Error generating code challenge:', error);
        codeChallengeMethod = 'plain'; //Устанавливаем метод plain в случае ошибки
        codeChallenge = ''; // Устанавливаем пустую строку, чтобы избежать ошибки в localStorage
    }

    localStorage.setItem('code_verifier', codeVerifier);
    localStorage.setItem('code_challenge', base64); // Теперь codeChallenge — строка
    localStorage.setItem('code_challenge_method', codeChallengeMethod);
    localStorage.setItem('redirect_url', TOKEN_ENDPOINT);
};

const generateCodeVerifier = () => {
    return generateSecureRandomString(64, DEFAULT_LAKE + "-._~")
};

const generateSecureRandomString = (len = 64, lake = DEFAULT_LAKE) => {
    const indices = new Uint8Array(len);
    crypto.getRandomValues(indices);
    let result = "";
    for (let index of indices){
        result += lake[index % lake.length]
    }

    return result;
};

const generateRandomString = (len = 10, lake = DEFAULT_LAKE) => {
    let result = "";
    for (let i = 0; i < len; i++) {
        const index = Math.floor(Math.random() * lake.length);
        result += lake[index];
    }
    return result;
};