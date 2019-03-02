open Jest;
open Expect;


describe("Tests", () =>
  describe("with jest", () => {
    test("true is true", (_) => {  
        expect(true) |> toBe(true);
    });
  })
)
