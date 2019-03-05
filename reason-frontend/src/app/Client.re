[%raw "require('isomorphic-fetch')"];

let backendURI = "http://localhost:8080/";
let createGame: string => Js.Promise.t(Domain.response) = (name: string) =>
  Js.Promise.(
    Fetch.fetchWithInit(backendURI ++ name, Fetch.RequestInit.make(~method_=Post, ()))
    |> then_(Fetch.Response.json)
    |> then_(resp => resp |> Domain.Decoders.decodeResponse |> resolve)
  );
