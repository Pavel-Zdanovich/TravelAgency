import React from 'react';
import styles from './DateInput.css';

const DateInput = (props) => {
    return (
        <>
            <label className={styles.label} htmlFor={"select_start_date"}>{props.name}</label>
            <input className={styles.input} type={"datetime-local"}/>
        </>
    );
}

export default DateInput;