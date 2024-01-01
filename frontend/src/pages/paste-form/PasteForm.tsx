import * as React from 'react'
import './PasteForm.css'
import {FormikState, useFormik} from "formik";
import {executeFetch, RequestMethod} from "../../app/fetch.ts";
import {useNavigate} from "react-router-dom";
import TextArea from "../../features/TextArea/TextArea.tsx";

interface PasteFormValue {
    text: string
}

interface PasteCreationResponse {
    pasteId: string
}

const PasteForm: React.FC = () => {
    const navigate = useNavigate();
    const initialFormValue: PasteFormValue = {text: ""}

    const onCreatePaste = async (value: PasteFormValue) => {
        const response = await executeFetch(`/api/paste/create`, RequestMethod.POST, {text: value.text})
        const pasteCreationResponse = await response.json() as PasteCreationResponse
        navigate(`/${pasteCreationResponse.pasteId}`)
    }

    const formik = useFormik({
        initialValues: initialFormValue,
        onSubmit: async (values, {resetForm}) => {
            await onCreatePaste(values)
            resetForm(initialFormValue as Partial<FormikState<PasteFormValue>>)
        },
    });
    return (
        <form className="paste-form" onSubmit={formik.handleSubmit}>
            <div className="text-area-box">
                <TextArea value={formik.values.text} onChange={formik.handleChange}/>
            </div>
            <div className="submit-button-wrapper">
                <input className="create-button" type="submit" value="Create paste"/>
            </div>
        </form>
    )
}

export default PasteForm
