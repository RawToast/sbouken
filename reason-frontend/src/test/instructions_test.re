open Jest;

describe("Instructions", () => {
  open ExpectJs;

  test("Renders with no input", () => {
    let component = ReactShallowRenderer.renderWithRenderer(<Instructions />);
    
    expect(Js.Undefined.return(component)) |> toBeDefined;
  })
})
