import React from 'react';
import Checkbox from './Checkbox';
import AgreementSubmitButton from './AgreementSubmitButton';

const LicenseAgreement = ({ onClose }) => {
    return (
        <div className="license-agreement-container">
            <h2>Соглашение об использовании файлов cookie</h2>
            <p>
                Мы используем файлы cookie для улучшения работы нашего веб-сайта. Файлы cookie — это небольшие текстовые
                файлы, которые хранятся на вашем компьютере, чтобы помочь нам персонализировать ваш опыт. Мы используем
                как сессионные, так и постоянные файлы cookie. Сессионные файлы cookie удаляются при закрытии браузера,
                а постоянные файлы cookie хранятся на вашем компьютере до тех пор, пока не будут удалены или не истечет
                срок их действия. Вы можете настроить свой браузер так, чтобы он блокировал все файлы cookie или
                предупреждал вас о них. Однако, некоторые функции сайта могут не работать без файлов cookie. Для
                получения дополнительной информации ознакомьтесь с нашей <a href="#">политикой конфиденциальности</a>.
            </p>
            <Checkbox name="agree" label="Agree"/>
            <AgreementSubmitButton onClose={onClose}/>
        </div>
    );
};

export default LicenseAgreement;