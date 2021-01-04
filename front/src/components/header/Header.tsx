import React from 'react';

import Search from "./search/Search";
import System, {ISystem} from "./system/System";

import styles from './Header.module.css';
import Label from "./label/Label";

interface IHeader {
    label: string
    search: string
    system: ISystem
}

export type {IHeader};

const Header: React.FC<IHeader> = ({label, search, system}: IHeader) => {
    return (
        <header className={styles.header}>
            <Label name={label}/>
            <Search name={search}/>
            <System lang={system.lang} prof={system.prof} auth={system.auth}/>
        </header>
    );
}

export default Header;
