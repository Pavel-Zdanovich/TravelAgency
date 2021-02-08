import React from "react";

import styles from "./DateInput.module.css";

interface IDateInput {
  name: string;
}

export type { IDateInput };

const DateInput: React.FC<IDateInput> = ({ name }: IDateInput) => {
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
