export enum Method {
  GET,
  POST,
  PUT,
  DELETE,
}

export function request(
  method: Method,
  url: string,
  body: BodyInit | null,
  headers: HeadersInit,
) {
  fetch(url, {
    method: method.toString(),
    body: body,
    headers: headers,
  });
}
