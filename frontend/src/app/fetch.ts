let backendUri = ''

if (window.location.hostname === 'localhost') {
    backendUri = 'http://localhost:8080';
}

export enum RequestMethod {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
}

export function executeFetch(
    uri: string,
    method: RequestMethod,
    body: any = null,
): Promise<Response> {
    return fetch(backendUri + uri, {
        body: body === null ? null : JSON.stringify(body),
        headers: [["Content-Type", "application/json"]],
        method,
    });
}

export async function fetchGet<Result>(
    uri: string,
    messageOnError: string,
): Promise<Result> {
    try {
        const response = await executeFetch(uri, RequestMethod.GET);
        if (!response.ok) {
            throw Error(messageOnError);
        }
        return await response.json() as Result
    } catch (error) {
        throw Error(messageOnError);
    }
}
