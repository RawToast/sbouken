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
      |> toEqual({
           x: 6,
           y: 1
         })
    );
  });
  
  describe("Decode Full Json", () =>
    test("Decodes valid json", () =>
      expect(true) |> toBe(true)
    )
  );
});
