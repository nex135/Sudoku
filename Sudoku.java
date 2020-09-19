/**
 * By: Kevin Tan and Ivy Liu
 * Teacher: Ms.Martin
 * Date: December 17, 2018
 * Due Date: January 17th, 2019
 * File Name: Sudoku.java
 * Purpose: To run a Sudoku Game with six templates for the player to choose, and special features like bonus cells and coloured wrong answers
 */ 

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.util.Scanner;
import javax.sound.sampled.*;


public class Sudoku extends JFrame implements ActionListener {
  
  //Declare the Colour Variable (for special cell colours)
  Color regularBackground = new Color(245,245,245);
  Color specialBackground = new Color(102,255,255);
  Color falseBackground = new Color(255,51,51);
  
  //Declare variables (not for display)
  String [][] templateArr = new String [9][9];
  String [][] answerArr = new String [9][9];
  int [] specialCell = new int [2];
  int musicCount = 0;
  
  //for background music
  Clip clip;
  
  //Declare the panels
  JPanel welcome = new JPanel(); //The first panel that welcomes players
  JPanel info = new JPanel (); //Displays information on how the game is played
  JPanel selection = new JPanel (); //Displays the different puzzles that the player can choose from
  JPanel puzzle = new JPanel(); //The panel that will display the sudoku puzzle, through text fields
  JPanel buttonPanel = new JPanel(); //The panel that displays the SUBMIT & BONUS buttons on the game panel
  JPanel congrats = new JPanel (); // The panel that shows when you succesfully solve a puzzle
  JPanel fail = new JPanel(); //The panel that shows when you do not succesfully solve a puzzle
  JPanel secondaryCongrats = new JPanel (); //Panel that displays the text on congrats
  JPanel secondaryFail = new JPanel (); //Panel that displays the text on fail
  JPanel buttonCongrats = new JPanel ();//Holds buttons for congrats
  JPanel buttonFail = new JPanel();//Holds buttons for fail
  JPanel buttonSelection = new JPanel (); //holds button on selection
  JPanel buttonStart = new JPanel ();//holds info and start button on welcome
  JPanel secondaryPuzzle = new JPanel ();//holds the 81 text fields in puzzle
  
  
  //Create GUI components (labels, buttons, textFields)******************************************************************
  
  //Declare the images
  ImageIcon logo; //For logo on welcome panel
  ImageIcon music; //For music button (shows when music is playing)
  ImageIcon mute; //For mute button (shows when music is off)
  
  //Welcome panel compononents
  JLabel ourNameLabel = new JLabel ("By: Kevin & Ivy");
  JLabel logoLabel; //For logo on welcome panel
  JButton startButton = new JButton ("START");
  JButton infoButton = new JButton ("INFO");
  JButton soundButton; //For music/mute button on welcome panel
  
  //String of game information to be appended to info JTextArea
  String information = "Sudoku is a logic-based, number-placement puzzle. The goal of the game is to fill the 9x9 grid "+ 
    "in a manner so that each 3x3 sub-grid and each row and column the "+ 
    "numbers 1 to 9. In addition, our version of Sudoku has a bonus function, where "+
    "a random box will be selected, and during the game, if you submit the correct answer "+ 
    "for that box, you will receive the answers for 3 other boxes. If you do "+ 
    "submit an incorrect answer, upon trying again, the incorrect boxes will be coloured red.";
  
  //Info panel compononents
  JTextArea infoDump = new JTextArea (25,50); //Area to display game information & instructions
  JButton startGameButton = new JButton ("START"); //Button that leads to the selection panel
  
  //Selection panel components
  JLabel chooseLabel = new JLabel ("Choose a puzzle ~ ~");
  JButton sudoku1 = new JButton ("Puzzle 1"); //Button to read in the first puzzle template
  JButton sudoku2 = new JButton ("Puzzle 2"); //Button to read in the second puzzle template  
  JButton sudoku3 = new JButton ("Puzzle 3"); //Button to read in the third puzzle template
  JButton sudoku4 = new JButton ("Puzzle 4"); //Button to read in the forth puzzle template
  JButton sudoku5 = new JButton ("Puzzle 5"); //Button to read in the fifth puzzle template
  JButton sudoku6 = new JButton ("Puzzle 6"); //Button to read in the sixth puzzle template
  
