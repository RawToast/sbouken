open Domain;
open ReasonReact;
let component = ReasonReact.statelessComponent("GameMap");

module GameElements = {
  let makeEnemy = (e: enemyInfo) =>
    switch (e.name) {
    | "Zombie"   => ("Z", "enemy")
    | "Minotaur" => ("M", "enemy")
    | _          => ("X", "enemy")
    };

  let makeObject = (t: Domain.meta, default) =>
    t.tileEffect
    |> Rationale.Option.map(tileEffect =>
         switch (tileEffect) {
         | TRAP  => (",", "trap")
         | SNARE => (";", "snare")
         | HEAL  => ("+", "health")
         | GOLD  => ("g", "gold")
         }
       )
    |> Rationale.Option.default(default);

  let stateToElement = (~incVisible=true, place: meta, default) =>
    place.occupier
    |> Rationale.Option.map(occupier =>
         switch (occupier) {
         | Player => ("O", "player")
         | Enemy(e) => makeEnemy(e)
         | Unknown => ("?", "Unknown")
         }
       )
    |> Rationale.Option.map(((txt, claz)) =>
         if (!(place.visbility > 0)) {
           (".", claz ++ " map-not-visible");
         } else {
           (txt, claz ++ " map-visible");
         }
       )
    |> Rationale.Option.default(default);

  let tilesToElements = (index, places) =>
    places
    |> List.mapi((i, place: Domain.meta) =>
         (
           switch (place.tile) {
           | GROUND      => makeObject(place, (".", "ground")) |> stateToElement(place)
           | ROUGH       => makeObject(place, (":", "rough")) |> stateToElement(place)
           | WATER       => makeObject(place, ("w", "water")) |> stateToElement(place) |> (((s, c)) => (s, c ++ " map-water"))
           | WALL        => ("#", "wall") |> stateToElement(~incVisible=false, place)
           | STAIRS_UP   => makeObject(place, ("/", "stairs")) |> stateToElement(place)
           | STAIRS_DOWN => makeObject(place, ("\\", "stairs")) |> stateToElement(place)
           | EXIT        => makeObject(place, ("e", "exit")) |> stateToElement(place)
           }
         )
         |> (
           ((str, clazz)) =>
             <text key={string_of_int(index) ++ "x" ++ string_of_int(i)} className={"map-" ++ clazz ++ " map"}>
               {string(str)}
             </text>
         )
       );

  let asElements: list(list(Domain.meta)) => list(list(ReasonReact.reactElement)) =
    map => map |> List.mapi((i, es) => es |> tilesToElements(i)) |> List.map(li => [<br />, ...li]);
};

let handleKeyPress = (takeInput, evt: Dom.keyboardEvent) => {
  evt
  |> Webapi.Dom.KeyboardEvent.code
  |> (
    code =>
      switch (code) {
      | "KeyQ" => takeInput("NorthWest")
      | "KeyW" => takeInput("North")
      | "KeyE" => takeInput("NorthEast")
      | "KeyA" => takeInput("West")
      | "KeyD" => takeInput("East")
      | "KeyZ" => takeInput("SouthWest")
      | "KeyX" => takeInput("South")
      | "KeyC" => takeInput("SouthEast")
      | "KeyS" => takeInput("Use") /* Alt key for wait? */
      | _ => Js.Console.log("Unrecognized command")
      }
  );
  ();
};

open Webapi.Dom;

let make = (~area: list(list(Domain.meta)), ~takeInput, _children) => {
  ...component,
  didMount: _ => document |> Document.addKeyDownEventListener(handleKeyPress(takeInput)),
  render: _self =>
    <div className="GameMap">
      {
        GameElements.asElements(area)
        |> List.rev
        |> List.map(ts => ts |> Array.of_list |> ReasonReact.array)
        |> Array.of_list
        |> ReasonReact.array
      }
    </div>,
};
