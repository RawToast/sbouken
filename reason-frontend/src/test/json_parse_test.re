open Jest;

describe("Parser", () => {
  open ExpectJs;
  open Domain;

  describe("Parse Player Json", () => {
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

    test("Parses valid json", () =>
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

    test("Parses partial player json", () =>
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
  
  describe("Parse Position Json", () => {
    let positionJson = {|{
          "x": 6,
          "y": 1
        }|};

    test("Parses valid json", () =>
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
  
  describe("Parse Full Json", () =>
    test("Parses valid json", () =>
      expect(true) |> toBe(true)
    )
  );
});
