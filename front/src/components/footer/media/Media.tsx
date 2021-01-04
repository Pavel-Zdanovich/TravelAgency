import React from "react";

import styles from "./Media.module.css";

interface IMedia {
    name: string
}

export type {IMedia};

const Media: React.FC<IMedia> = ({name}: IMedia) => {
    return (
        <div className={styles.media}>{name}</div>
    );
}

export default Media;