  //Game panel componenets
  JButton submitButton = new JButton ("SUBMIT");
  JButton bonusButton = new JButton ("BONUS");
  //JButton cheatButton = new JButton ("SKIP"); //Button used for checking errors when programming - fills the puzzle with answer
  JButton gameSoundButton; //For music/mute button on game panel
  JTextField [][] displayArr = new JTextField [9][9];
  
  //Congrats panel components
  JButton anotherButton = new JButton ("YES"); //Buttons leads to the selection panel
  JButton quitButton = new JButton ("Quit");
  JLabel congratulationsLabel = new JLabel ("Congratulations!");
  JLabel anotherLabel = new JLabel ("Do you want to try another one?");
  JLabel endLabel = new JLabel ("Do you want to quit the game?");
  
  //Fail panel components
  JButton redoButton = new JButton ("YES, REDO THE GAME"); //Button leads back to the game
  JButton quitGameButton = new JButton ("QUIT");
  JLabel failLabel = new JLabel ("Oops!");
  JLabel redoLabel = new JLabel ("Do you want to continue working on it?");
  JLabel quitLabel = new JLabel ("Do you want to quit the game?");
  
  
  /***********************************************************************************************************************/
  /*********************************************The Big Constructor******************************************************/
  public Sudoku(){
    
    //Change font size and center the text
    chooseLabel.setFont(new Font("Serif", Font.PLAIN, 25));
    
    //Generate the template array (empty array)
    for (int i = 0; i < 9; i++){
      for (int j = 0; j < 9; j++){
        displayArr[i][j] = new JTextField(5);
        displayArr[i][j].setHorizontalAlignment(JTextField.CENTER); //Center the text in the JTextFields
      }//end of for loop
    }//end of for loop
    
    //Set the frame
    setTitle ("Sudoku!"); //Create a window with a title
    setSize (1000, 1000); //Set size of the window
    setResizable (true);//Allows user to change size
    
    
    //Setting panel layouts*********************************************************************************************/
    //Declare the different types of layouts
    FlowLayout layoutFlow = new FlowLayout();
    GridLayout layoutPuzzle = new GridLayout (9,9); //Layout for the 81 text fields
    GridLayout layoutSelection = new GridLayout (2,3); //Layout for the buttons on selection screen
    GridLayout layoutBeginningButton = new GridLayout(1,3); //Layout for the three buttons on the starting page
    //GridLayout layoutGameButton = new GridLayout (1,4); //Layout for cheat button on the game panel
    GridLayout layoutEndingButton = new GridLayout (2,1); //Layout for options when finished puzzle
    GridLayout layoutEnding = new GridLayout (2,1); //Layout for ending panels
    GridLayout layoutWelcome = new GridLayout (3,1); //Layout for welcome panel
    BoxLayout layoutSecondaryCongrats = new BoxLayout (secondaryCongrats,BoxLayout.Y_AXIS); //Layout for text on congrats
    BoxLayout layoutSecondaryFail = new BoxLayout (secondaryFail,BoxLayout.Y_AXIS); //Layout for text on fail
    BoxLayout layoutPuzzlePanel = new BoxLayout (puzzle,BoxLayout.Y_AXIS); //Layout for puzzle panel
    BoxLayout layoutSelectionPanel = new BoxLayout (selection,BoxLayout.Y_AXIS); //layout for selection panel
    
    //Set Layouts for different pannels
    setLayout (layoutFlow); //Sets frame layout as flow
    
    //Welcome panel
    welcome.setPreferredSize( new Dimension (800,800));
    welcome.setLayout (layoutWelcome); //Layout for welcome panel
    buttonStart.setLayout (layoutBeginningButton); //Set beginning to buttonStart panel
    
    //Info panel
    info.setPreferredSize (new Dimension (500,200));
    info.setLayout (layoutEndingButton); //Layout for the info panel
    
    //Selction panel
    buttonSelection.setPreferredSize (new Dimension (1000,500));
    buttonSelection.setLayout (layoutSelection); //Layout for button on selection
    selection.setLayout (layoutSelectionPanel); //Layout for selection
    
    //puzzle panel
    secondaryPuzzle.setLayout (layoutPuzzle); //Layout for 9-9 grid
    buttonPanel.setPreferredSize(new Dimension (500,60));
    //buttonPanel.setLayout(layoutGameButton); //Layout for showing the four buttons (SUBMIT, BONUS, SOUND, CHEAT)
    buttonPanel.setLayout(layoutBeginningButton); //Layout for showing the three buttons (SUBMIT, BONUS, SOUND)
    puzzle.setPreferredSize(new Dimension (750,750));
    puzzle.setLayout (layoutPuzzlePanel); //Layout for puzzle panel
    
    //Congrats panel
    buttonCongrats.setLayout (layoutEndingButton); //Layout for button on congrats panel
    secondaryCongrats.setLayout (layoutSecondaryCongrats); //Layout for text on congrats panel
    congrats.setLayout (layoutEnding); //Layout for congrats panel
    
    //Fail panel
    buttonFail.setLayout (layoutEndingButton); //Layout for button on fail panel
    secondaryFail.setLayout (layoutSecondaryFail); //Layout for text on fail panel
    fail.setLayout (layoutEnding); //Layout for fail panel
    
    
    //Adding components to panels***************************************************************************************/
    
    //import the logo image for the welcome panel
    logo = new ImageIcon(getClass().getResource("Sudoku_Logo.png"));
    logoLabel = new JLabel(logo);
    
    //import the music buttons for the welcome panel
    music = new ImageIcon(getClass().getResource("music.png"));
    mute = new ImageIcon(getClass().getResource("mute.png"));
    //initialize & set music image to the button
    soundButton = new JButton ();
    soundButton.setIcon(music);
    gameSoundButton= new JButton();
    gameSoundButton.setIcon (music);
    
    //import background music
    try { 
      File audioFile = new File("backgroundMusic.wav"); 
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); 
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat()); 
      clip = (Clip) AudioSystem.getLine(info); 
      clip.open(audioStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    }catch (Exception e) { 
      e.printStackTrace(); 
    }//end of catch
    
