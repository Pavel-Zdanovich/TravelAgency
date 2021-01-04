import React from 'react';

import styles from './Auth.module.css';

interface IAuth {
    name: string
}

export type {IAuth};

const Auth: React.FC<IAuth> = ({name}: IAuth) => {
    return (
        <div className={styles.auth}>{name}</div>
    );
}

export default Auth;
