
type gameAction =
  | KeyboardInput(string);
  // | MovePlayer(int, int)
  // | TakeStairs
  // | UseExit;

type appAction =
  | StartGame(string)
  | Begin(Domain.response);

type effect =
  | StartedGame(Domain.response)
  | UpdatedGame(Domain.response)
  | EndGame(Domain.response)
  | Error(string);

type action =
  | GameAction(gameAction)
  | AppAction(appAction)
  | Effect(effect);

module type GameClient = {
  let createGame: string => Js.Promise.t(Domain.response);
};

module Actions(GC: GameClient) = {
  let create: string => ReasonReact.update('a, 'b, action) = 
    (name) =>
    ReasonReact.SideEffects(
      self =>
        Js.Promise.(
          GC.createGame(name)
          |> then_((serverResponse: Domain.response) => {
               self.send(Effect(StartedGame(serverResponse)));
               resolve(serverResponse);
             })
        )
        |> ignore
    );

  let keyboardInput: string => ReasonReact.update('a, 'b, action) = 
    _ => ReasonReact.NoUpdate;
};