    //For buttons on starting panel
    startButton.addActionListener(this); 
    infoButton.addActionListener(this); 
    soundButton.addActionListener(this);
    buttonStart.add(startButton);
    buttonStart.add(infoButton);
    buttonStart.add(soundButton);
    
    //for Starting panel
    welcome.add(logoLabel);
    welcome.add(buttonStart);
    welcome.add(ourNameLabel);
    
    //for info panel
    infoDump.append(information);
    infoDump.setLayout(layoutFlow);
    infoDump.setLineWrap(true);
    infoDump.setWrapStyleWord(true);
    infoDump.setEditable(false);
    
    //Adding things to info panel
    startGameButton.addActionListener(this); 
    info.add(infoDump);
    info.add(startGameButton);
    
    //for Selection Pane
    sudoku1.addActionListener(this); 
    sudoku2.addActionListener(this); 
    sudoku3.addActionListener(this); 
    sudoku4.addActionListener(this); 
    sudoku5.addActionListener(this); 
    sudoku6.addActionListener(this);
    buttonSelection.add(sudoku1);
    buttonSelection.add(sudoku2);
    buttonSelection.add(sudoku3);
    buttonSelection.add(sudoku4);
    buttonSelection.add(sudoku5);
    buttonSelection.add(sudoku6);
    selection.add(buttonSelection);
    selection.add(chooseLabel);
    
