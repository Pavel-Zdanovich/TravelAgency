import React from "react";

import styles from "./Label.module.css";

interface ILabelProps {
  name: string;
}

const Label: React.FC<ILabelProps> = ({ name }: ILabelProps) => {
  return <label className={styles.label}>{name}</label>;
};

export default Label;
