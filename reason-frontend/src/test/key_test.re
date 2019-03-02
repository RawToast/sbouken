open Jest;

describe("Key", () => {
  open ExpectJs;

  test("Renders with no input", () => {
    let component = ReactShallowRenderer.renderWithRenderer(<Key />);
    
    expect(Js.Undefined.return(component)) |> toBeDefined;
  })
})
