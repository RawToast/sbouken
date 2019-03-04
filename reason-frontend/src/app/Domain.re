type player = {
  name: string,
  health: int,
  timeDelta: float,
  gold: int,
  score: int,
};

type position = {
  x: int,
  y: int,
};

type tile =
  | GROUND
  | ROUGH
  | STAIRS_UP
  | STAIRS_DOWN
  | WATER
  | WALL
  | EXIT;

type tileEffect =
  | TRAP
  | SNARE
  | HEAL
  | GOLD;

type enemyInfo = {
  name: string,
  description: string,
};
type occupier =
  | Player
  | Enemy(enemyInfo)
  | Unknown;

type meta = {
  tile,
  visbility: int,
  occupier: option(occupier),
  tileEffect: option(tileEffect),
};

type place = {
  position,
  meta,
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

  let decodePosition = (json: Js.Json.t): position =>
    Decode.{x: json |> field("x", Decode.int), y: json |> field("y", Decode.int)};

  let decodeTile = tileString: tile =>
    switch (tileString) {
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

  let decodeTileEffect = tileEffectString: option(tileEffect) =>
    switch (tileEffectString) {
    | "Trap" => Some(TRAP)
    | "Snare" => Some(SNARE)
    | "Heal" => Some(HEAL)
    | "Gold" => Some(GOLD)
    | _ => None
    };

  let enemyDecoder = (json: Js.Json.t): enemyInfo =>
    Decode.{name: json |> field("name", Decode.string), description: json |> field("description", Decode.string)};

  let decodeOccupier = (json: Js.Json.t): option(occupier) =>
    Decode.(
      json
      |> field("kind", Decode.string)
      |> (
        s =>
          switch (s) {
          | "Player" => Some(Player)
          | "Unknown" => Some(Unknown)
          | "Enemy" => Some(Enemy(enemyDecoder(json)))
          | _ => None
          }
      )
    );

  let decodeMeta = (json: Js.Json.t): meta =>
    Decode.{
      tile: json |> field("tile", Decode.string) |> decodeTile,
      visbility: json |> field("visibility", Decode.int),
      occupier: json |> optional(field("occupier", decodeOccupier)) |> Rationale.Option.flatten,
      tileEffect: json |> optional(field("tileEffect", Decode.string)) >>= decodeTileEffect,
    };

  let decodePlace = (json: Js.Json.t): place =>
    Decode.{position: json |> field("position", decodePosition), meta: json |> field("meta", decodeMeta)};
};
