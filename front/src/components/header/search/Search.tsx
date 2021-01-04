import React from 'react';

import styles from './Search.module.css';

interface ISearch {
    name: string
}

export type {ISearch};

const Search: React.FC<ISearch> = ({name}: ISearch) => {
    return (
        <form className={styles.search}>
            <label className={styles.label} htmlFor={"search_input"}>{name}</label>
            <input className={styles.input} type={"search"} placeholder={name}/>
        </form>
    );
}

export default Search;
