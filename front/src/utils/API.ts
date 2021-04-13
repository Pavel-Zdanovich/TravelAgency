const protocol = "http";
const host = "localhost";
const port = "8080";
const path = "travelagency";
const url = `${protocol}://${host}:${port}/${path}`;

export interface IRequest {
  method: string;
  path: string;
  headers?: HeadersInit;
  body?: BodyInit;
  mode?: RequestMode;
  credentials?: RequestCredentials;
}

function request({
  method,
  path,
  headers,
  body,
  mode = "cors",
  credentials = "omit",
}: IRequest): Promise<Response> {
  const input: RequestInfo = `${url}/${path}`;
  const init: RequestInit = {
    method: method,
    body: body,
    headers: headers,
    mode: mode,
    credentials: credentials,
  };
  console.groupCollapsed(`${init.method} ${input}`);
  console.log(init);
  console.groupEnd();
  return fetch(input, init);
}

export default { request };
