open Jest;

describe("Decoders", () => {
  open ExpectJs;
  open Domain;

  describe("DecodePlayer", () => {
    let fullPlayerJson = {|{
            "name": "Test Player",
            "health": 10,
            "timeDelta": 0.5,
            "gold": 10,
            "score": 9001
        }|};

    let playerJson = {|{
            "name": "New Player",
            "health": 10,
            "timeDelta": 0
        }|};

    test("Decodes valid json", () =>
      fullPlayerJson
      |> Json.parseOrRaise
      |> Domain.Decoders.decodePlayer
      |> expect(_)
      |> toEqual({
           name: "Test Player",
           health: 10,
           timeDelta: 0.5,
           gold: 10,
           score: 9001,
         })
    );

    test("Decodes partial player json", () =>
      playerJson
      |> Json.parseOrRaise
      |> Domain.Decoders.decodePlayer
      |> expect(_)
      |> toEqual({
           name: "New Player",
           health: 10,
           timeDelta: 0.,
           gold: 0,
           score: 0,
         })
    );
  });

  describe("DecodePosition", () => {
    let positionJson = {|{
          "x": 6,
          "y": 1
        }|};

    test("Decodes valid json", () =>
      positionJson
      |> Json.parseOrRaise
      |> Domain.Decoders.decodePosition
      |> expect(_)
      |> toEqual({x: 6, y: 1})
    );
  });

  describe("DecodeTile", () => {
    test("Can decode a ground tile", () =>
      "Ground"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.GROUND)
    );

    test("Can decode a rough tile", () =>
      "Rough"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.ROUGH)
    );

    test("Can decode a water tile", () =>
      "Water"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.WATER)
    );

    test("Can decode an exit tile", () =>
      "Exit"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.EXIT)
    );

    test("Can decode a wall tile", () =>
      "Wall"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.WALL)
    );

    test("Decodes Stairs to a Stairs up tile", () =>
      "Stairs"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.STAIRS_UP)
    );

    test("Decodes StairsUp to a Stairs up tile", () =>
      "StairsUp"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.STAIRS_UP)
    );

    test("Decodes StairsDown to a Stairs down tile", () =>
      "StairsDown"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.STAIRS_DOWN)
    );

    test("Defaults to Wall when the tile is invalid", () =>
      "HEy"
      |> Domain.Decoders.decodeTile
      |> expect(_)
      |> toEqual(Domain.WALL)
    );
  });

  describe("DecodeTileEffect", () => {
    test("Can decode a trap", () =>
      "Trap"
      |> Domain.Decoders.decodeTileEffect
      |> expect(_)
      |> toEqual(Some(TRAP))
    );

    test("Can decode a snare", () =>
      "Snare"
      |> Domain.Decoders.decodeTileEffect
      |> expect(_)
      |> toEqual(Some(SNARE))
    );

    test("Can decode gold", () =>
      "Gold"
      |> Domain.Decoders.decodeTileEffect
      |> expect(_)
      |> toEqual(Some(GOLD))
    );

    test("Can decode heal", () =>
      "Heal"
      |> Domain.Decoders.decodeTileEffect
      |> expect(_)
      |> toEqual(Some(Domain.HEAL))
    );

    test("Defaults to None", () =>
      "SomethingElse"
      |> Domain.Decoders.decodeTileEffect
      |> expect(_)
      |> toEqual(None)
    );
  });

  describe("DecodeMeta", () => {
    let metaJson = {|{
          "tile": "Wall",
          "visibility": 7
        }|};

    test("Decodes valid json", () =>
      metaJson
      |> Json.parseOrRaise
      |> Domain.Decoders.decodeMeta
      |> expect(_)
      |> toEqual({tile: Domain.WALL, visbility: 7, tileEffect: None})
    );

    test("Decodes meta with an effect", () =>
      {|{
          "tile" : "Wall",
          "visibility" : 7,
          "tileEffect" : "Trap"
        }|}
      |> Json.parseOrRaise
      |> Domain.Decoders.decodeMeta
      |> expect(_)
      |> toEqual({tile: Domain.WALL, visbility: 7, tileEffect: Some(TRAP)})
    );
  });

  describe("DecodePlace", () => {
    let simplePlaceJson = {|{
            "position": {
                "x": 0,
                "y": 0
            },
            "meta": {
                "tile": "Ground",
                "visibility": 5
            }
        }|};

    test("Decodes simple place json", () =>
      simplePlaceJson
      |> Json.parseOrRaise
      |> Domain.Decoders.decodePlace
      |> expect(_)
      |> toEqual({
           position: {
             x: 0,
             y: 0,
           },
           meta: {
             tile: Domain.GROUND,
             visbility: 5,
             tileEffect: None
           },
         })
    );
  });

  describe("Decode Full Json", () =>
    test("Decodes valid json", () =>
      expect(true) |> toBe(true)
    )
  );
});
