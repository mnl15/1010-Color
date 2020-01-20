=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
Mona Lee 
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

As the final project for my Introduction to Programming Languages and Techniques class at the University of Pennsylvania, I implemented the popular game 1010. The core computer science concepts I used and more about my implementation are explained below. Once the game is opened, more detailed instructions about game play are provided.  


===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays 
  		I used a 2D array to store the 10x10 board and each of the Squares that
  		display the pieces. This feature is an appropriate use for the concept 
  		because the board is displayed as a grid where each square has an x and y
  		location. A 2D array lets me iterate through the array in order to paint 
  		all the squares and easily determine if a row or a column is full in order 
  		to clear it for the game. 

  2. Collections 
  		I used LinkedLists in multiple parts of my game. First, I used a LinkedList 
  		in the FileReader for each of the lines in my high scores file. A LinkedList
  		is best because the size is easily changed and only the order matters. A 
  		LinkedList is also used for my pieces to store the offsets which means the 
  		location of each of the squares in the specific piece. Again, the offsets 
  		can have any number of elements and I never need to access a certain element 
  		at a specific location. Lastly I use a LinkedList to store all the possible 
  		"legal" moves for the pieces during a given state. 

  3. File I/O
  		I used File I/O for the high scores in the game. The file that is used 
  		contains the high scores and the name of the player that got that score. 
  		I read in the high scores from this file and they, including the names 
  		are displayed on the screen. Then at the end of each game, the high scores
  		are updated and written to the same file with the new score if the player 
  		scored better than any of the initial top three scores. This feature is 
  		appropriate because the scores are stored for each game and for any game 
  		going forward, not just within one game. 

  4. Testable Component 
  		The main state of my game is the board/2D array and I used testing for 
  		testing whether the program correctly determined whether a piece could be 
  		placed in a specified position or not. Also I tested whether the 2D array 
  		for the board is updated correctly and if the game ends correctly. 


=======================
=: My Implementation :=
=======================

- Overview of each of the classes in my code, and what their
  function is in the overall game.
	- SQUARE: represents each of the squares in the 10x10 board for the game. These 
	  are what makeup the 2D array in the Game class. They contain whether the 
	  square is occupied by a piece and what the color of the piece is.
	- BOX: represents the boxes that contain the three pieces that the player 
	  must place on the board. These are used by the Game and GameState class. 
	  They contain the piece or nothing if the piece was already replaced on the 
	  board. 
	- PIECE: represents the pieces that the player puts on the board. They start
	  in the boxes then the user interacts with them in the Game class to place 
	  them on the board. They have a color and contain information about the 
	  locations of the individual squares that makeup the piece. 
	- FILEREADER: functions to read in the high scores files for the game to 
	  utilize and display. This file does not deal with writing out the new scores 
	  after a game ends, only at the beginning in order to initialize the screen
	  with the high scores. 
	- GAMESTATE: models the functionality of the game and contains the 10x10 board,
	  the boxes and their pieces, how the pieces can be placed, and clears the 
	  lines when they are full. This is used by the Game class to make sure the 
	  game functions according to the game rules and updates correctly. It also 
	  presents the window that asks the player for their name. 
	- GAME: presents the game to the user with the high scores, instructions and 
	  board. It reads in the high scores, contains and displays the canvas, 
	  and contains the mouselistener for any clicks the player does. This class acts 
	  as the main class that uses the other components to make the game function. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
    - http://blog.coelho.net/games/2016/07/28/1010-game.html
      This site was used for the different pieces that 1010 offers and more 
      information about scores and such
