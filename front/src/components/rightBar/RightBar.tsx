import React from 'react';

import Label from "./label/Label";
import Gallery from "./gallery/Gallery";
import Review from "./review/Review";

import styles from './RightBar.module.css';

interface IRightBar {
    name: string,
    src: any
}

const RightBar: React.FC<IRightBar> = ({name, src}: IRightBar) => {
    return (
        <aside className={styles.aside}>
            <Label name={name}/>
            <Gallery src={src} alt={name}/>
            <Review name={name}/>
        </aside>
    );
}

export default RightBar;
