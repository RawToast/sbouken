
module type GameClient = {
  let createGame: string => Js.Promise.t(Domain.response);
};

module Actions(GC: GameClient) = {
  let create = (name) =>
    ReasonReact.SideEffects(
      self =>
        Js.Promise.(
          GC.createGame(name)
          |> then_((serverResponse: Domain.response) => {
               self.send(Domain.StartedGame(serverResponse));
               resolve(serverResponse);
             })
        )
        |> ignore,
    );
};
