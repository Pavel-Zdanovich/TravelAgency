import React from "react";

import styles from "./RangeInput.module.css";

interface IRangeInputProps {
  name: string;
  min: number;
  max: number;
}

export type { IRangeInputProps };

const RangeInput: React.FC<IRangeInputProps> = ({
  name,
  min,
  max,
}: IRangeInputProps) => {
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
