open Jest;

describe("Key", () =>
  ExpectJs.(
    test("Renders with no input", () => {
      let component = 
        ReactShallowRenderer.renderWithRenderer(<Key />);

      expect(Js.Undefined.return(component)) |> toBeDefined;
    })
  )
);
