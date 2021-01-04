import React from 'react';

import styles from './Lang.module.css';

interface ILang {
    name: string
}

export type {ILang}

const Lang: React.FC<ILang> = ({name}: ILang) => {
    return (
        <div className={styles.lang}>{name}</div>
    );
}

export default Lang;
