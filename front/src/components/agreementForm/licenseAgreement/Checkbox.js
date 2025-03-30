import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getCheckboxState } from '../selectors';
import { checkboxClick } from '../actions';

    const Checkbox = ({ name, label }) => {
        const dispatch = useDispatch();
        const checked = useSelector(getCheckboxState(name));

        const handleClick = () => {
            dispatch(checkboxClick(name));
        };

        return (
            <div>
                <input
                    id={name}
                    type="checkbox"
                    checked={checked}
                    onChange={handleClick}
                />
                <label htmlFor={name}>{label}</label>
            </div>
        );
    };

    export default Checkbox;
