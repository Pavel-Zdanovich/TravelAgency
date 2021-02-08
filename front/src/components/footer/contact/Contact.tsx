import React from "react";

import styles from "./Contact.module.css";

const Contact: React.FC = () => {
  const name = "CONTACT";

  return <div className={styles.contact}>{name}</div>;
};

export default Contact;
