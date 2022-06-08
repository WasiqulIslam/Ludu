//7:44 PM 10/10/2004 u 10:25 AM 10/12/2004 u11:37 PM 10/12/2004 u 12:01 PM 10/16/2004 u 2:01 PM 10/23/2004 step(1000 permutations solved): 7:17 PM 10/23/2004 u 4:26 PM 10/24/2004 u 10:16 PM 10/24/2004 u 7:43 AM 10/25/2004 debug 3:54 PM 10/25/2004 u 11:37 AM 11/5/2004 u 11:34 PM 11/5/2004 u 11:04 AM 11/7/2004 u 8:45 PM 11/8/2004 u 9:26 PM 11/8/2004 u 4:28 PM 11/9/2004 u 8:01 PM 11/13/2004 bugRemovedAndImproved 9:36 PM 11/13/2004 u 9:37 PM 11/13/2004, u 1:42 PM 12/26/2004 u 2:06 PM 12/26/2004 u 3:33 PM 12/26/2004 u 8:01 PM 12/26/2004 u 12:46 AM 12/28/2004 u ( 12:28 AM 12/31/2004 FRI ) u 9:19 AM 31-Dec-2005 FRI u 3:40 PM 31-Dec-2004 FRI
//last updated 3:40 PM 31-Dec-2004 FRI
//version 1.2.3
//u 9:34 PM 9/12/2005
//buf solved 9:52 PM 9/12/2005
//version 2.0 9:52 PM 9/12/2005
//u 8:38 AM 9/13/2005
                 
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

