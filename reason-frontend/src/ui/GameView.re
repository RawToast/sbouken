
open ReasonReact;
open Domain;

type inxPlace = { i: int, place: place };
type inxRow = { i: int, place: list(inxPlace) };

let size = 6;
let fullSize = 1 + (size * 2);
let blocks = Rationale.RList.repeat({tile: WALL, visbility: 0, occupier: None, tileEffect: None  }, size);
let allBlocks = blocks |> List.map(_ => blocks);

let viewport = (player: Domain.player, area: Domain.level) => {
  let update = (x, y, meta: Domain.meta) => {
    
    meta;
  };

  let updatedBlocks = allBlocks
    |> List.mapi((y, ys) => ys |> List.mapi((x, m) => update(x, y, m)));

  updatedBlocks;
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
