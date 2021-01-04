import React from 'react';

import styles from './Profile.module.css';

interface IProfile {
    name: string
}

export type {IProfile};

const Profile: React.FC<IProfile> = ({name}: IProfile) => {
    return (
        <div className={styles.profile}>{name}</div>
    );
}

export default Profile;
