import React from "react";

import styles from "./RangeInput.module.css";

interface IRangeInput {
  name: string;
  min: number;
  max: number;
}

export type { IRangeInput };

const RangeInput: React.FC<IRangeInput> = ({ name, min, max }: IRangeInput) => {
  const id = "select_" + name;

  return (
    <>
      <label className={styles.label} htmlFor={id}>
        {name}
      </label>
      <input
        className={styles.input}
        id={id}
        type={"range"}
        min={min}
        max={max}
      />
    </>
  );
};

export default RangeInput;
