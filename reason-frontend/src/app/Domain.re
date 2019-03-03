type player = {
  name: string,
  health: int,
  timeDelta: float,
  gold: int,
  score: int,
};

module Decoders = {
  open Rationale.Option;
  open Json;

  let decodePlayer = (json: Js.Json.t): player =>
    Decode.{
      name: json |> field("name", Decode.string),
      health: json |> field("health", Decode.int),
      timeDelta: json |> field("timeDelta", Decode.float),
      gold: json |> optional(field("gold", Decode.int)) |> default(0),
      score: json |> optional(field("score", Decode.int)) |> default(0),
    };
};
