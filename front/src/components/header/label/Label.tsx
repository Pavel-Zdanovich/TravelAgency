import React from "react";

import styles from "./Label.module.css";

interface ILabel {
    name: string
}

export type {ILabel};

const Label: React.FC<ILabel> = ({name}: ILabel) => {
    return (
        <label className={styles.label}>{name}</label>
    );
}

export default Label;
