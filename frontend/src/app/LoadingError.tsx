import React from "react";

const LoadingError: React.FC<{text: string}> = ({text}) => {
    return (
        <p>{text}</p>
    )
}

export default LoadingError;