import React from "react";

import styles from "./Contact.module.css";

interface IContact {
    name: string
}

export type {IContact};

const Contact: React.FC<IContact> = ({name}: IContact) => {
    return (
        <div className={styles.contact}>{name}</div>
    );
}

export default Contact;