    //for game panel
    for (int width = 0; width< 9; width++){
      for (int height = 0; height< 9; height++){
        secondaryPuzzle.add(displayArr[width][height]);
      }//end of for loop
    }//end of for loop
    submitButton.addActionListener(this);
    bonusButton.addActionListener(this);
    gameSoundButton.addActionListener(this);
    buttonPanel.add(submitButton);
    buttonPanel.add(bonusButton);
    buttonPanel.add(gameSoundButton);
    puzzle.add(secondaryPuzzle);
    puzzle.add(buttonPanel);
    /**cheat button used when programming - fills the puzzle with all correct answers
     cheatButton.addActionListener (this);
     buttonPanel.add(cheatButton);
     */
    
    //for congrats panel
    anotherButton.addActionListener(this); 
    quitButton.addActionListener(this); 
    buttonCongrats.add(anotherButton);
    buttonCongrats.add(quitButton);
    secondaryCongrats.add(anotherLabel);
    secondaryCongrats.add(endLabel);
    congrats.add(congratulationsLabel);
    congrats.add(buttonCongrats);
    congrats.add(secondaryCongrats);
    
    //for fail panel
    redoButton.addActionListener(this); 
    quitGameButton.addActionListener(this); 
    buttonFail.add(redoButton);
    buttonFail.add(quitGameButton);
    secondaryFail.add(redoLabel);
    secondaryFail.add(quitLabel);
    fail.add(failLabel);
    fail.add(buttonFail);
    fail.add(secondaryFail);
    
    //adding panels to the frame
    add(welcome);
    add(info);
    info.setVisible(false);
    add(selection);
    selection.setVisible(false);
    add(puzzle);
    puzzle.setVisible(false);
    add(congrats);
    congrats.setVisible(false);
    add(fail);
    fail.setVisible(false);
    //set frame to visible
    setVisible(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }//end constructor
  
  
  
  /***********************************************************************************************************************/  
  /****************************************************Different Methods**************************************************/  
  
  /**
   * @param fileName is a String representing the name of the file in the library to read
   * @return a String array containing the sudoku read from the library file
   */
  public static String[][] readIn(String fileName) throws Exception{
    //Read from a text file
    File file = new File (fileName);
    Scanner readFile = new Scanner(file);//readFile is the name of a Scanner object (reads in from file)
    
    //read into an int array first
    int [][] intArr = new int[9][9];
    //Read the specific line from File & store data to File
    for (int i = 0; i < 9; i++){
      for (int j = 0; j < 9; j++){
        intArr [i][j] = readFile.nextInt();
      }//end of for loop
    }//end of for loop
    readFile.close();
    
    //insert the integers into a String array
    String[][] sArr = new String[9][9];
    for (int i = 0; i < 9; i++){
      for (int j = 0; j < 9; j++){
        sArr[i][j] = Integer.toString(intArr[i][j]);
      }//end of for loop
    }//end of for loop
    
    return sArr;
  }//end of readIn method for Strings
  
  /**
   * @param checkArr is a JTextField [][] called for selecting one of its cells randomly
   * @return an int array containing two numbers (the wid and hei that make up the position of a cell in the 2D array)
   */
  public int[] selectRandomCell(JTextField[][] checkArr){
    //generate two random numbers to pick a position in the array
    int randomWid = (int) Math.round(Math.random()*8);
    int randomHei = (int) Math.round(Math.random()*8);
    
    //avoid choosing the cells that are already displaying a number (the quesiton)
    while(!checkArr[randomWid][randomHei].getText().trim().equals("")){
      randomWid = (int) Math.round(Math.random()*8);
      randomHei = (int) Math.round(Math.random()*8);
    }//end of while loop
    
    //store the two random numbers into an array for future use (generating the special cell)
    int[] randomCell = {randomWid, randomHei};
    return randomCell;
  }//end of selectRandomCell method
  