public class Ludu extends JWindow implements ActionListener, KeyListener, java.io.Serializable
{
   private javax.swing.Timer positionChanger, refresher;
   private int pointerDisplacement = -10;
   private int pointerIncrement = 1;
   private boolean debugStatus = false;
   private boolean isDebugStatusChangable = false;  //if true debugStatus can be changed by fressing 'F1' function key
   private String colorStrings[] = { "R", "B", "G", "Y" };
   private int rankLength = 0;
   private int rankID[] = new int[ 4 ];  //ranked player's id
   private int count = 0, count1 = 0;   //count for selectNextFunction, count1 for debugging
   private final int countLimit = 200;   //for debugging
   private int specialIndex = 0;  //used in both programming and debugging
   private final int positionDrawSequence[] = { 24, 25, 26, 23, 63, 27, 22, 64, 28, 92, 21, 65, 29, 88, 20, 66, 30, 19, 67, 31, 13, 14, 15, 16, 17, 18, 96, 32, 33, 34, 35, 36, 37, 12, 68, 69, 70, 71, 72, 97, 95, 62, 61, 60, 59, 58, 38, 11, 10, 9, 8, 7, 6, 94, 44, 43, 42, 41, 40, 39, 5, 57, 45, 4, 56, 46, 3, 55, 47, 80, 2, 54, 48, 84, 1, 53, 49, 52, 51, 50 };
   private Board board;
   private MPanel boardPanel;
   private GutiGraphics pieceImages[] = new GutiGraphics[ 4 ];
   private SelectionPointerGraphics selectionPointer = new SelectionPointerGraphics();
   private MButton quitButton, keyButton, playerButton, startButton, helpButton;
   private DiePanel dies[] = new DiePanel[ 3 ];
   private JTextArea stateArea, rankArea;
   private boolean boardGraphicsChanged = true;
   private int currentPlayer = Constants.RED;
   private boolean isStarted = false;
   private BufferedImage boardImageOriginal, boardImageCurrent;
   private int currentSelection = 0;
   private Player players[] = new Player[ 4 ];
   private Player backupOfPlayers[] = new Player[ 4 ];
   private int currentPoints[] = new int[ 3 ];
   private int currentPointsLength = 0;
   private boolean isBoardSelected = false;
   private int selectedPositionIndex = -1;   //at which position(predefined) the selection pointer should currently drawn
   private int selectionType = -1;   //selection type may be Constants.DOUBLE_SELECTION or Constants.SINGLE_SELECTION
   private int currentSelectionDieIndex = -1;   //three dies may have valid points such as 6, 6, and 3; So currently which die point move is selecting is indicated by this variable, possible valid value : 0, 1 and 2 only, i,e, die0, die1 and die2
   private int currentPermutationSelection = 0;
   private int lastSelection[] = new int[ 3 ];   //stores the current players choices of selection sequencially
   private int numberOfPlayers = 4;   
   private int backupOfNumberOfPlayers;
   private int step = -1, part = -1;   //step eg. -> Constants.GENETATE_DIE, Constants.GUTI_SELECT_MOVE, part eg. -> Constants.PART_1, Constants.PART_2, Constants.PART_3;  
   private long seed;   //seed is used to generate die numbers. Die numbers are generated from the difference between key pressed and released counted in milli seconds
   private boolean path[] = new boolean[ 1000 ];
   private boolean tmp[] = new boolean[ 1000 ];
   private boolean movePathFound = true;
   private int currentRank = 1;
   private boolean busy = false;
   private KillInfo killInfo[] = new KillInfo[ 3 ];
   private int killInfoLength = 0;
   Sound sound;
   public Ludu( JFrame owner )                     //tag
   {
      super( owner );
      System.err.println( "Please Wait..." );
      Container container = this;
      container.setLayout( new BorderLayout() );
      board = new Board();
      for( int i = 0; i < 4; i ++ )
      {
         pieceImages[ i ] = new GutiGraphics();
         pieceImages[ i ].setType( i );
         pieceImages[ i ].build();
      }
      boardImageOriginal = board.getBoardImage();
      boardImageCurrent = new BufferedImage( 600, 600, BufferedImage.TYPE_INT_RGB );
      WritableRaster wr = boardImageOriginal.copyData( null );
      boardImageCurrent.setData( wr );
      boardPanel = new MPanel( 600, 600 )
      {
         public void update( Graphics g )                     //tag
         {
            paintComponent( g );
         }
         public void paintComponent( Graphics g )                     //tag
         {
            g.setClip( 0, 0, 600, 600 );
            if( boardGraphicsChanged )
            {
               //----------------- draw BuffImg
               boardImageCurrent = new BufferedImage( 600, 600, BufferedImage.TYPE_INT_RGB );
               WritableRaster wr = boardImageOriginal.copyData( null );
               boardImageCurrent.setData( wr );
               //----------------- draw all pieces
               Graphics gbi = boardImageCurrent.getGraphics();
               GutiContainer c;
               Position p;
               int t[] = new int[16];
               int axe[] = new int[16];
               int yee[] = new int[16];
               int inc = 0;
               for( int k = 0,i = -1; k < positionDrawSequence.length; k++ )
               {
                  i = positionDrawSequence[ k ];
                  c = board.getContainer( i );
                  p = board.getPosition( i );
                  if( c.length() > 0 )
                  {
                     Guti gt;
                     int dec = 0;;
                     for( int j = 0; j < c.length(); j++ )
                     {
                        gt = c.getPiece( j );
                        t[ inc ] = gt.getType();
                        axe[ inc ] = p.getX();
                        yee[ inc ] = ( p.getY() + dec );
                        inc++;
                        dec -= 5;
                     }
                  }
               }
               Graphics2D g2bi = ( Graphics2D ) gbi; 
               g2bi.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
               Position pos;
               for( int k = 0, i = -1; k < positionDrawSequence.length; k++ )
               {
                  i = positionDrawSequence[ k ];
                  pos = board.getPosition( i );
                  if( pos.isBlocked() )
                  {
                     g2bi.setColor( pos.getBlockColor() );
                     g2bi.fillRoundRect( pos.getX() + 1, pos.getY() + 1, 39, 39, 25, 25 );
                     g2bi.setColor( Color.black );
                  }
               }
               for( int i = 0; i < inc && i < 16; i++ )
               {
                  pieceImages[ t[ i ] ].drawGuti( gbi, axe[ i ], yee[ i ] );
               }
               boardGraphicsChanged = false;
            }
            g.drawImage( boardImageCurrent, 0, 0, Ludu.this );
            if( isBoardSelected )
            {
               Position p;
               //---------------------- draw selection pointer
               if( selectedPositionIndex >= 1 && selectedPositionIndex <= 99 )
               {
                  p = board.getPosition( selectedPositionIndex );
                  selectionPointer.drawSelection( g, p.getX(), p.getY(), selectionType, pointerDisplacement );
               }
               else
               {
                  error( "Invalid Selection" );
               }
            }
         }
      };
      container.add( boardPanel, BorderLayout.CENTER );
      quitButton = new MButton( "Quit" );
      quitButton.setButtonWidth( 150 );
      helpButton = new MButton( "Help/About" );
      helpButton.setButtonWidth( 150 );
      keyButton = new MButton( "Show Keys" );
      keyButton.setButtonWidth( 150 );
      playerButton = new MButton( "Players and Teams" );
      playerButton.setButtonWidth( 150 );
      startButton = new MButton( "Start" );
      startButton.setButtonWidth( 150 );
      MPanel rhs = new MPanel( 200, 600 );
      rhs.setBackground( new Color( 179, 117, 191 ) );
      MPanel rhsup = new MPanel( 200, 300 );
      MPanel rhsdown = new MPanel( 200, 300 );
      rhsup.setBackground( new Color( 179, 117, 191 ) );
      rhsdown.setBackground( new Color( 179, 117, 191 ) );
      rhs.setLayout( new GridLayout( 2, 1 ) );
      rhsup.setLayout( new FlowLayout() );
      rhsdown.setLayout( new GridLayout( 2, 1 ) );
      quitButton.addActionListener( this );
      helpButton.addActionListener( this );
      keyButton.addActionListener( this );
      playerButton.addActionListener( this );
      startButton.addActionListener( this );
      rhsup.add( quitButton );
      rhsup.add( helpButton );
      rhsup.add( keyButton );
      rhsup.add( playerButton );
      rhsup.add( startButton );
      rhs.add( rhsup );
      MPanel p1 = new MPanel( 200, 150 );
      MPanel p2 = new MPanel( 200, 150 );
      MPanel p1_1 = new MPanel( 200, 75 );
      MPanel p1_2 = new MPanel( 200, 75 );
      for( int i = 0; i < 3; i ++ )
      {
         dies[ i ] = new DiePanel();
      }
      stateArea = new JTextArea();
      stateArea.setEnabled( false );
      stateArea.setDisabledTextColor( Color.blue );
      rankArea = new JTextArea();
      rankArea.setEnabled( false );
      rankArea.setDisabledTextColor( Color.red );
      p1.setLayout( new GridLayout( 2, 1 ) );
      p1_1.setLayout( new BorderLayout() );
      p1_2.setLayout( new FlowLayout() );
      p1_2.add( dies[ 0 ] );
      p1_2.add( dies[ 1 ] );
      p1_2.add( dies[ 2 ] );
      p1_1.add( new JScrollPane( stateArea ) );
      p1.add( p1_1 );
      p1.add( p1_2 );
      rhsdown.add( p1 );
      p2.setLayout( new BorderLayout() );
      p2.add( new JScrollPane( rankArea ) );
      rhsdown.add( p2 );
      rhs.add( rhsdown );
      container.add( rhs, BorderLayout.EAST );

      sound = new Sound();

      Player.numberOfPlayers = 4;
      numberOfPlayers = 4;
      String pn[] = { "PLAYER 1", "PLAYER 2", "PLAYER 3", "PLAYER 4" };
      String tn[] = { "TEAM 1", "TEAM 2", "TEAM 3", "TEAM 4" };
      for( int i = 0; i <= 3; i ++ )
      {
         players[ i ] = new Player( i, i, pn[ i ], tn[ i ] );
      }
      players[ 0 ].setColor( Constants.RED );
      players[ 1 ].setColor( Constants.BLUE );
      players[ 2 ].setColor( Constants.GREEN );
      players[ 3 ].setColor( Constants.YELLOW );
      resetAll();
      setSize( 800, 600 );

      positionChanger = new javax.swing.Timer( 100, this ); //100ms;changes position 1 pixels every 100 milli second
      refresher = new javax.swing.Timer( 25, this );    //25ms; refresh board 40 times a second
      positionChanger.start();
      refresher.start();
      addKeyListener( this );
      stateArea.setText( "Waiting for a game to be started..." );
      System.err.println( "Program is ready..." );
   }
   public void actionPerformed( ActionEvent event )                     //tag
   {
      if( event.getSource() == quitButton )
      {
         exiting();
      }
      else if( event.getSource() == keyButton )                     //tag
      {
         sound.playSound( Constants.CLICK_SOUND );
         String d = "Player              Primary Key    Secondary Key\n" + 
                         "1                        Q                         W\n"+
                         "2                        O                         P\n"+
                         "3                        X                         C\n"+
                         "4                        N                         M";
         JOptionPane.showMessageDialog( this, d, "Keys (fixed)", JOptionPane.INFORMATION_MESSAGE );
      }
      else if( event.getSource() == playerButton )                     //tag
      {
         if( isStarted )
         {
            sound.playSound( Constants.ERROR_SOUND );
            int a = JOptionPane.showConfirmDialog( this, "Do you really want to quit current game\nand change player settings?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
            if( a != JOptionPane.YES_OPTION )
            {
               return;
            }
         }
         else
            sound.playSound( Constants.CLICK_SOUND );
         Player bkup[] = { new Player( players[ 0 ] ), new Player( players[ 1 ] ), new Player( players[ 2 ] ), new Player( players[ 3 ] ) };
         int nOPbkup = numberOfPlayers;

         JTextField pn[] = new JTextField[ 4 ];
         JTextField tn[] = new JTextField[ 4 ], playerNumberField;
         JLabel lbl[] = new JLabel[ 4 ], playerHeadingLabel, teamHeadingLabel, playerNumberLabel;
         playerHeadingLabel = new JLabel( "Player Name" );
         teamHeadingLabel = new JLabel( "TeamName" );
         playerNumberLabel = new JLabel( "Number of players:" );
         playerNumberField = new JTextField( 10 );
         for( int i = 0; i <= 3; i ++ )
         {
            pn[ i ] = new JTextField( 10 );
            tn[ i ] = new JTextField( 10 );
         }
         JPanel main = new JPanel();
         JPanel upper = new JPanel(), lower = new JPanel() ;
         main.setLayout( new BorderLayout() );
         upper.setLayout( new FlowLayout( FlowLayout.CENTER ) );
         upper.add( playerNumberLabel );
         playerNumberField.setText( "" + numberOfPlayers );
         for( int i = 0; i <= 3; i++ )
         {
            pn[ i ].setText( players[ i ].getPlayerName() );
            tn[ i ].setText( players[ i ].getTeamName() );
         }
         upper.add( playerNumberField );
         main.add( upper, BorderLayout.NORTH );
         lower.setLayout( new GridLayout( 5, 2 ) );
         JPanel tmp;
         tmp = new JPanel();
         tmp.setLayout( new FlowLayout( FlowLayout.LEFT ) );
         tmp.add( playerHeadingLabel );
         lower.add( tmp );
         tmp = new JPanel();
         tmp.setLayout( new FlowLayout( FlowLayout.LEFT ) );
         tmp.add( teamHeadingLabel );
         lower.add( tmp );
         for( int i = 0; i <= 3; i ++ )
         {
            tmp = new JPanel();
            tmp.setLayout( new FlowLayout( FlowLayout.LEFT ) );
            tmp.add( pn[ i ] );
            lower.add( tmp );
            tmp = new JPanel();
            tmp.setLayout( new FlowLayout( FlowLayout.LEFT ) );
            tmp.add( tn[ i ] );
            lower.add( tmp );
         }
         main.add( lower, BorderLayout.CENTER );
         int a = JOptionPane.showConfirmDialog( this, main, "Player and Team Settings", JOptionPane.OK_CANCEL_OPTION );
         if( a != JOptionPane.OK_OPTION )
            return;
         try
         {
            a = Integer.parseInt( playerNumberField.getText() );
         }
         catch( NumberFormatException nfe )
         {
            JOptionPane.showMessageDialog( this, "Invalid player number.", "Error", JOptionPane.ERROR_MESSAGE );
            return;
         }
         if( !( a >= 2 && a <= 4 ) )
         {
            JOptionPane.showMessageDialog( this, "At least 2 players are required and at most 4 can play.", "Error", JOptionPane.ERROR_MESSAGE );   
            return;
         }

         String pns[] = { "PLAYER 1", "PLAYER 2", "PLAYER 3", "PLAYER 4" };
         String tns[] = { "TEAM 1", "TEAM 2", "TEAM 3", "TEAM 4" };
         for( int i = 0; i <= 3; i ++ )
         {
            players[ i ] = new Player( i, i, pns[ i ], tns[ i ] );
         }

         numberOfPlayers = a;
         for( int i = 0; i < numberOfPlayers; i++ )
         {
            String t = ( pn[ i ].getText().trim() ).toUpperCase();
            if( !t.equals( "" ) )
               players[ i ].setPlayerName( t );
         }
         for( int i = 0; i < numberOfPlayers; i++ )
         {
            String t = ( tn[ i ].getText().trim() ).toUpperCase();
            if( !t.equals( "" ) )
               players[ i ].setTeamName( t );
         }
         a = 0;
         boolean flag[] = { true, true, true, true };
         for( int i = 0; i < numberOfPlayers; i++ )
         {
            if( flag[ i ] )
            {
               players[ i ].setTeamID( a );
               flag[ i ] = false;
               for( int j = i + 1; j < numberOfPlayers; j++ )
               {
                  if( players[ i ].getTeamName().equals( players[ j ].getTeamName() ) )
                  {
                     players[ j ].setTeamID( a );
                     flag[ j ] = false;
                  }
               }
               a++;
            }
         }
         if( a < 2 )  //At least two team must be played with
         {
            //restoring backup
            players[ 0 ] = bkup[ 0 ];
            players[ 1 ] = bkup[ 1 ];
            players[ 2 ] = bkup[ 2 ];
            players[ 3 ] = bkup[ 3 ];
            numberOfPlayers = nOPbkup;
            JOptionPane.showMessageDialog( this, "At least two teams are required.", "Error", JOptionPane.ERROR_MESSAGE );
            return;
         }

         //Assigning player color

         Player.numberOfPlayers = numberOfPlayers;

         if( numberOfPlayers == 2 )
         {
            players[ 0 ].setColor( Constants.RED );
            players[ 1 ].setColor( Constants.GREEN );
         }
         else if( numberOfPlayers == 3 )
         {
            players[ 0 ].setColor( Constants.RED );
            players[ 1 ].setColor( Constants.BLUE );
            players[ 2 ].setColor( Constants.GREEN );
         }
         else if( numberOfPlayers == 4 )
         {
            players[ 0 ].setColor( Constants.RED );
            players[ 1 ].setColor( Constants.BLUE );
            players[ 2 ].setColor( Constants.GREEN );
            players[ 3 ].setColor( Constants.YELLOW );
         }
         //initializing player Guti positions
         int pos[] = { 80, 84, 88, 92 };
         for( int i = 0; i < numberOfPlayers; i++ )
         {
            a = players[ i ].getColor();
            players[ i ].setGutiCount( 4 );
            for( int j = 0; j <= 3; j++ )
            {
               players[ i ].setLocation( j, pos[ a ] );
               players[ i ].setDoubling( j, false );
            }
         }
         resetAll();
      }
      else if( event.getSource() == startButton )                     //tag
      {
         if( isStarted )
         {
            sound.playSound( Constants.ERROR_SOUND );
            int a = JOptionPane.showConfirmDialog( this, "Do you really want to quit current game\nand Start a new Game?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE );
            if( a != JOptionPane.YES_OPTION )
            {
               return;
            }
         }
         else
         {
            sound.playSound( Constants.CLICK_SOUND );
         }
         start();
      }
      else if( event.getSource() == helpButton )                     //tag
      {
         sound.playSound( Constants.HELP_SOUND );
         JTextArea ha = new JTextArea( 20, 40 );
         ha.setLineWrap( true );
         ha.setWrapStyleWord( true );
         ha.setText( "Ludu version 2.0( 8:37 AM 13-Sep-2005 TUE )\nProgrammed by Wasiqul Islam(e-mail address: \'wasiqul_islam@yahoo.com\')[ phone : 031-650052( Bangladesh ) ],\n2nd Batch, Computer Science & Engineering Department,\nUniversity of Science & Technology Chittagong(USTC),\nFoyslake, Chittagong, Bangladesh." +
            "\n\nThis program is made to play 'Ludu' game played in Bangladesh.\nI suggest that only who knows Ludu should play this and not including any detailed game playing information but one can learn this game easily by just playing it.\n\nThe button \'Quit\'(at the upper right corner) will always be available to exit the game at any time." +
            "\nAt most 4 players can play this game at a time. The button \'Show keys\' display the keys predefined for each player.\nYou can change player numbers and names etc. with the button named \'Player Settings\'. This is a team based game and there may be at least two teams (and players) and at most four. Game winning is based on teams not on players.\nYou have to click \'Start\' to start a new game.\n\nPlaying the game is very simple.\n" +
            "Each player has both a primary and a secondary key. A help display will automatically show current player name and his/her keys. Most of the job of a player is done by the primary key.\nWith the secondary key a player decides which pieces he wants to move on and can cycle through each valid or movable pieces.\nThe lower right white display area is where the player winning status or rank is displayed. It is displayed in red color whereas normal help display is displayed in blue." +
            "\nOne piece as well as two pieces can be moved combinedly. This I call (later one) double move.\nWhen secondary key cycles through movable pieces it selects the piece with an arrow-like white pointer.\nWhen the arrow pointer changes into black color it indicates a double move. A double moved group can't be moved further singly[ except for the safe( star indicated ) locations ] and no opponent team pieces can pass over them with one move.\nOnly opponent double grouped pieces are able to expel then or kill them." +
            "\nA double grouped piece can't block an star indicated safe area and only blocks opponent teams but not players in the same team.\n\n\n\n[ N.B. If you can't understand this help messages, try playing the game first with simple moves(click the \'Start\' button and use only the 'primary key') and read this message again later. ]" );
         ha.setSelectionStart( 0 );
         ha.setSelectionEnd( 0 );
         ha.setEditable( false );
         JOptionPane.showMessageDialog( this, new JScrollPane( ha ), "Help/About", JOptionPane.INFORMATION_MESSAGE );
         sound.playSound( Constants.HELP_SOUND );
      }
      else if( event.getSource() == refresher )     //tag
      {
         boardPanel.repaint();
      }
      else if( event.getSource() == positionChanger )      //tag
      {
         int a = pointerDisplacement;
         a += pointerIncrement;
         if( a <= -18 || a >= -3 )
         {
            pointerIncrement = - pointerIncrement;
         }
         pointerDisplacement = a;
      }
   }
   public void keyPressed( KeyEvent event )                     //tag
   {
      try
      {
         if( busy )
            return;
         char key = event.getKeyChar();
         if( isStarted && step == Constants.GENERATE_DIE )
         {
            if( currentPlayer == 0 && ( key == 'Q' || key == 'q' ) )
            {
               sound.loopSound( Constants.SHAKE_SOUND );
               seed = System.currentTimeMillis();
            }
            else if( currentPlayer == 1 && ( key == 'O' || key == 'o' ) )
            {
               sound.loopSound( Constants.SHAKE_SOUND );
               seed = System.currentTimeMillis();
            }
            else if( currentPlayer == 2 && ( key == 'X' || key == 'x' ) )
            {
               sound.loopSound( Constants.SHAKE_SOUND );
               seed = System.currentTimeMillis();
            }
            else if( currentPlayer == 3 && ( key == 'N' || key == 'n' ) )
            {
               sound.loopSound( Constants.SHAKE_SOUND );
               seed = System.currentTimeMillis();
            }
         }
      }
      catch( Throwable t )
      {
         t.printStackTrace();
      }
   }
   public void keyReleased( KeyEvent event )                     //tag
   {
      try
      {
         if( busy )
            return;
         char key = event.getKeyChar();
         if( isDebugStatusChangable == true && event.getKeyCode() == KeyEvent.VK_F1 )
         {
            debugStatus = ! debugStatus;
            JOptionPane.showMessageDialog( this, "Debug Status Changed and becomes " + debugStatus );
            return;
         }
         if( isStarted && step == Constants.GENERATE_DIE )
         {
            if( currentPlayer == 0 && ( key == 'Q' || key == 'q' ) )
            {
               busy = true;
               sound.playSound( Constants.LAUNCH_SOUND );
               generatePoint();
               busy = false;
            }
            else if( currentPlayer == 1 && ( key == 'O' || key == 'o' ) )
            {
               busy = true;
               sound.playSound( Constants.LAUNCH_SOUND );
               generatePoint();
               busy = false;
            }
            else if( currentPlayer == 2 && ( key == 'X' || key == 'x' ) )
            {
               busy = true;
               sound.playSound( Constants.LAUNCH_SOUND );
               generatePoint();
               busy = false;
            }
            else if( currentPlayer == 3 && ( key == 'N' || key == 'n' ) )
            {
               busy = true;
               sound.playSound( Constants.LAUNCH_SOUND );
               generatePoint();
               busy = false;
            }
         }
         else if( isStarted && step == Constants.GUTI_SELECT_MOVE )
         {
            if( currentPlayer == 0 && ( key == 'Q' || key == 'q' ) )
            {
               busy = true;
               sound.playSound( Constants.MOVE_SOUND );
               moveNext();
               busy = false;
            }
            else if( currentPlayer == 1 && ( key == 'O' || key == 'o' ) )
            {
               busy = true;
               sound.playSound( Constants.MOVE_SOUND );
               moveNext();
               busy = false;
            }
            else if( currentPlayer == 2 && ( key == 'X' || key == 'x' ) )
            {
               busy = true;
               sound.playSound( Constants.MOVE_SOUND );
               moveNext();
               busy = false;
            }
            else if( currentPlayer == 3 && ( key == 'N' || key == 'n' ) )
            {
               busy = true;
               sound.playSound( Constants.MOVE_SOUND );
               moveNext();
               busy = false;
            }
            else if( currentPlayer == 0 && ( key == 'W' || key == 'w' ) )
            {
               busy = true;
               sound.playSound( Constants.SELECT_SOUND );
               selectNext();
               busy = false;
            }
            else if( currentPlayer == 1 && ( key == 'P' || key == 'p' ) )
            {
               busy = true;
               sound.playSound( Constants.SELECT_SOUND );
               selectNext();
               busy = false;
            }
            else if( currentPlayer == 2 && ( key == 'C' || key == 'c' ) )
            {
               busy = true;
               sound.playSound( Constants.SELECT_SOUND );
               selectNext();
               busy = false;
            }
            else if( currentPlayer == 3 && ( key == 'M' || key == 'm' ) )
            {
               busy = true;
               sound.playSound( Constants.SELECT_SOUND );
               selectNext();
               busy = false;
            }
         }
      }
      catch( Throwable t )
      {
         t.printStackTrace();
      }
   }
   public void keyTyped( KeyEvent event )                     //tag
   {
      try
      {
         if( busy )
            return;
         char c = event.getKeyChar();
         if( c == KeyEvent.VK_ESCAPE )
         {
            exiting();
         }
      }
      catch( Throwable t )
      {
         t.printStackTrace();
      }
   }
   private void exiting()                     //tag
   {
      sound.playSound( Constants.ERROR_SOUND );
      int a = JOptionPane.showConfirmDialog( this, "Really Want to quit?", "Confirmation", JOptionPane.YES_NO_OPTION );
      if( a == JOptionPane.YES_OPTION )
      {
         System.err.println( "Exiting..." );
         System.exit( 0 );
      }
   }
   private void resetAll()                     //tag
   {
      isStarted = false;
      rankLength = 0;
      int i, k;
      for( k = 0; k < positionDrawSequence.length ; k++ )
      {
         i = positionDrawSequence[ k ];
         board.getContainer( i ).reset();
         board.getPosition( i ).unblock();
      }
      clearPlayerInfo();
      clearPlayerRank();
      stateArea.setText( "Waiting for a game to be started..." );
      dies[ 0 ].clear();
      dies[ 1 ].clear();
      dies[ 2 ].clear();
      currentSelection = 0;
      for( i = 0; i < numberOfPlayers; i++ )
      {
         players[ i ].setGutiCount( 0 );
         players[ i ].setRetired( false );
         players[ i ].resetMove();
         for( int ii = 0; ii <= 3; ii++ )   //each players four Guti must not be doubling
         {
            players[ i ].releaseDoubling( ii );
         }
      }
      currentRank = 1;
      isBoardSelected = false;
      currentSelectionDieIndex = 0;
      currentPermutationSelection = 0;
      lastSelection[ 0 ] = 0;
      lastSelection[ 1 ] = 0;
      lastSelection[ 2 ] = 0;
      movePathFound = true;
      boardGraphicsChanged = true;
      killInfoLength = 0;
      refreshBoard();
   }
   private boolean checkIfGameOver()                     //tag
   {
      //If there are 2 teams of which all players of a team are retired(in game: won), the game is over
      int teams = 0;
      for( int i = 0; i <= 3; i ++ )
      {
         if( players[ i ].getTeamID() > teams )
         {
            teams = players[ i ].getTeamID();
         }
      }
      int notRetiredFlag = 0;
      label1: for( int i = 0; i <= teams; i++ )
      {
         for( int j = 0; j < numberOfPlayers; j++ )
         {
            if( players[ j ].getTeamID() == i && !( players[ j ].isRetired() ) )
            {
               notRetiredFlag++;
               continue label1;
            }
         }
      }
      if( notRetiredFlag <= 1  )
      {
         String tmp = "Game Over";
         String w = "";   //winner team
         boolean teamFlag[] = new boolean[ teams + 1 ];
         int i;
         for( i = 0; i <= teams; i++ )
         {
            teamFlag[ i ] = true;
         }
         for( i = 0; i < numberOfPlayers; i++ )
         {
            if( !players[ i ].isRetired() )
            {
               teamFlag[ players[ i ].getTeamID() ] = false;
            }
         }
         int a = 0;
         for( i = 0; i < rankLength; i++ )
         {
            if( teamFlag[ players[ rankID[ i ] ].getTeamID() ] == true )
            {
               a = i;
               break;
            }
         }
         w = players[ rankID[ a ] ].getTeamName();
         tmp += "\nWinner Team: " + w;
         tmp += "\nWaiting for a game to be restarted...";
         stateArea.setText( tmp );
         isStarted = false;
         sound.playSound( Constants.GAME_OVER_SOUND );
         return true;
      }
      else
      {
         return false;
      }
   }
   private void start()                     //tag
   {
      resetAll();
      isStarted = true;
      int a;
      int pos[] = { 80, 84, 88, 92 };
      for( int i = 0; i < numberOfPlayers; i++ )
      {
         a = players[ i ].getColor();
         players[ i ].setGutiCount( 4 );
         for( int j = 0; j <= 3; j++ )
         {
            players[ i ].setLocation( j, pos[ a ] );
         }
      }
      updateGutiFromPlayerInfo();
      currentPlayer = 0;
      step = Constants.GENERATE_DIE;
      part = Constants.PART_1;
      printPlayerInfo();
      refreshBoard();
   }
   private void printPlayerInfo()                     //tag
   {
      String s = "Player:   " + players[ currentPlayer ].getPlayerName() + " (" + colorStrings[ players[ currentPlayer ].getColor() ] + ")" + " moves\n";
      s += "Team:   " + players[ currentPlayer ].getTeamName() + "\n";
      if( currentPlayer == Constants.RED )
      {
         s += "Keys:  Q    W";
      }
      else if( currentPlayer == Constants.BLUE )
      {
         s += "Keys:  O    P";
      }
      else if( currentPlayer == Constants.GREEN )
      {
         s += "Keys:  X    C";
      }
      else if( currentPlayer == Constants.YELLOW )
      {
         s += "Keys:  N    M";
      }
      stateArea.setText( s );
   }
   private void clearPlayerInfo()                     //tag
   {
      stateArea.setText( "" );
   }
   private void clearPlayerRank()                     //tag
   {
      rankArea.setText( "" );
   }
   private void refreshBoard()                     //tag
   {
      boardPanel.repaint();
   }
   private void generatePoint()                     //tag
   {
      long e = System.currentTimeMillis();
      int a = ( int ) ( Math.abs( e - seed ) );
      a += ( ( ( new Random() ).nextDouble() ) * 6 );
      a %= 6;
      a += 1;
      if( debugStatus )
      {
         int aaa = 1;
         String aaaa = JOptionPane.showInputDialog( this, "Enter Die Value" );
         try
         {
            int aaaaa = Integer.parseInt( aaaa );
            if( aaaaa >= 1 && aaaaa <= 6 )
            {
               aaa = aaaaa;
            }
         }
         catch( NumberFormatException nfe )
         {
            //no operation
         }
         a = aaa;
      }
      if( part == Constants.PART_1 )
      {
         currentPoints[ 0 ] = a;
         currentPoints[ 1 ] = -1;
         currentPoints[ 2 ] = -1;
         currentPointsLength = 1;
         if( a != 6 )
         {
            step = Constants.GUTI_SELECT_MOVE;
            part = Constants.PART_1;
            buildAllPossibleMoves();
            currentSelectionDieIndex = 0;
            currentPermutationSelection = 0;
            selectNext();
         }
         else
         {
            part = Constants.PART_2;
         }
         updateDie();
      }
      else if( part == Constants.PART_2 )
      {
         currentPoints[ 1 ] = a;
         currentPoints[ 2 ] = -1;
         currentPointsLength = 2;
         if( a != 6 )
         {
            step = Constants.GUTI_SELECT_MOVE;
            part = Constants.PART_1;
            buildAllPossibleMoves();
            currentSelectionDieIndex = 0;
            currentPermutationSelection = 0;
            selectNext();
         }
         else
         {
            part = Constants.PART_3;
         }
         updateDie();
      }
      else if( part == Constants.PART_3 )
      {
         currentPoints[ 2 ] = a;
         currentPointsLength = 3;
         if( a != 6 )
         {
            step = Constants.GUTI_SELECT_MOVE;
            part = Constants.PART_1;
            buildAllPossibleMoves();
            currentSelectionDieIndex = 0;
            currentPermutationSelection = 0;
            selectNext();
         }
         else   //a==6
         {
            currentPoints[ 2 ] = 6;
            currentPointsLength = 0;
            step = Constants.GENERATE_DIE;   //Extra
            part = Constants.PART_1;
         }
         updateDie();
      }
   }
   private void updateDie()                     //tag
   {
      dies[ 0 ].setValue( currentPoints[ 0 ] );
      dies[ 1 ].setValue( currentPoints[ 1 ] );
      dies[ 2 ].setValue( currentPoints[ 2 ] );
   }
   private void buildAllPossibleMoves()                     //tag
   {
      int i, loopLimit = 1;
      for( i = 0; i <= 999; i++ )  //resetting all paths
      {
         path[ i ] = false;
      }
      for( i = 0; i < currentPointsLength; i++ )
      /* if currentPointlength = 1 then loopLimit = 10, 2 => 100, 3 => 1000 */
      {
         loopLimit *= 10;
      }
      for( specialIndex  = 0; specialIndex < loopLimit; specialIndex++ ) //main calculation
      {
         i = specialIndex;
         int a, b, c;   //seperating specialIndex into three decimal parts
         a = ( int )( i / 100 );
         b = ( int ) ( ( ( int )( i - ( a * 100 ) ) ) / 10 );
         c = ( int )( i - ( ( a * 100 ) + ( b * 10 ) ) );
         checkMove( i, a, b, c );
      }
      standardizePaths();   //changing path sequence a, b, c to c, b, a (reversing) for future easy usability
      selectNext();
   }
   private void standardizePaths()                     //tag
   {
      int i, j;
      for( i = 0; i < 999; i++ )
      {
         int a, b, c;   //seperating specialIndex into three decimal parts
         a = ( int )( i / 100 );
         b = ( int ) ( ( ( int )( i - ( a * 100 ) ) ) / 10 );
         c = ( int )( i - ( ( a * 100 ) + ( b * 10 ) ) );
         j = ( int ) ( ( c * 100 ) + ( b * 10 ) + a );  //e.g if i = 231 then j = 132, i, e, a kind of inversing
         tmp[ j ] = path[ i ];
      }
      for( i = 0; i < 999; i++ )
      {
         path[ i ] = tmp[ i ];
      }
   }
   private void checkMove( int index, int hundreds, int tens, int ones )                     //tag
   {
      if( currentPointsLength == 1 )  //only ones is counted
      {
         backupPlayers();
         if( checkMove2( ones, currentPoints[ 0 ] ) )
         {
            path[ index ] = true;
         }
         restorePlayersFromBackup();
      }
      else if( currentPointsLength == 2 )  //ones and tens are counted
      {
         backupPlayers();
         if( checkMove2( ones, currentPoints[ 0 ] ) && checkMove2( tens, currentPoints[ 1 ] ) )
         {
            path[ index ] = true;
         }
         restorePlayersFromBackup();
      }
      else if( currentPointsLength == 3 )  //all are counted
      {
         backupPlayers();
         if( checkMove2( ones, currentPoints[ 0 ] ) && checkMove2( tens, currentPoints[ 1 ] ) && checkMove2( hundreds, currentPoints[ 2 ] ) )
         {
            path[ index ] = true;
         }
         restorePlayersFromBackup();
      }
   }
   private boolean checkMove2( int index, int point )                     //tag
   {
      int a[] = PermutationPair.getPieces( index );
      if( a[ 0 ] != -1 )
      {
         if( !( players[ currentPlayer ].isMovable( a[ 0 ] ) ) )
         {
            return false;
         }
      }
      if( a[ 1 ] != -1 )
      {
         if( !( players[ currentPlayer ].isMovable( a[ 1 ] ) ) )
         {
            return false;
         }
      }

      int p = point;
      if( PermutationPair.isDouble( index )  )
      {
         if( players[ currentPlayer ].isDoubling( a[ 0 ] ) || players[ currentPlayer ].isDoubling( a[ 1 ] ) )
         {
            if( !( players[ currentPlayer ].doublingWith( a[ 0 ] ) == a[ 1 ] ) )
            {
               return false;
            }
         }
         if( p % 2 == 0  && players[ currentPlayer ].getLocation( a[ 0 ] ) != Position.getRestingPosition( players[ currentPlayer ].getColor() ) && players[ currentPlayer ].getLocation( a[ 1 ] ) != Position.getRestingPosition( players[ currentPlayer ].getColor() ) && players[ currentPlayer ].getLocation( a[ 0 ] ) == players[ currentPlayer ].getLocation( a[ 1 ] ) )   //check if point is odd or even
         {
            int m = ( int ) ( p / 2 );
            if( checkPath( a, players[ currentPlayer ].getLocation( a[ 0 ] ), m, true ) )
            {
               players[ currentPlayer ].setDoubling( a[ 0 ], a[ 1 ] );
               return true;
            }
            else
            {
               return false;
            }
         }
         else
         {
            return false;
         }
      }
      else  //single
      {
         boolean flag = true;  //double Guti can't be moved singly
         if( a[ 0 ] != -1 )
         {
            if( players[ currentPlayer ].isDoubling( a[ 0 ] ) && !( board.getPosition( players[ currentPlayer ].getLocation( a[ 0 ] ) ).isStarIndicated() ) )
            {
               flag = false;
            }
         }
         else
         {
            flag = false;
            error( "Invalid Selection" );
         }
         if( flag )
         {
            if( p == 6 )   //checking for chakka
            {
               if( players[ currentPlayer ].getLocation( a[ 0 ] ) == Position.getRestingPosition( players[ currentPlayer ].getColor() ) )
               {
                  players[ currentPlayer ].setLocation( a[ 0 ] , Position.getStartPosition( players[ currentPlayer ].getColor() ) );
                  return true;
               }
               else
               {
                  if( checkPath( a, players[ currentPlayer ].getLocation( a[ 0 ] ), p, false ) )
                     return true;
                  else
                     return false;
               }
            }
            else  //not chakka
            {
               if( checkPath( a, players[ currentPlayer ].getLocation( a[ 0 ] ), p, false ) )
                  return true;
               else
                  return false;
            }
         }
         else
         {
            return false;
         }
      }
   }
   private boolean checkPath( int a[], int currentLocation, int point, boolean isDouble )                     //tag
   {
      boolean passFlag = true;
      Position p;
      p = board.getPosition( currentLocation );  //here location is 'id' of position
      for( int i = 0; i < point; i++ )
      {
         if( p != null )
            p = board.getPosition( p.getNext( players[ currentPlayer ].getColor() ) );
         else
         {
            passFlag = false;
            break;
         }
         if( p != null )
         {
            if( p.isBlockedFor( players[ currentPlayer ].getTeamID() ) && i != ( point - 1 ) )  //Guti can stay in blocked location but can't pass through
            {
               passFlag = false;
               break;
            }
            else if( p.isBlockedFor( players[ currentPlayer ].getTeamID() ) && i == ( point - 1 ) )
            {
               if( a[ 0 ] != -1 )
               {
                  players[ currentPlayer ].setMovable( a[ 0 ], false );
               }
               if( a[ 1 ] != -1 )
               {
                  players[ currentPlayer ].setMovable( a[ 1 ], false );
               }
            }
         }
         else
         {
            passFlag = false;
            break;
         }
      }
      if( passFlag )
      {
         int location = p.getID();
         players[ currentPlayer ].setLocation( a[ 0 ], location );
         if( isDouble )
         {
            players[ currentPlayer ].setLocation( a[ 1 ], location );
         }
         else  //bug solved else block 9:34 PM 9/12/2005 ( was omitted )
         {
            players[ currentPlayer ].releaseDoubling( a[ 0 ] );
         }
         return true;
      }
      else
         return false;
   }
   private void selectNext()                     //tag
   {
      if( currentSelectionDieIndex == 0 )
      {
         boolean findingFlag = false;
         count = 0;
         while( true )
         {
            findingFlag = false;
            currentPermutationSelection++;
            count++;
            currentPermutationSelection%=10;  //creating a cycle
            for( int i = ( currentPermutationSelection * 100 ); i < ( ( currentPermutationSelection + 1 ) * 100 ); i++ )
            {
               if( path[ i ] == true )
               {
                  findingFlag = true;
                  break;
               }
            }
            if( count >= 11 )   //if a full search cycle completes and no path found
            {
               setupEmptySelection();
               return;
            }
            if( findingFlag == true )
            {
               lastSelection[ currentSelectionDieIndex ] = currentPermutationSelection;
               setupSelection();
               break;   //breaking infinite while loop
            }
         }
      }
      else if( currentSelectionDieIndex == 1 )
      {
         boolean findingFlag = false;
         count = 0;
         while( true )
         {
            findingFlag = false;
            currentPermutationSelection++;
            count++;
            currentPermutationSelection%=10;  //creating a cycle
            for( int i = ( ( lastSelection[ 0 ] * 100 ) + ( currentPermutationSelection * 10 ) ); i < ( ( lastSelection[ 0 ] * 100 ) + ( ( currentPermutationSelection + 1 ) * 10 ) ); i++ )
            {
               if( path[ i ] == true )
               {
                  findingFlag = true;
                  break;
               }
            }
            if( count >= 11 )   //if a full search cycle completes and no path found
            {
               setupEmptySelection();
               return;
            }
            if( findingFlag == true )
            {
               lastSelection[ currentSelectionDieIndex ] = currentPermutationSelection;
               setupSelection();
               break;   //breaking infinite while loop
            }
         }
      }
      else if( currentSelectionDieIndex == 2 )
      {
         boolean findingFlag = false;
         int count = 0;
         while( true )
         {
            findingFlag = false;
            currentPermutationSelection++;
            count++;
            currentPermutationSelection%=10;  //creating a cycle
            for( int i = ( ( lastSelection[ 0 ] * 100 ) + ( lastSelection[ 1 ] * 10 ) + currentPermutationSelection ); i < ( ( lastSelection[ 0 ] * 100 ) + ( lastSelection[ 1 ] * 10 ) + ( currentPermutationSelection + 1 ) ) ; i++ )
            {
               if( path[ i ] == true )
               {
                  findingFlag = true;
                  break;
               }
            }
            if( count >= 11 )   //if a full search cycle completes and no path found
            {
               setupEmptySelection();
               return;
            }
            if( findingFlag == true )
            {
               lastSelection[ currentSelectionDieIndex ] = currentPermutationSelection;
               setupSelection();
               break;   //breaking infinite while loop
            }
         }
      }
   }
   private void setupEmptySelection()                     //tag
   {
      selectionType = Constants.SINGLE;
      selectedPositionIndex = Position.getEmptyPosition( players[ currentPlayer ].getColor() );
      isBoardSelected = true;
      dies[ 0 ].deselect();
      dies[ 1 ].deselect();
      dies[ 2 ].deselect();
      dies[ currentSelectionDieIndex ].select();
      movePathFound = false;
      refreshBoard();
   }
   private void setupSelection()                     //tag
   {
      int a, b[], c;
      a = currentPermutationSelection;
      b = PermutationPair.getPieces( a );
      if( PermutationPair.isDouble( a ) )
      {
         selectionType = Constants.DOUBLE;
      }
      else
      {
         selectionType = Constants.SINGLE;
      }
      selectedPositionIndex = players[ currentPlayer ].getLocation( b[ 0 ] );
      isBoardSelected = true;
      dies[ 0 ].deselect();
      dies[ 1 ].deselect();
      dies[ 2 ].deselect();
      dies[ currentSelectionDieIndex ].select();
      movePathFound = true;
      refreshBoard();
   }
   private void moveNext()                     //tag
   {
      if( !movePathFound )
      {
         changePlayer();
         return;
      }
      int perm = lastSelection[ currentSelectionDieIndex ];
      int point = currentPoints[ currentSelectionDieIndex ];
      int a[] = PermutationPair.getPieces( perm );
      if( PermutationPair.isDouble( perm ) )
      {
         point = ( int ) ( point / 2 );
         int currentLocation = players[ currentPlayer ].getLocation( a[ 0 ] );
         int nextLocation = findNextLocation( currentLocation, players[ currentPlayer ].getColor(), point );
         if( currentLocation == -1 || nextLocation == -1 )
         {
            error( "1232 location invalid" );
            return;
         }
         board.getPosition( currentLocation ).unblock( players[ currentPlayer ].getPlayerID(), players[ currentPlayer ].getTeamID(), a[ 0 ], a[ 1 ] );
         board.getPosition( nextLocation ).block( players[ currentPlayer ].getPlayerID(), players[ currentPlayer ].getTeamID(), a[ 0 ], a[ 1 ] );
         GutiContainer c = board.getContainer( currentLocation );
         c.deleteOne( players[ currentPlayer ].getGuti( a[ 0 ] ) );
         c.deleteOne( players[ currentPlayer ].getGuti( a[ 1 ] ) );
         c = board.getContainer( nextLocation );
         c.addOne( players[ currentPlayer ].getGuti( a[ 0 ] ) );
         c.addOne( players[ currentPlayer ].getGuti( a[ 1 ] ) );
         players[ currentPlayer ].setLocation( a[ 0 ], nextLocation );
         players[ currentPlayer ].setLocation( a[ 1 ], nextLocation );
         players[ currentPlayer ].setDoubling( a[ 0 ], a[ 1 ] );
         setKilledPieces( a[0], nextLocation, players[ currentPlayer ].getTeamID(), Constants.DOUBLE );
      }
      else  //not double
      {
         players[ currentPlayer ].releaseDoubling( a[ 0 ] );
         board.getPosition( players[ currentPlayer ].getLocation( a[ 0 ] ) ).releaseBlocking( players[ currentPlayer ].getPlayerID(), players[ currentPlayer ].getTeamID(), a[ 0 ] );
         int currentLocation = 0, nextLocation;
         currentLocation = players[ currentPlayer ].getLocation( a[ 0 ] );
         if( point == 6 && currentLocation == Position.getRestingPosition( players[ currentPlayer ].getColor() ) )
         {
            nextLocation = Position.getStartPosition( players[ currentPlayer ].getColor() );
         }
         else //if point == 6 or else but currentPosition is not in resting position
         {
            nextLocation = findNextLocation( currentLocation, players[ currentPlayer ].getColor(), point );
         }
         GutiContainer c = board.getContainer( currentLocation );
         c.deleteOne( players[ currentPlayer ].getGuti( a[ 0 ] ) );
         c = board.getContainer( nextLocation );
         c.addOne( players[ currentPlayer ].getGuti( a[ 0 ] ) );
         players[ currentPlayer ].setLocation( a[ 0 ], nextLocation );
         setKilledPieces( a[ 0 ], nextLocation, players[ currentPlayer ].getTeamID(), Constants.SINGLE );
      }
      boardGraphicsChanged = true;
      refreshBoard();
      currentSelectionDieIndex++;
      if( currentPointsLength == currentSelectionDieIndex )
      {
         removeKilledPieces();
         changePlayer();
      }
      else
      {
         selectNext();
      }
   }
   private void setKilledPieces( int gid, int location, int tid, int type )                     //tag
   {
      if( board.getPosition( location ).isSafe() )
         return;
      if( type == Constants.DOUBLE )
      {
         if( board.getContainer( location ).length() <= 4 )  //two own and two opponent pieces
         {
            Guti gt1 = board.getContainer( location ).getPiece( 0 );
            Guti gt2 = board.getContainer( location ).getPiece( 1 );
            if( gt1.getPlayerID() == gt2.getPlayerID() )
            {
               if( players[ gt1.getPlayerID() ].isDoubling( gt1.getGutiID() ) && ( gt2.getGutiID() == players[ gt1.getPlayerID() ].doublingWith( gt1.getGutiID() ) ) )
               {
                  if( players[ gt1.getPlayerID() ].getTeamID() != tid )
                  {
                     killInfo[ killInfoLength ] = new KillInfo( gid, location, true );
                     killInfoLength++;
                  }
               }
            }
         }
      }
      else if( type == Constants.SINGLE )
      {
         if( board.getContainer( location ).length() <= 2 )  //one own and one opponent pieces
         {
            Guti gt1 = board.getContainer( location ).getPiece( 0 );
            if( players[ gt1.getPlayerID() ].getTeamID() != tid )
            {
               killInfo[ killInfoLength ] = new KillInfo( gid, location, false );
               killInfoLength++;
            }
         }
      }
      else
      {
         error( "Unknown Type found" );
      }
   }
   private void removeKilledPieces()
   {
      for( int i = 0; i < killInfoLength; i++ )
      {
         if( players[ currentPlayer ].getLocation( killInfo[ i ].getKillerGutiID() ) ==  killInfo[ i ].getKillingLocation() )
         {
            if( killInfo[ i ].isDoublyKilled() )
            {
               Guti gt1 = board.getContainer( killInfo[ i ].getKillingLocation() ).getPiece( 0 );
               Guti gt2 = board.getContainer( killInfo[ i ].getKillingLocation() ).getPiece( 1 );
               GutiContainer cntprv = board.getContainer( killInfo[ i ].getKillingLocation() );
               gt1 = cntprv.deleteOne( gt1 );
               gt2 = cntprv.deleteOne( gt2 );
               int id = gt1.getPlayerID();
               GutiContainer cntnxt = board.getContainer( Position.getRestingPosition( players[ id ].getColor() ) );
               if( gt1 != null && gt2 != null)
               {
                  Position p = board.getPosition( players[ gt1.getPlayerID() ].getLocation( gt1.getGutiID() ) );
                  p.unblock( gt1.getPlayerID(), players[ gt1.getPlayerID() ].getTeamID(), gt1.getGutiID(), gt2.getGutiID()  );
                  cntnxt.addOne( gt1 );
                  players[ gt1.getPlayerID() ].setLocation( gt1.getGutiID() , cntnxt.getID() );
                  cntnxt.addOne( gt2 );
                  players[ gt2.getPlayerID() ].setLocation( gt2.getGutiID() , cntnxt.getID() );
                  players[ gt1.getPlayerID() ].unsetDoubling( gt1.getGutiID(), gt2.getGutiID() );
                  sound.playSound( Constants.KILL_SOUND );
               }
               boardGraphicsChanged = true;
               refreshBoard();
            }
            else   //singly killed
            {
               Guti gt1 = board.getContainer( killInfo[ i ].getKillingLocation() ).getPiece( 0 );
               GutiContainer cnt = board.getContainer( killInfo[ i ].getKillingLocation() );
               gt1 = cnt.deleteOne( gt1 );
               int id = gt1.getPlayerID();
               cnt = board.getContainer( Position.getRestingPosition( players[ id ].getColor() ) );
               if( gt1 != null )
               {
                  cnt.addOne( gt1 );
                  players[ gt1.getPlayerID() ].setLocation( gt1.getGutiID() , cnt.getID() );
                  sound.playSound( Constants.KILL_SOUND );
               }
               boardGraphicsChanged = true;
               refreshBoard();
            }
         }
      }
   }
   private int findNextLocation( int from, int color, int point )                     //tag
   {
      int loc = from;
      for( int i  =0; i < point; i++ )
      {
         if( loc == -1 )
         {
            break;
         }
         loc = board.getPosition( loc ).getNext( color );
      }
      return loc;
   }
   private void changePlayer()                     //tag  //cur
   {
      checkCurrentPlayerRetirement();
      if( checkIfGameOver() )
      {
         return;
      }
      currentPlayer++;
      currentPlayer %= numberOfPlayers;
      if( players[ currentPlayer ].isRetired() )
      {
         changePlayer();
      }
      else
      {
         resetAllRequiredForNewPlayer();
         printPlayerInfo();
      }
   }
   private void resetAllRequiredForNewPlayer()                     //tag
   {
      dies[ 0 ].clear();
      dies[ 1 ].clear();
      dies[ 2 ].clear();
      isBoardSelected = false;
      currentPoints[ 0 ] = -1;
      currentPoints[ 1 ] = -1;
      currentPoints[ 2 ] = -1;
      currentPointsLength = 0;
      currentPermutationSelection = 0;
      lastSelection[ 0 ] = 0;
      lastSelection[ 1 ] = 0;
      lastSelection[ 2 ] = 0;
      step = Constants.GENERATE_DIE;
      part = Constants.PART_1;
      movePathFound = true;
      killInfoLength = 0;
      killInfo = null;
      killInfo = new KillInfo[ 3 ];
      players[ currentPlayer ].resetMove();
      refreshBoard();
   }
   private void checkCurrentPlayerRetirement()                     //tag
   {
      if( players[ currentPlayer ].isRetired() )
         return;
      int retireFlag = 0;
      for( int i = 0; i <= 3; i++ )
      {
         if( players[ currentPlayer ].getLocation( i ) == Position.getFinalPosition( players[ currentPlayer ].getColor() ) )
         {
            retireFlag++;
         }
      }
      if( retireFlag >= 4 )  //all four pieces are at final positions
      {
         players[ currentPlayer ].setRetired( true );
         if( currentRank == 1 )
         {
            rankArea.setText( "Rank:\n" );
         }
         rankArea.append( "\n(" + currentRank + ")   Player: " + players[ currentPlayer ].getPlayerName() + "\n            Team: " + players[ currentPlayer ].getTeamName() + "\n" );
         currentRank++;
         rankID[ rankLength ] = currentPlayer;
         rankLength++;         
      }
   }
   private void backupPlayers()                     //tag
   {
      backupOfNumberOfPlayers = numberOfPlayers;
      backupOfPlayers[ 0 ] = new Player( players[ 0 ] );
      backupOfPlayers[ 1 ] = new Player( players[ 1 ] );
      backupOfPlayers[ 2 ] = new Player( players[ 2 ] );
      backupOfPlayers[ 3 ] = new Player( players[ 3 ] );
   }
   private void restorePlayersFromBackup()                     //tag
   {
      numberOfPlayers = backupOfNumberOfPlayers;
      players[ 0 ] = backupOfPlayers[ 0 ];
      players[ 1 ] = backupOfPlayers[ 1 ];
      players[ 2 ] = backupOfPlayers[ 2 ];
      players[ 3 ] = backupOfPlayers[ 3 ];
   }
   private void updateGutiFromPlayerInfo()                     //tag
   {
      for( int i = 0; i < numberOfPlayers; i++ )
      {
         int c = players[ i ].getColor();
         for( int j = 0; j < players[ i ].getGutiCount(); j++ )
         {
            Guti gt = new Guti( i, j, c );
            board.getContainer( players[ i ].getLocation( j ) ).addOne( gt);
         }
      }
      boardGraphicsChanged = true;
      refreshBoard();
   }
   private void emptyAllContainers()                     //tag
   {
      int i, k;
      for( k = 0; k < positionDrawSequence.length ; k++ )
      {
         i = positionDrawSequence[ k ];
         board.getContainer( i ).reset();
      }
      boardGraphicsChanged = true;
      refreshBoard();
   }
   private void debug( String msg )   //in debug function  command output is used for huge info
   {
      if( debugStatus )
         System.out.println( msg );
   }
   private void error( String msg )   //in error function JOptionPane is used for graphical and quick info
   {
      if( debugStatus )
      {
         JOptionPane.showMessageDialog( this, "Error message: \n" + msg );
      }
   }
   public static void main( String args[] )                     //tag
   {

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice myDevice = ge.getDefaultScreenDevice();
      DisplayMode newDisplayMode = null, oldDisplayMode = null;
      try 
      {
         oldDisplayMode = myDevice.getDisplayMode();
         if( oldDisplayMode.getWidth() != 800 || oldDisplayMode.getHeight() != 600 )
         {
            JOptionPane.showMessageDialog( null, "Please change display resolution to 800X600 pixels and run this program again", "Change resolution", JOptionPane.INFORMATION_MESSAGE );
            System.exit( 0 );
         }
         JFrame frame = new JFrame();
         frame.setTitle( "Ludu" );
         frame.setDefaultCloseOperation( JDialog.DO_NOTHING_ON_CLOSE );
         frame.setSize( 100, 100 );
         Ludu app = new Ludu( frame );
         frame.setVisible( true );
         frame.requestFocus();
         newDisplayMode = new DisplayMode( 800, 600, 16, 72 );
         myDevice.setFullScreenWindow( app );
         myDevice.setDisplayMode( newDisplayMode );
         app.setVisible( true );
         app.requestFocusInWindow();
      }
      finally 
      {
         myDevice.setDisplayMode(oldDisplayMode);
         myDevice.setFullScreenWindow(null);
      }
   }
}
