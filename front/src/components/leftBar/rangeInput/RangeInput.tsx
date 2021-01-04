import React from 'react';

import styles from './RangeInput.module.css';

interface IRangeInput {
    name: string,
    min: number,
    max: number
}

export type {IRangeInput};

const RangeInput = (props: IRangeInput) => {
    return (
        <>
            <label className={styles.label} htmlFor={"select_cost"}>{props.name}</label>
            <input className={styles.input} type={"range"} min={props.min} max={props.max}/>
        </>
    );
}

export default RangeInput;
