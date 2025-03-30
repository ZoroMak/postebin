import React from 'react';
import { useSelector } from 'react-redux';
import { getCheckboxState } from '../selectors';

const AgreementSubmitButton = ({ onClose }) => {
    const checkboxName = 'agree';
    const agreed = useSelector(getCheckboxState(checkboxName));

    const handleSubmit = () => {
        if (agreed) {
            onClose();
        } else {
            alert('Пожалуйста, примите соглашение.');
        }
    };

    return <input type="button" value="Submit" onClick={handleSubmit} disabled={!agreed} />;
};

export default AgreementSubmitButton;
