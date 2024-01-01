import React from "react";
import PasteForm from "./PasteForm.tsx";
import "./PasteFormPage.css"

const PasteFormPage: React.FC = () => {
    return (
        <div className="form-elements">
            <div>Form for creating pastes:</div>
            <PasteForm/>
        </div>
    )
}

export default PasteFormPage
