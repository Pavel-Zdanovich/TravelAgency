import React from "react";

import styles from "./Author.module.css";

const Author: React.FC = () => {
  const name = "AUTHOR";

  return <div className={styles.author}>{name}</div>;
};

export default Author;
