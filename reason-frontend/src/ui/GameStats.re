open Domain;

let component = ReasonReact.statelessComponent("GameStats");

let make = (~player: player, ~turn: float, ~level:string, _children) => {
  ...component,
  render: _self =>
    <div className="GameStats">
      <text>(ReasonReact.string(player.name ++ 
        " HP: " ++ string_of_int(player.health) ++ 
        " Gold: " ++ string_of_int(player.gold) ++ 
        " Turn: " ++ string_of_float(turn)))</text>
      <div><text>(ReasonReact.string(level))</text></div>
    </div>,
};
