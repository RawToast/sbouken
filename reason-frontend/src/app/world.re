open Types;

module World: World = {
  let updateLevel = (level, world) => { 
    let newLevels = world.levels 
      |> List.map(l => if (level.name == l.name) { level } else { l });
    
    { ... world, levels: newLevels };
  };

  let selectLevel = (name, world) => world.levels |> Rationale.RList.find(l => l.name == name);

  let currentLevel = (world) => selectLevel(world.current, world);
};

module FetchCsvBuilder = {
  open Level;
  open Rationale;
  
  let create = (player: player) => {
    let (x, y) = player.location;

    let d1 = Utils.requireAssetURI("../../public/world/Dungeon 1.csv");

    let lvls = [("Dungeon 1", d1)];
    
    Worldcreator.CsvWorldBuilder
      .loadWorldAsync("Dungeon 1", lvls)
      |> Js.Promise.then_(world => 
        world 
          |> World.currentLevel
          |> Option.fmap(Level.modifyTile(x, y, {tile: GROUND, state: Player(player), tileEffect: NoEff, visible: true }))
          |> Option.fmap(World.updateLevel(_, world))
          |> Option.default(world) 
          |> Js.Promise.resolve);
  };
};
