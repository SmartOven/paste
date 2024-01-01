import {useRouteError} from "react-router-dom";
import React from "react";

interface Error {
    statusText: string;
    message: string;
}

const ErrorPage: React.FC = () => {
    const error = useRouteError() as Error;
    return (
        <div className="main-page-error">
            <div className="flex-horizontal-center">
                <div>
                    <h1>Oops!</h1>
                    <div>It seems that we are unavailable for now 😥</div>
                    <div>Try to visit us later!</div>
                    <p>
                        <i>{error.statusText || error.message}</i>
                    </p>
                </div>
            </div>
        </div>
    );
}

export default ErrorPage
