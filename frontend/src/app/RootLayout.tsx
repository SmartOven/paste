import React from "react";
import {Outlet} from "react-router";
import "./RootLayout.css"

const RootLayout: React.FC = () => {
    return (
        <div className="page">
            <Outlet/>
        </div>
    )
}

export default RootLayout
