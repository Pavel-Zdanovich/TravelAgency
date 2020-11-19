import React from 'react';

import styles from './Search.css';

const Search = (props) => {
    return (
        <form>
            <label className={styles.label} htmlFor={"search_input"}>{props.name}</label>
            <input className={styles.input} type={"search"} placeholder={props.name}/>
        </form>
    );
}

export default Search;