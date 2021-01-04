import React from 'react';

import styles from './Map.module.css';

interface IMap {
    name: string,
    src: any
}

export type {IMap};

const Map: React.FC<IMap> = ({src, name}: IMap) => {
    return (
        <div className={styles.map}>
            <img src={src} alt={name}/>
        </div>
    );
}

export default Map;
