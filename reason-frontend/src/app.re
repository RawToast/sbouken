open Utils;

open ReasonReact;
open Webapi.Dom;

requireCSS("./App.css");

type view =
  | Home
  | InGame(Domain.response)
  | Score(string, int);

let component = ReasonReact.reducerComponent("App");

let handleEffect = result =>
  switch (result) {
  | Actions.StartedGame(response) => InGame(response) |> (r => ReasonReact.Update(r))
  | Actions.UpdatedGame(response) => InGame(response) |> (r => ReasonReact.Update(r))
  | Actions.EndGame(response) =>
    Score(response.player.name, response.player.score) |> (r => ReasonReact.Update(r))
  | Error(error) =>
    Js.Console.error(error);
    ReasonReact.NoUpdate;
  };

let module AsyncActions = Actions.Actions(Client);

let nappReducer = (act: Actions.action, view) =>
    switch (act) {
    | Actions.AppAction(appAction) =>
      switch (appAction) {
      | Actions.StartGame(name) => AsyncActions.create(name)
      | Actions.Begin(response) => ReasonReact.Update(InGame(response))
      }
    | Actions.GameAction(gameAction) => 
      switch gameAction {
      | Actions.KeyboardInput(str) => AsyncActions.keyboardInput(str) 
      };
    | Actions.Effect(effect) => handleEffect(effect);
    };

let make = _children => {
  ...component,
  initialState: () => Home,
  reducer: nappReducer,
  render: self =>
    <div className="App">
      (
        switch (self.state) {
        | Home =>
          <StartView
            startGame=(string => self.send(Actions.StartGame(string)))
          />
        | Score(name, score) =>
          <div>
            <div>
              (
                ReasonReact.string(
                  name ++ " scored " ++ string_of_int(score) ++ " points",
                )
              )
            </div>
            <button onClick=(_ => Location.reload(location))>
              (string("Try again"))
            </button>
          </div>
        | InGame(response) =>
          <GameView
            game = response
            takeInput = ((command) => self.send(Actions.GameAction(Actions.KeyboardInput(command))))
            // useExit=(() => self.send(GameAction(UseExit)))
          />
        }
      )
    </div>,
};
