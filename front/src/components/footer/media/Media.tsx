import React from "react";

import styles from "./Media.module.css";

const Media: React.FC = () => {
  const name = "MEDIA";

  return <div className={styles.media}>{name}</div>;
};

export default Media;
