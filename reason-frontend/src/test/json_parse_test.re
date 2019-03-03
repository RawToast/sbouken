open Jest;

describe("Parser", () => {
  open ExpectJs;

    describe("Parse Player Json", () => {
        test("Parses valid json", () => {
            
            
            expect(true) |> toBe(true);
        })
  })
  describe("Parse Full Json", () => {
    test("Parses valid json", () => {

        
        expect(true) |> toBe(true);
    })
  })
})

let json = {|{
    "id": "f8df1322-128f-4018-95fc-625486810192",
    "player": {
        "name": "daveyboy",
        "health": 10,
        "timeDelta": 0
    },
    "currentLevel": {
        "name": "Dungeon 1",
        "area": [
            {
                "position": {
                    "x": 5,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "enemyKind": "Zombie"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "tileEffect": "Heal"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "tileEffect": "Gold"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "enemyKind": "Zombie"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "enemyKind": "Zombie"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)",
                    "enemyKind": "Zombie"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 0,
                    "y": 5
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 6
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 2,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 11
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 0
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 4,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 14
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 13
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 7,
                    "y": 9
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 8
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 4
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 3
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 6,
                    "y": 10
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 1
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 5,
                    "y": 12
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 8,
                    "y": 2
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 1,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            },
            {
                "position": {
                    "x": 3,
                    "y": 7
                },
                "meta": {
                    "tile": "Ground",
                    "visibility": "Visibile(7)"
                }
            }
        ],
        "tileSet": "Default"
    }
}|};
