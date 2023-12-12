import React from 'react'
import './App.css'
import RootLayout from "./pages/RootLayout.tsx";
import {RouterProvider} from "react-router";
import PasteFormPage from "./pages/paste/PasteFormPage.tsx";
import {createBrowserRouter} from "react-router-dom";
import ErrorPage from "./pages/ErrorPage.tsx";
import PasteNotFoundPage from "./pages/paste/PasteNotFoundPage.tsx";
import PastePage from "./pages/paste/PastePage.tsx";
import {pasteLoader} from "./app/loaders.ts";

const router = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout/>,
        children: [
            {
                path: "/",
                element: <PasteFormPage/>,
                errorElement: <ErrorPage/>,
            },
            {
                path: "/:pasteId",
                element: <PastePage/>,
                loader: pasteLoader,
                errorElement: <PasteNotFoundPage/>
            },
        ]
    },
]);

const App: React.FC = () => {
    return (
        <RouterProvider router={router}/>
    );
}

export default App
