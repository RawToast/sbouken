
open ReasonReact;
open Domain;

type inxPlace = { i: int, place: place };
type inxRow = { i: int, place: list(inxPlace) };

let size = 6;
let fullSize = 1 + (size * 2);
let blocks = Rationale.RList.repeat({tile: WALL, visbility: 0, occupier: None, tileEffect: None  }, size);
let allBlocks = blocks |> List.map(_ => blocks);

let viewport = (player: Domain.player, level: Domain.level) => {
  open Rationale;
  let playerPos = level.playerLocation;
  let min = v => {
    let x = v - size;
    if (0 > x) 0 else x
  };
  let max = v => {
    let x = v + size;
    if (x > fullSize) fullSize else x
  };
  let minX = min(playerPos.x);
  let minY = min(playerPos.y);
  let maxX = max(playerPos.x);
  let maxY = max(playerPos.y);

  /**  player at 25, 25 on a map of 50, 50
  * mins are 19, maxes are 31
  * filter x/y 19-31
  * set 25/25 to 6, 6?   
  * set 19/19 to 0, 0
  * set 31/31 to 12, 12
  **/ 
  let restrictedArea = level.area 
    |> RList.filter_map(
      p => (p.position.x >= minX) && (maxX >= p.position.x) && (p.position.y >= minY) && (maxY >= p.position.y),
      p => {...p, position: {x: p.position.x - minX, y: p.position.y - minY}}
    );

  let update = (x, y, meta) => {
    restrictedArea 
      |> RList.find(a => a.position.x == x && a.position.y == y)
      |> Option.map(p => p.meta)
      |> Option.default(meta);
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
          area=(game.level |> viewport(game.player)) 
          takeInput=(takeInput)
          />
    </div>
};
