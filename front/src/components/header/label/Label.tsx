import React from "react";

import styles from "./Label.module.css";

const Label: React.FC = () => {
  const name = "TravelAgency";

  return <label className={styles.label}>{name}</label>;
};

export default Label;
