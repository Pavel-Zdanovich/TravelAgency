import React from "react";

import L10n from "./l10n/L10n";
import Profile from "./profile/Profile";
import Auth from "./auth/Auth";

import styles from "./System.module.css";

const System: React.FC = () => {
  return (
    <div className={styles.system}>
      <L10n />
      <Profile />
      <Auth />
    </div>
  );
};

export default System;
