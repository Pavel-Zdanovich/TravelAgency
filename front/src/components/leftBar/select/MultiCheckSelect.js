import React from 'react';

import styles from './MultiCheckSelect.css';

const MultiCheckSelect = (props) => {
    return (
        <>
            <label className={styles.label}>{props.select.name}</label>
            <select className={styles.select2Container}>
                <option value={"air conditioner"} data-badge="">air conditioner</option>
                <option value={"cable TV"} data-badge="">cable TV</option>
                <option value={"car rental"} data-badge="">car rental</option>
                <option value={"mini-bar"} data-badge="">mini-bar</option>
                <option value={"parking"} data-badge="">parking</option>
                <option value={"restaurant"} data-badge="">restaurant</option>
                <option value={"room service"} data-badge="">room service</option>
                <option value={"spa"} data-badge="">spa</option>
                <option value={"swimming pool"} data-badge="">swimming pool</option>
                <option value={"wi-fi"} data-badge="">wi-fi</option>
            </select>
        </>
    );
}

export default MultiCheckSelect;