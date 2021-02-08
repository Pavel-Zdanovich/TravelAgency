import React from "react";

import styles from "./Profile.module.css";

const Profile: React.FC = () => {
  const name = "PROF";

  return <div className={styles.profile}>{name}</div>;
};

export default Profile;
