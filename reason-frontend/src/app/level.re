open Types;
open Rationale;

module LevelBuilder = {
  let blankPlace = {tile: GROUND, state: Empty, tileEffect: NoEff, visible: false };
  let makeBlankLevel = (name: string) => {
    let emptyMap =
      RList.repeat(blankPlace, 15) |> List.map(i => RList.repeat(i, 15));
    {name, map: emptyMap};
  };
  
  let makeWaterLevel = (name: string) => {
    let emptyMap =
      RList.repeat({tile: WATER, state: Empty, tileEffect: NoEff, visible: false }, 15) |> List.map(i => RList.repeat(i, 15));
    {name, map: emptyMap};
  };

  let makeLevel = (name, sizeX, sizeY, defaultTile) => {
    let emptyMap =
      RList.repeat({tile: defaultTile, state: Empty, tileEffect: NoEff, visible: false }, sizeY) |> List.map(i => RList.repeat(i, sizeX));
    {name, map: emptyMap};
  };
};

module Tiles = {
  let groundTile = { tile: GROUND, state: Empty, tileEffect: NoEff, visible: false };
  let wallTile = { tile: WALL, state: Empty, tileEffect: NoEff, visible: false };
  let waterTile = { tile: WATER, state: Empty, tileEffect: NoEff, visible: false };

  let isGround = t => switch t {
    | GROUND => true
    | _ => false
    };

  let cantSeeThrough = t => switch t.tile {
    | WALL => true
    | _ => false
    };

  let isWall = t => switch t {
    | WALL => true
    | _ => false
    };

  let isEnemy = t => switch t.state {
    | Enemy(_) => true
    | _ => false
    };

  let hasEffect = t => switch t.tileEffect {
    | NoEff => false
    | _ => true
    };

  let isSnare = t => switch t.tileEffect {
    | Snare(_) => true
    | _ => false
    };

  let isGold = t => switch t.tileEffect {
    | Gold(_) => true
    | _ => false
    };

  let isHeal = t => switch t.tileEffect {
    | Heal(_) => true
    | _ => false
    };

  let isStairs = t => switch t.tile {
    | STAIRS(_) => true
    | _ => false
    };

  let isEmpty = t => switch t.state {
    | Empty => true
    | _ => false
    };
  
  let isExit = t => switch t {
    | EXIT(_) => true
    | _ => false
    };

  let tilePenalty = t => switch t {
    | WATER => 1.5
    | ROUGH => 1.1
    | _ => 1.
    };

  let statePenalty = t => switch t {
    | Enemy(_) => 3.
    | _ => 0.
    };

  let objPenalty = (incTraps, t) => switch t {
    | Trap(_) => if (incTraps) 3. else 0.
    | Snare(_) => if (incTraps) 3. else 0.
    | _ => 0.
    };

  let placePenalty = (~incTraps=false, t)  => tilePenalty(t.tile) +. statePenalty(t.state) +. objPenalty(incTraps, t.tileEffect);

  let placePenaltyNoEnemy = t => tilePenalty(t.tile);

  let canOccupy = p => 
    if (isEnemy(p)) false
    else if(isWall(p.tile)) false
    else isEmpty(p);

  let canOccupyOrAttack = p => 
    if (isEnemy(p)) false
    else if(isWall(p.tile)) false
    else true;
};

module Level = {
  let modifyTile = (x: int, y: int, newPlace: place, level: level) => {
      let updateMap =
        List.mapi((xi: int, xs: list(place)) =>
          if (xi == y) {
              xs |> 
               List.mapi((yi: int, place: place) =>
                 if (yi == x) newPlace else place);
          } else xs
        );
      { name: level.name, map: updateMap(level.map) };
  };

  let modifyTiles = (points: list((int, int)), newPlace: place, level: level) =>
    points |> List.fold_left((l, ((x:int, y: int))) => modifyTile(x, y, newPlace, l), level);

};
