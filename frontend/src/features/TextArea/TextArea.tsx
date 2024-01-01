import React, {TextareaHTMLAttributes} from "react";
import "./TextArea.css"

const TextArea: React.FC<TextareaHTMLAttributes<HTMLTextAreaElement>> = (attrs) => {
    return (
        <div className="text-area-box-dont-remove">
            <textarea
                className="paste-text-area"
                id="text"
                name="text"
                placeholder="Write here the text of your paste"
                value={attrs.value}
                onChange={attrs.onChange}
            />
        </div>
    )
}

export default TextArea
