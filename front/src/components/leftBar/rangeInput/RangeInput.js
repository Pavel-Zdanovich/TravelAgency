import React from 'react';

import styles from './RangeInput.css';

const RangeInput = (props) => {
    return (
        <>
            <label className={styles.label} htmlFor={"select_cost"}>{props.range.name}</label>
            <input className={styles.input} type={"range"} min={props.range.min} max={props.range.max}/>
        </>
    );
}

export default RangeInput;