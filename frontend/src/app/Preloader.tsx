import React from "react";

const Preloader: React.FC<{ text: string }> = ({text}) => {
    return (
        <div>
            <p>{text}</p>
            <div className="preloader-img">
                <img src="/assets/spinning-toilet-be-like-poggers.gif" alt="Loading gif..."/>
            </div>
        </div>
    );
};

export default Preloader;