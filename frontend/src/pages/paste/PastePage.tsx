import React, {Suspense} from "react";
import {Paste} from "../../app/loaders.ts";
import {Await, useLoaderData, useNavigate} from "react-router-dom";
import Preloader from "../../app/Preloader.tsx";
import LoadingError from "../../app/LoadingError.tsx";
import {executeFetch, RequestMethod} from "../../app/fetch.ts";

const PastePage: React.FC = () => {
    const navigate = useNavigate();
    const loaderData = useLoaderData() as { paste: Paste };

    const onDeletePaste = async (pasteId: string) => {
        await executeFetch(`/api/paste/delete?pasteId=${pasteId}`, RequestMethod.DELETE)
        navigate('/')
    }

    return (
        <div>
            <Suspense fallback={<Preloader text={"Loading paste..."}/>}>
                <Await
                    resolve={loaderData.paste}
                    errorElement={<LoadingError text={"Error loading paste!"}/>}
                >
                    {(paste: Paste) => (
                        <div>
                            <div>
                                <button onClick={() => onDeletePaste(paste.pasteId)}>Delete paste</button>
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
