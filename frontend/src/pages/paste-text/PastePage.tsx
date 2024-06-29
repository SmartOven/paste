import React, {Suspense} from "react";
import {Paste} from "../../app/loaders.ts";
import {Await, useLoaderData, useNavigate} from "react-router-dom";
import Preloader from "../../app/Preloader.tsx";
import LoadingError from "../../app/LoadingError.tsx";

const PastePage: React.FC = () => {
    const navigate = useNavigate();
    const loaderData = useLoaderData() as { paste: Paste };

    const onNavHome = async () => {
        navigate('/')
    }

    return (
        <div>
            <Suspense fallback={<Preloader text={"Loading paste-text..."}/>}>
                <Await
                    resolve={loaderData.paste}
                    errorElement={<LoadingError text={"Error loading paste-text!"}/>}
                >
                    {(paste: Paste) => (
                        <div>
                            <div>
                                <button onClick={onNavHome}>Create new paste</button>
                            </div>
                            {/*<div className="text">*/}
                            <div>
                                <pre>
                                    {paste.text}
                                </pre>
                            </div>
                        </div>
                    )}
                </Await>
            </Suspense>
        </div>
    )
}

export default PastePage
