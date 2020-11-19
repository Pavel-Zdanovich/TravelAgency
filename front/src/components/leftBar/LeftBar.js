import React from 'react';
import DateInput from "./dateInput/DateInput";
import RangeInput from "./rangeInput/RangeInput";
import MultiCheckSelect from "./select/MultiCheckSelect";

import styles from './LeftBar.css';

const LeftBar = (props) => {
    return (
        <aside className={styles.aside}>
            <form className={styles.form}>
                <DateInput name={"START DATE"}/>

                <DateInput name={"END DATE"}/>

                <RangeInput range={
                    {
                        name: "COST",
                        min: 1,
                        max: 100
                    }
                }/>

                <RangeInput range={
                    {
                        name: "STARS",
                        min: 1,
                        max: 5
                    }
                }/>

                <MultiCheckSelect select={
                    {
                        name: "FEATURES"
                    }
                }/>
            </form>
        </aside>
    );
}

export default LeftBar;