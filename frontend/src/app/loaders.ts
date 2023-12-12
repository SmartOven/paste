import {fetchGet} from "./fetch.ts";
import {defer} from "react-router-dom";

export interface Paste {
    text: string
    pasteId: string
}

export async function pasteLoader(param: any): Promise<any> {
    const pasteId = param.params.pasteId;
    const paste: Promise<Paste> = fetchGet<Paste>(
        `/api/paste/get?pasteId=${pasteId}`,
        "Failed to fetch paste"
    );
    return defer({paste: paste})
}
