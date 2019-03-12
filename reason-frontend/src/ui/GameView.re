
open ReasonReact;
open Domain;
open Types;

type inxPlace = { i: int, place: place };
type inxRow = { i: int, place: list(inxPlace) };

let viewport = (player: Domain.player, area: Domain.level) => {
  open Rationale;
  let size = 6;
  let fullSize = 1 + (size * 2);
  // let (x, y) = player.location;
  let blocks = RList.repeat({tile: WALL, state: Empty, tileEffect: NoEff, visible: false  }, size);
  
  let pt1 = area |> List.map(ys => blocks @ ys @ blocks);

  let row = pt1 |> List.hd 
                |> List.length 
                |> RList.repeat({tile: WALL, state: Empty, tileEffect: NoEff, visible: false })
                |> RList.repeat(_, size);

  (row @ pt1 @ row)
    |> RList.drop(y) 
    |> RList.take(fullSize)
    |> List.map(l => RList.drop(x, l) |> RList.take(fullSize));
};

let component = ReasonReact.statelessComponent("GameView");

let make = (~game: Domain.response, ~takeInput, _children) => {
  ...component,
  render: (_) => 
    <div>
      <GameStats player=(game.player) turn=(game.player.timeDelta) level=(game.level.name) />
      <GameMap 
          area=(game.level.area |> viewport(game.player)) 
          takeInput=(takeInput)
          />
    </div>
};
