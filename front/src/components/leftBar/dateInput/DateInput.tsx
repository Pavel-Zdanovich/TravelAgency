import React from "react";

import styles from "./DateInput.module.css";

interface IDateInputProps {
  name: string;
}

export type { IDateInputProps };

const DateInput: React.FC<IDateInputProps> = ({ name }: IDateInputProps) => {
  const id = "select_" + name;

  return (
    <>
      <label className={styles.label} htmlFor={id}>
        {name}
      </label>
      <input className={styles.input} type={"datetime-local"} id={id} />
    </>
  );
};

export default DateInput;
