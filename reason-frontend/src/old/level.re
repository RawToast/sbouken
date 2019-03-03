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
