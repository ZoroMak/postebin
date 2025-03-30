export const CHECKBOX_CLICK = 'CHECKBOX_CLICK';

const initialState = {};

export const checkboxReducer = (state = initialState, action) => {
    if (action.type === CHECKBOX_CLICK && action.payload !== undefined) {
        return { ...state, [action.payload]: !state[action.payload] };
    }
    return state;
};
