import React from 'react';

import styles from './DateInput.module.css';

interface IDateInput {
    name: string
}

export type {IDateInput};

const DateInput: React.FC<IDateInput> = ({name}: IDateInput) => {
    return (
        <div>
            <label className={styles.label} htmlFor={"select_start_date"}>{name}</label>
            <input className={styles.input} type={"datetime-local"}/>
        </div>
    );
}

export default DateInput;
