import * as React from 'react'
import './PasteForm.css'
import {FormikState, useFormik} from "formik";
import {executeFetch, RequestMethod} from "../../../app/fetch.ts";
import {useNavigate} from "react-router-dom";

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
        <div>
            <form className="paste-form" onSubmit={formik.handleSubmit}>
                <div>
                    <textarea
                        className="paste-text"
                        id="text"
                        name="text"
                        placeholder="Write here the text of your paste"
                        value={formik.values.text}
                        onChange={formik.handleChange}
                    />
                </div>
                <input type="submit" value="Create paste"/>
            </form>
        </div>
    )
}

export default PasteForm
