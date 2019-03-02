open Types;
open Rationale;

module Enemies = {
  let randId = () => Js.Math.random() |> string_of_float;

  let makeZombie = () => { 
    id: randId(), name: "Zombie", 
    stats: { health: 5, speed: 0.7, position: 0., damage: 1 }, 
    ai: { moveRange: 5, terrainCost: false, mustSee: true, memory: None, attackRange: 1, flying: false, swim: false, seedark: false, small: false }};
  let makeEnemy = () => { 
    id: randId(), name: "Enemy", 
    stats: { health: 3, speed: 1., position: 0., damage: 2 },
    ai: { moveRange: 6, terrainCost: true, mustSee: true, memory: None, attackRange: 1, flying: false, swim: false, seedark: false, small: false }};
  let makeMinotaur = () => {
    id: randId(), name: "Minotaur", 
    stats: { health: 9, speed: 1., position: 0., damage: 3 },
    ai: { moveRange: 8, terrainCost: true, mustSee: false, memory: None, attackRange: 1, flying: false, swim: false, seedark: false, small: false }};

  let addEnemy = (str, place) => {
    switch str {
    | "Z" => { ... place, state: Enemy(makeZombie()) }
    | "X" => { ... place, state: Enemy(makeEnemy()) }
    | "M" => { ... place, state: Enemy(makeMinotaur()) }
    | _ => place
    };
  };
}
