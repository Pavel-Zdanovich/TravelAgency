import React from 'react';
import DateInput from "./dateInput/DateInput";
import RangeInput from "./rangeInput/RangeInput";
import MultiCheckSelect from "./select/MultiCheckSelect";

import styles from './LeftBar.module.css';

interface ILeftBar {
}

export type {ILeftBar};

const LeftBar: React.FC<ILeftBar> = ({}: ILeftBar) => {
    return (
        <aside className={styles.aside}>
            <form className={styles.form}>
                <DateInput name={"START DATE"}/>

                <DateInput name={"END DATE"}/>

                <RangeInput name={"COST"} min={1} max={100}/>

                <RangeInput name={"STARS"} min={1} max={5}/>

                <MultiCheckSelect name={"FEATURES"}/>
            </form>
        </aside>
    );
}

export default LeftBar;
