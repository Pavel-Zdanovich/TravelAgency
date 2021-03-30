import React from "react";
import { useSelector } from "react-redux";

import { State } from "../../../../store/state";
import { IAuth } from "../../../../store/components/auth/types";

import styles from "./Profile.module.css";

const Profile: React.FC = () => {
  const defaultName = "?";

  const auth: IAuth = useSelector((state: State) => state.auth.payload);

  return <div className={styles.profile}>{auth ? auth.name : defaultName}</div>;
};

export default Profile;
