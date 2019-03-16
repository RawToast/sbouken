open Jest;

describe("Patch Notes", () =>
  ExpectJs.(
    test("Renders with no input", () => {
      let component = ReactShallowRenderer.renderWithRenderer(<PatchNotes />);

      expect(Js.Undefined.return(component)) |> toBeDefined;
    })
  )
);
