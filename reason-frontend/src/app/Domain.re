type player = {
  name: string,
  health: int,
  timeDelta: float,
  gold: int,
  score: int,
};

type position = {
  x: int,
  y: int
};

type tile =
  | GROUND
  | ROUGH
  | STAIRS_UP
  | STAIRS_DOWN
  | WATER
  | WALL
  | EXIT;

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

  let decodePosition = (json: Js.Json.t): position =>
    Decode.{
      x: json |> field("x", Decode.int),
      y: json |> field("y", Decode.int)
    };

  let decodeTile = (tileString): tile =>
    switch(tileString) {
    | "Ground" => GROUND
    | "Rough" => ROUGH
    | "Water" => WATER
    | "Wall" => WALL
    | "Exit" => EXIT
    | "Stairs" => STAIRS_UP
    | "StairsUp" => STAIRS_UP
    | "StairsDown" => STAIRS_DOWN
    | _ => WALL
    };
};
