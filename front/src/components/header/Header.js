import React from 'react';
import Search from "./search/Search";
import Lang from "./lang/Lang";
import Profile from "./profile/Profile";
import Auth from "./auth/Auth";

import styles from './Header.css';

const Header = (props) => {
    return (
        <header className={styles.header}>
            <label className={styles.label}>{props.name}</label>
            <Search className={styles.search} name={"SEARCH"}/>
            <div className={styles.system}>
                <Lang className={styles.lang} name={"LANG"}/>
                <Profile className={styles.profile} name={"PROFILE"}/>
                <Auth className={styles.auth} name={"AUTH"}/>
            </div>
        </header>
    );
}

export default Header;