  /**
   * @param bonusCell is the specialCell[], which stores the position of the bonus cell that's randomly selected
   * @param checkArr is a JTextField [][] called for selecting one of its cells randomly
   * the method uses selectRandomCell method to generate a random cell from the array by changing the colour of it
   */
  public void setBonusCell(int[] bonusCell, JTextField[][] checkArr){
    //changes the background colour of the random cell so the player knows it is the bonus cell
    checkArr[bonusCell[0]][bonusCell[1]].setBackground(specialBackground);//set the bonus cell to a special background colour
  }//end of setBonusCell method
  
  /**
   * @param bonusCell is an int[] that contains two numbers generated randomly
   * @param checkArr is a JTextField [][] called for selecting one of its cells randomly
   * @param ansArr is the answerArray (String[][]) that is read from file, used to compare answers
   * the method checks to see if the player input in the special bonus cell is correct, and if correct, the method provides answer to three more random cells
   */
  public void checkBonusCell(int[] bonusCell, JTextField[][] checkArr, String[][] ansArr){
    //compare the number in the bonus cell with the corresponding position in the answerArr
    //if correct, randomly select three other unfilled cells and give out the answer
    if ((checkArr[bonusCell[0]][bonusCell[1]].getText().equals(ansArr[bonusCell[0]][bonusCell[1]]))){
      System.out.println("Bonus Cell Correct");
      for (int i = 0; i < 3; i++){ 
        int[] temp = new int [2];//generate new pair of indexes
        temp = selectRandomCell(checkArr);
        checkArr[temp[0]][temp[1]].setBackground(specialBackground);//set the bonus cell to a special background colour
        checkArr[temp[0]][temp[1]].setText(ansArr[temp[0]][temp[1]]);//display the text in the cell
      }//end of for loop
    }//end of if statement
    else{
      System.out.println("Bonus Cell Incorrect");
    }//end of else statement
  }//end of checkBonusCells method
  
  /**
   * @param sArr is a String [][] that stores the template read in from library file
   * sets the text in the sArr to the displayArr to be printed on screen
   */
  public void display(String[][]sArr){
    for (int i = 0; i < 9; i++){
      for (int j = 0; j < 9; j++){
        if (sArr[i][j].equals("0")){
          displayArr[i][j].setText("");
          displayArr[i][j].setEditable(true);
        }//end of if statement
        else{
          displayArr[i][j].setText(sArr[i][j]);
          displayArr[i][j].setEditable(false);
        }//end of else statement
      }//end of for loop
    }//end of for loop
  }//end of display method
  
  
  
