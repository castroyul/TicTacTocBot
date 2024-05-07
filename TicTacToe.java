// Tic Tac Toe Game App
// Dye and Castro Inc.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random; 

public class TicTacToe extends JPanel implements ActionListener {
    JComboBox <String> difficultyMenu;
    JLabel turnText = new JLabel("   X Turn   ");
    JButton newGame = new JButton("New Game");
    JLabel X_Score = new JLabel("X:  -");
    JLabel O_Score = new JLabel("     O:  -");
    JButton [][] board = new JButton [3][3];
    String [] dificultyList = new String [] {"Easy", "Medium", "Hard", "Two Players"};
    int xScore = 1, oScore = 1, totalMoves = 0;
    String currentPlayer = "X";
    private int x = 0;
    private int y = 0;
    
    public TicTacToe() {
        this.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        JPanel scorePanel = new JPanel();
        JPanel boardPanel = new JPanel();
        
        Font appFontLarge = new Font("Arial", Font.PLAIN, 22);
        Font appFontSmall = new Font("Arial", Font.PLAIN, 18);
        
        // Menu Panel
        difficultyMenu = new JComboBox<String>(dificultyList);
        difficultyMenu.setFont(appFontLarge);
        turnText.setFont(appFontLarge);
        newGame.setFont(appFontLarge);
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuPanel.add(difficultyMenu);
        menuPanel.add(turnText);
        menuPanel.add(newGame);
        
        // Score Panel
        X_Score.setFont(appFontSmall);
        O_Score.setFont(appFontSmall);        
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(X_Score);
        scorePanel.add(O_Score);
        
        northPanel.setLayout(new BorderLayout());
        northPanel.add(menuPanel, BorderLayout.NORTH);
        northPanel.add(scorePanel, BorderLayout.SOUTH);
        
        //Board Panel
        boardPanel.setLayout(new FlowLayout());
        for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j++) {
				JButton btn = new JButton();
				btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 100 ));
                btn.setPreferredSize(new Dimension(160, 160));
				board[i][j] = btn;
				btn.addActionListener(this);
				boardPanel.add(btn);
			}
		}
        
        // Adding the panels 
        this.add(northPanel, BorderLayout.NORTH);
        this.add(boardPanel, BorderLayout.CENTER);
        
        // Action Listeners
        difficultyMenu.addActionListener(this);
        newGame.addActionListener(this);
    }  
    
    private void togglePlayer() {
		if (currentPlayer.contentEquals("X")) {
			currentPlayer = "O";
            turnText.setText("   O Turn   ");
		}
		else {
			currentPlayer = "X";
            turnText.setText("   X Turn   ");
		}
	}
    private boolean hasWinner() {
        for (int i = 0; i < 3; i++){
            if (board[i][0].getText() == currentPlayer && board[i][1].getText() == currentPlayer && board[i][2].getText() == currentPlayer){
                return true;
            }
            if (board[0][i].getText() == currentPlayer && board[1][i].getText() == currentPlayer && board[2][i].getText() == currentPlayer){
                return true;
            }
        }
		if (board[0][0].getText() == currentPlayer && board[1][1].getText() == currentPlayer && board[2][2].getText() == currentPlayer) {
            return true;
		}
		if (board[2][0].getText() == currentPlayer && board[1][1].getText() == currentPlayer && board[0][2].getText() == currentPlayer) {
            return true;
		}
        return false;
	}
    private void resetBoard() {
		for(int i = 0; i < 3; i ++) {
			for(int j = 0; j < 3; j++) {
				board[i][j].setText("");
			}
		}
	}
    public static boolean isComplete(JButton [][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (!((board[r][c].getText().equals("X") || (board[r][c].getText().equals("O"))))){
                    return false;
                }
            }
        }
        return true;
    }
    private void updateData() {
        if (totalMoves > 4) {
            if (hasWinner()) {
                if (currentPlayer.contentEquals("X")){
                    X_Score.setText("X: " + xScore++);
                }
                else {
                    O_Score.setText("    O: " + oScore++);
                }
                totalMoves = 0;
                JOptionPane.showMessageDialog(null, "Player "+ currentPlayer +" has won!");
                resetBoard();
            }
            else if (isComplete(board)) {
                totalMoves = 0;
                JOptionPane.showMessageDialog(null, "Draw!");
                resetBoard();
            }
        }
    }
    
    //////////// A. I. CODE
    public int [] bestMove() { // A.I will play as O
        int [] out = new int[2];
    	String [][] array = makeArray();
    	
        if (winningSpot() != null) {
            out = winningSpot();
            return out;
        }
        else if (totalMoves == 1 && array[1][1] == "") {       	 
            out[0] = 1;
       	    out[1] = 1;
            return out;
        }
        else if (totalMoves == 1 && array[1][1] == "X") {
            if (array[0][0] == "") {
                out[0] = 0;
                out[1] = 0;
                return out;
            }
            else if (array[0][2] == "") {
                out[0] = 0;
                out[1] = 0;
                return out;
            }
            else if (array[2][2] == "") {
                out[0] = 0;
                out[1] = 0;
                return out;
            }
            else if (array[2][0] == "") {
                out[0] = 0;
                out[1] = 0;
                return out;
            } 
        }
        else if (totalMoves > 2) {
            // check if human has 2 in a row.
            //horizontal 
            for (int i = 0; i < 3; i++) {
                if ((array[i][0] == "X" && array[i][1] == "X") || (array[i][1] == "X" && array[i][2] == "X") || (array[i][2] == "X" && array[i][0] == "X")) {
                    for (int j = 0; j < 3; j++) {
                        if (array[i][j] == "") {
                            out[0] = i;
                            out[1] = j;
                            return out;
                        }
                    }		    
                }
            }
            // Vertical
            for (int i = 0; i < 3; i++) {
                if ((array[0][i] == "X" && array[1][i] == "X") || (array[1][i] == "X" && array[2][i] == "X") || (array[2][i] == "X" && array[0][i] == "X")) {
			        for (int j = 0; j < 3; j++) {
			           if (array[j][i] == "") {
                           out[0] = j;
                           out[1] = i;
                           return out;		        	  
			           }
                    }			    
                }
            }
            // Diagonal
            if ((array[0][0] == "X" && array[1][1] == "X") || (array[1][1] == "X" && array[2][2] == "X") || (array[2][2] == "X" && array[0][0] == "X")) {
                for (int i = 0; i < 3; i ++) {
                    if (array[i][i] == "") {
                        //System.out.println(i);
                        out[0] = i;
                        out[1] = i;
                        return out;					 
                    }
                }		    
            }
            if ((array[0][2] == "X" && array[1][1] == "X") || (array[1][1] == "X" && array[2][0] == "X") || (array[2][0] == "X" && array[0][2] == "X")) {
                for (int i = 0; i < 3; i ++) {
                    if (array[i][((i-2)* -1)] == "") { 
                        //return the points and quit
                        out[0] = i;
                        out[1] = ((-1) * (i-2));
                        return out;
                    }
                }    
            }
        }
        else if (array[1][1] == "O") {
            for (int i = 0; i < 3; i ++) {
                for (int j = 0; j < 3; j++) {
                    if (array[i][j] == "") {
                        out[0] = i;
        				out[1] = j;
        				return out;
                    }
                }
            }
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (array[i][j] == "") {
                    out[0] = i;
                    out[1] = j;
                    return out;
                }
            }
        }
        
        return out;
        
    }
    public String [][] makeArray(){
    		String array[][] = new String[3][3];
    		
    		 for(int i = 0; i < 3; i ++) {
 		     	for(int j = 0; j < 3; j ++) {
 		     		array[i][j] = board[i][j].getText();
 		     	}
    		 }
    		 return array;
 		} 
    public String checkWinner(String [][] array) {// I made another checkwinner because i dont want to rewrite the old one to handle a 2D array, 
            String winner = null;
                
            // horizontal
            for (int i = 0; i < 3; i++) {
                if (array[i][0] == array[i][1] && array[i][1] == array[i][2] && array[i][2] == array[i][0]) {
                    winner = array[i][0];
                }
            }
            
            // Vertical
            for (int i = 0; i < 3; i++) {
                if (array[0][i] == array[1][1] && array[1][i] == array[2][i] && array[2][i] == array[0][i]) {
                    winner = array[0][i];
                }
            }
            
            // Diagonal
            if (array[0][0] == array[1][1] && array[1][1] == array[2][2] && array[2][2] == array[0][0]) {
                winner = array[0][0];
            }
            if (array[2][0]== array[1][1] && array[1][1] == array[0][2] && array[0][2] == array[2][0]) {
                winner = array[2][0];
            }
            
            int openSpots = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (array[i][j] == "") {
                        openSpots++;
                    }
                }
            }
            
            if (winner == null && openSpots == 0) {
                return "tie";
            } 
            else {
                return winner;
            }	  	
        }
    public int [] winningSpot() {
        	int [] out = new int[2];
        	String [][] array = makeArray();
        	

        	
        	 // check if human has 2 in a row.
        	//horizontal 
    		  for (int i = 0; i < 3; i++) {
    		
    		    if( (array[i][0]== "O" &&  array[i][1] == "O") || 
    		    		(array[i][1]== "O" && array[i][2] == "O")||
    				 ( array[i][2] == "O" && array[i][0] == "O")) {
    		        for(int j =0; j < 3; j++) {
    		           if(array[i][j] == "") {
    		        	  out[0] = i;
    		        	  out[1] = j;
    		        	  return out;
    		           }
    		        }		    
    		    }
    		  }
    		  // Vertical
    		  for (int i = 0; i < 3; i++) {
    				
    			    if( (array[0][i]== "O" &&  array[1][i] == "O") || 
    			    		(array[1][i]== "O" && array[2][i] == "O")||
    					 ( array[2][i] == "O" && array[0][i] == "O")) {
    			        for(int j =0; j < 3; j++) {
    			           if(array[j][i] == "") {
    				        	  out[0] = j;
    				        	  out[1] = i;
    				        	  return out;		        	  
    			           }
    			        }			    
    			    }
    			  }
    		  // Diagonal
    		  if ( (array[0][0]== "O" &&  array[1][1] == "O") || 
    		    		(array[1][1]== "O" && array[2][2] == "O")||
    				 ( array[2][2] == "O" && array[0][0] == "O")) {
    			  for(int i = 0; i < 3; i ++) {
    				  if(array[i][i] == "") {
    					  out[0] = i;
    					  out[1] = i;
    					  return out;					 
    				  }
    			  }		    
    		  }
    		  if ( (array[0][2]== "O" &&  array[1][1] == "O") || 
    		    		(array[1][1]== "O" && array[2][0] == "O")||
    				 ( array[2][0] == "O" && array[0][2] == "O")) {
    			  for(int i = 0; i < 3; i ++) {
    				  if(array[i][((i-2)* -1)] == "") {
    					  //return the points and quit
    					 out[0] = i;
    					 out[1] = ((-1) *(i-2) );
    					 return out;
    				  }
    			  }		    
    		  }
            return null;
        	}
    //////////// A. I. CODE

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                
                // Difficulty Easy
                if (difficultyMenu.getSelectedIndex() == 0) {
                    if (currentPlayer == "X") {
                        if (e.getSource() == board[i][j] && board[i][j].getText() == "") {
                            board[i][j].setText(currentPlayer);
                            totalMoves++;
                            updateData();
                            togglePlayer();
                        }
                    }
                    while (currentPlayer == "O") {
                        Random rand = new Random();
                        int r = rand.nextInt(3);
                        int c = rand.nextInt(3);
                        
                        if (!(r == i && c == j) && board[r][c].getText() == "") {
                            board[r][c].setText(currentPlayer);
                            totalMoves++;
                            updateData();
                            togglePlayer();
                        }
                    }
                }
        
                // Difficulty Medium
                if (difficultyMenu.getSelectedIndex() == 1) {
                    if (currentPlayer == "X") {
                        if (e.getSource() == board[i][j] && board[i][j].getText() == "") {
                            board[i][j].setText(currentPlayer);
                            totalMoves++;
                            updateData();
                            togglePlayer();
                        }
                    }
                    while (currentPlayer == "O") {
                        Random rand = new Random();
                        int r = rand.nextInt(3);
                        int c = rand.nextInt(3);
                        
                        if (!(r == i && c == j) && board[r][c].getText() == "" && totalMoves <= 2) {
                            board[r][c].setText(currentPlayer);
                        }
                        else {
                            int [] array = bestMove();
                            board[array[0]][array[1]].setText("O");
                        }
                        totalMoves++;
                        updateData();
                        togglePlayer();
                    }
                }
                
                // Difficulty Hard  
                if (difficultyMenu.getSelectedIndex() == 2) {
                    if (currentPlayer == "X") {
                        if (e.getSource() == board[i][j] && board[i][j].getText() == "") {
                            board[i][j].setText(currentPlayer);
                            totalMoves++;
                            updateData();
                            togglePlayer();
                        }
                    }
                    if (currentPlayer == "O") {
                        
                        int [] array = bestMove();
                        board[array[0]][array[1]].setText("O");
                        
                        totalMoves++;
                        updateData();  
                        togglePlayer();     
                    }
                }
                
                // Multiplayer Mode
                if (difficultyMenu.getSelectedIndex() == 3) {
                    if (e.getSource() == board[i][j] && board[i][j].getText() == "") {
                        board[i][j].setText(currentPlayer);
                        totalMoves++;
                        updateData();
                        togglePlayer();
                    }
                }
            }
        }
        
        // New Game Action Button
        if (e.getSource() == newGame) {
            turnText.setText("   X Turn   ");
            X_Score.setText("X:  -");
            O_Score.setText("     O:  -");
            xScore = 1;
            oScore = 1;
            totalMoves = 0;
            resetBoard();
        }
    }
} 