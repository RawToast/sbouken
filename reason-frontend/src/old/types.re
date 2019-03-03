/* Basic types */
type stats = {
  health: int,
  speed: float,
  position: float,
  damage: int
};

type player = {
  name: string,
  stats: stats,
  gold: int,
  location: (int, int)
};

type ai = {
  moveRange: int,
  terrainCost: bool,
  mustSee: bool,
  memory: option((int, int)),
  attackRange: int,
  flying: bool,
  swim: bool,
  seedark: bool,
  small: bool
};

type enemy = {
  id: string,
  name: string,
  stats: stats,
  ai: ai
};

type enemyInfo = {
  enemy: enemy,
  location: (int, int)
};

type link = {
  id: int,
  level: string,
};

type tileEffect = 
  | Trap(int)
  | Snare(float)
  | Heal(int)
  | Gold(int)
  | NoEff;

type tile =
  | GROUND
  | ROUGH
  | STAIRS(link)
  | WATER
  | WALL
  | EXIT(int);

type occupier = 
  | Player(player)
  | Enemy(enemy)
  | Empty;

type place = {
  visible: bool,
  tile: tile,
  state: occupier,
  tileEffect: tileEffect
  /* illuminance: int */
};

type area = list(list(place));

type level = {
  name: string,
  map: area,
};

type playerArea = {
  player: player,
  area: area,
};

type world = {
  levels: list(level),
  current: string
};

type game = {
  player: player,
  world: world,
  turn: float
};

type error = 
  | InvalidState
  | ImpossibleMove;

type actionResult = 
  | Ok(game)
  | EndGame(int, string)
  | Error(string);

let error = (err) => Js.Result.Error(err);

let success = (ok) => Js.Result.Ok(ok);

let isPlayer = place => switch place.state {
  | Player(_) => true
  | _ => false
  };

/* Modules */
module type World = {
  let updateLevel: (level, world) => world;
  let currentLevel: world => option(level);
  let selectLevel: (string, world) => option(level);
};

module type WorldBuilder = {
  let create: player => world;
};

module type WorldCreator = {
  let buildPlace: string => place;
  let buildArea: string => area;
  let buildLevel: (string, string) => level;
  let loadWorldAsync: (string, list((string, string))) => Js.Promise.t(world);
};

module type Game = {
  let create: string => game;
  let attack: (int, int, game) => actionResult;
  let movePlayer: (int, int, game) => actionResult;
  let useStairs: game => actionResult;
  let useExit: game => actionResult;
  let resultUpdateVision: actionResult => actionResult;

  let updateVision: game => game;
};

module type AsyncGame = {
  let create: string => Js.Promise.t(game);
  let attack: (int, int, game) => actionResult;
  let movePlayer: (int, int, game) => actionResult;
  let useStairs: game => actionResult;
  let useExit: game => actionResult;
};

module Operators = {
  let isOk = r => switch(r) {
    | Ok(_) => true
    | _ => false
    };
  
  /* flatMap on ActionResult */
  let (>>=) = (r, f) => switch(r) {
    | Ok(gam) => f(gam)
    | Error(err) => Error(err)
    | EndGame(score, name) => EndGame(score, name)
    };
  
  /* Default operator */
  let (|?) = (r, g) => switch(r) {
    | Ok(gam) => gam
    | _ => g
    };
};