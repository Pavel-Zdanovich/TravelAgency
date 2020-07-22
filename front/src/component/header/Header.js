import React from 'react';
import Search from "./Search";
import Lang from "./Lang";
import Avatar from "./Avatar";
import Auth from "./Auth";

function Header(props) {
    return (
        <div>
            <div>{props.name}</div>
            <Search name={"Search"}/>
            <Lang name={"Lang"}/>
            <Avatar name={"Avatar"}/>
            <Auth name={"Auth"}/>
        </div>
    );
}

export default Header;