let fullResponse = {|
{
	"id": "f8df1322-128f-4018-95fc-625486810192",
	"player": {
		"name": "the player",
		"health": 10,
		"timeDelta": 0
	},
	"level": {
		"name": "Dungeon 1",
    "playerLocation" : {
      "x": 0,
      "y": 0
    },
		"area": [{
				"position": {
					"x": 0,
					"y": 0
				},
				"meta": {
					"tile": "Ground",
					"visibility": 5,
					"occupier": "Player"
				}
			},
			{
				"position": {
					"x": 0,
					"y": 1
				},
				"meta": {
					"tile": "Ground",
					"visibility": 5,
					"occupier": {
						"name": "Zombie",
						"description": "Is scary"
					},
					"tileEffect": "Gold"
				}
			}
		],
		"tileSet": "default"
	}
}|};
