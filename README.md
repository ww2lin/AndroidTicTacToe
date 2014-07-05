AndroidTicTacToe
================

A simple Tic-Tac-Toe game -customizable board size from 3 to 8 -customizable board wining constraint 3-8 -AI ( randomized and greedy) -MVC architecture -Available in java Application and Android platform

Program:
Using a Model-View-Controller (MVC) architecture, created a Android program 
to play the classic 3 x 3 tic-tac-toe game. When someone wins the button will
turn green to show the wining line

supports a options, where user are allowed to change some of the settings
e.g, name,boardsize, computer, etc.
Pressing confirm will result the changes to be commited, and the game will be
restarted.

press cancel will allow the user to resume their game.

while in the options screen(first time the application starts up), the user
can click cancel, or back, in reuslt of doing so, the game is loaded with 
default parameters, Note this is the only for the first time that the 
application starts up.

Enhancements:
	all the options below can be change through the interface
	-board size
		can scale the board size to a bigger board
		it is hardcoded to 8(for the upperbound)
		but can easily be change inside the source code

	-wining constraint
		the defualt rule to win is to get a 3 in a row.
		but this can be change up to the board size
		(note: if winConstraint > current selected boardSize then the
		game will aways tie, this is an intended behaviour)

	-AI
		2 simple computer
			-random (places random piece)
			-greedy (where it tries to get most points)
		user can select "two player" it he/she does not want
		to play with computers
