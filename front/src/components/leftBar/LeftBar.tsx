import React from "react";

import DateInput from "./dateInput/DateInput";
import RangeInput from "./rangeInput/RangeInput";
import MultiCheckSelect from "./select/MultiCheckSelect";

import styles from "./LeftBar.module.css";

const LeftBar: React.FC = () => {
  const startDate = "START DATE";
  const endDate = "END DATE";
  const cost = {
    name: "COST",
    min: 1,
    max: 100,
  };
  const stars = {
    name: "STARS",
    min: 1,
    max: 5,
  };
  const features = "FEATURES";

  return (
    <aside className={styles.aside}>
      <form className={styles.form}>
        <DateInput name={startDate} />

        <DateInput name={endDate} />

        <RangeInput name={cost.name} min={cost.min} max={cost.max} />

        <RangeInput name={stars.name} min={stars.min} max={stars.max} />

        <MultiCheckSelect name={features} />
      </form>
    </aside>
  );
};

export default LeftBar;