  /***********************************************************************************************************************/
  /*****************************************************ActionPerformed***************************************************/
  public void actionPerformed(ActionEvent event){  
    String command = event.getActionCommand();
    //Playing music
    if(event.getSource () == soundButton || event.getSource () == gameSoundButton){
      System.out.println("'MUSIC' button pressed");
      musicCount++;
      if (musicCount % 2 == 0){
        soundButton.setIcon(music);
        gameSoundButton.setIcon(music);
        //music(0);
        clip.start();
      }//end of if statement
      else{
        soundButton.setIcon(mute);
        gameSoundButton.setIcon(mute);
        //music(musicCount);
        clip.stop();
      }//end of else statement
    }//end of if statement
    
    //Going to Info Screen
    if (command.equals("INFO")){
      System.out.println("'INFO' button pressed");
      welcome.setVisible(false);
      info.setVisible(true);
    }//end of if statement
    
    //Going to Selection Screen
    if (command.equals("START") || command.equals("YES")){
      System.out.println("'Start' button pressed");
      for(int z = 0; z<9;z++)
      {
        for (int y= 0; y<9;y++)
        {
          displayArr[z][y].setBackground(regularBackground);
        }//end for loop
      }//end for loop
      welcome.setVisible(false);
      info.setVisible(false);
      congrats.setVisible(false);
      selection.setVisible(true);
      
    }//end of if statement
    
    //Selecting Different Games
    if (command.equals("Puzzle 1")){
      try{
        templateArr = readIn("Sudoku 1.txt");
        answerArr = readIn("Sudoku 1 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell,displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 1.txt' or 'Sudoku 1 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    else if (command.equals("Puzzle 2")){
      try{
        templateArr = readIn("Sudoku 2.txt");
        answerArr = readIn("Sudoku 2 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell,displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 2.txt' or 'Sudoku 2 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    else if (command.equals("Puzzle 3")){
      try{
        templateArr = readIn("Sudoku 3.txt");
        answerArr = readIn("Sudoku 3 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell,displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 3.txt' or 'Sudoku 3 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    else if (command.equals("Puzzle 4")){
      try{
        templateArr = readIn("Sudoku 4.txt");
        answerArr = readIn("Sudoku 4 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell,displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 4.txt' or 'Sudoku 4 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    else if (command.equals("Puzzle 5")){
      try{
        templateArr = readIn("Sudoku 5.txt");
        answerArr = readIn("Sudoku 5 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell, displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 5.txt' or 'Sudoku 5 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    else if (command.equals("Puzzle 6")){
      try{
        templateArr = readIn("Sudoku 6.txt");
        answerArr = readIn("Sudoku 6 Answer.txt");
        display(templateArr);
        specialCell = selectRandomCell(displayArr); //generate two random numbers for the indexes for the 2D array
        setBonusCell(specialCell, displayArr);
        selection.setVisible(false);
        puzzle.setVisible(true);
      }catch(Exception e){ 
        System.out.println("Can't read from file 'Sudoku 6.txt' or 'Sudoku 6 Answer.txt'");
      }//end of catch
    }//end of if statement
    
    //Checking Bonus Cell
    if (command.equals("BONUS")){
      checkBonusCell(specialCell,displayArr,answerArr);
      bonusButton.setEnabled(false);
    }//end of if statement
    
    //Submitting the Whole Puzzle
    if (command.equals("SUBMIT")){
      //System.out.println(templateArr[1][1].getText());//testing (contents in the textfield not showing in GUI)
      puzzle.setVisible(false);
      //check each position in the array by comparing the template array to the answer array
      System.out.println("Start checking input");
      int count = 0;
      for (int wid = 0; wid < 9; wid++){
        for (int hei = 0; hei < 9; hei++){
          if (displayArr[wid][hei].getText().trim().equals(answerArr[wid][hei])){
            count++;
            displayArr[wid][hei].setBackground(regularBackground);
          }//end of if statement
          else{
            displayArr[wid][hei].setBackground(falseBackground);
          }//end of else statement
        }//end of for loop for hei
      }//end of for loop for wid
      
      //Setting Visible Congrats/Fail Screen
      if (count == 81){
        congrats.setVisible(true);
      }//end of if statement
      else{
        fail.setVisible(true);
      }//end of else statement
    }//end of if statement
    
    //Continue Working on the Game (from Fail Screen)
    if (command.equals("YES, REDO THE GAME")){
      System.out.println("'REDO-GAME' button pressed");
      fail.setVisible(false);
      puzzle.setVisible(true);
    }//end of if statement
    
    //Quit the Game
    if (command.equals("QUIT") || command.equals("Quit")) {  
      clip.stop();
      this.dispose();  
    }//end of if statement 
    
    /**
     //Cheat button - used when programming, generates all correct answer
     if (command.equals("SKIP")){
     for(int i = 0; i<9; i++){
     for(int j = 0; j<9; j++){
     displayArr[i][j].setText(answerArr[i][j]);
     }//end of for loop
     }//end of for loop
     }//end of if statement
     */ 
    
  }//end of ActionPerformed
  
  
  
  /***********************************************************************************************************************/  
  /******************************************************** MAIN *********************************************************/
  public static void main(String[] args) {
    new Sudoku();  // Start the GUI 
  }//end of main 
}//end of class