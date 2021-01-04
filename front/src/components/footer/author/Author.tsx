import React from 'react';

import styles from "./Author.module.css";

interface IAuthor {
    name: string
}

export type {IAuthor};

const Author: React.FC<IAuthor> = ({name}: IAuthor) => {
    return (
        <div className={styles.author}>{name}</div>
    );
}

export default Author;
