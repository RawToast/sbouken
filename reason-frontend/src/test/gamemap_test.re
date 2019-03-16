open Jest;
open Domain;
open GameMap;
open ExpectJs;

describe("GameMap", () => {

  describe("toElementInfo", () => {
    let default = ("test", "test");
    describe("given a tile containing the Player", () => {
      let meta = { tile: GROUND, visbility: 7, occupier: Some(Player), tileEffect: None};
      let (ascii, classType) = GameElements.toElementInfo(meta, default);
      test("returns O", () => expect(ascii) |> toBe("O"))
      test("with a class of 'player'", () => expect(classType) |> toBe("player"))
    })

    describe("given a tile containing a Zombie", () => {
      let zombie = Some(Enemy({name: "Zombie", description: "Thing"}));
      let meta = { tile: GROUND, visbility: 7, occupier: zombie, tileEffect: None};
      let (ascii, classType) = GameElements.toElementInfo(meta, default);
      test("returns Z", () => expect(ascii) |> toBe("Z"))
      test("with a class of 'enemy'", () => expect(classType) |> toBe("enemy"))
    })
  })
  
  describe("makeObject", () => {
    let default = ("test", "test");
    describe("given a tile containing Gold", () => {
      let meta = { tile: GROUND, visbility: 7, occupier: None, tileEffect: Some(GOLD)};
      let (ascii, classType) = GameElements.makeObject(meta, default);
      test("returns g", () => expect(ascii) |> toBe("g"))
      test("with a class of 'player'", () => expect(classType) |> toBe("gold"))
    })

    describe("given a tile containing a Trap", () => {
      let meta = { tile: GROUND, visbility: 7, occupier: None, tileEffect: Some(TRAP)};
      let (ascii, classType) = GameElements.makeObject(meta, default);
      test("returns ,", () => expect(ascii) |> toBe(","))
      test("with a class of 'enemy'", () => expect(classType) |> toBe("trap"))
    })

    describe("given a tile containing a Snare", () => {
      let meta = { tile: GROUND, visbility: 7, occupier: None, tileEffect: Some(SNARE)};
      let (ascii, classType) = GameElements.makeObject(meta, default);
      test("returns ;", () => expect(ascii) |> toBe(";"))
      test("with a class of 'enemy'", () => expect(classType) |> toBe("snare"))
    })

    describe("given a tile containing Health", () => {
      let meta = { tile: GROUND, visbility: 7, occupier: None, tileEffect: Some(HEAL)};
      let (ascii, classType) = GameElements.makeObject(meta, default);
      test("returns +", () => expect(ascii) |> toBe("+"))
      test("with a class of 'enemy'", () => expect(classType) |> toBe("health"))
    })
  })
})
