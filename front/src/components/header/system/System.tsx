import React from "react";

import styles from "./System.module.css";
import Lang from "./lang/Lang";
import Profile from "./profile/Profile";
import Auth from "./auth/Auth";

interface ISystem {
    lang: string,
    prof: string,
    auth: string
}

export type {ISystem};

const System: React.FC<ISystem> = ({lang, prof, auth}: ISystem) => {
    return (
        <div className={styles.system}>
            <Lang name={lang}/>
            <Profile name={prof}/>
            <Auth name={auth}/>
        </div>
    );
}

export default System;
