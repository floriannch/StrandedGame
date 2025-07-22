// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.net.URL;
// import java.awt.image.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.net.URL;



/**
 * Class UserInterface - Implements a simple graphical user interface with a 
 * text entry area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    
    private JButton    aInvButton;
    private JButton    aHelpButton;
    private JButton    aQuitButton;
    private JButton    aNorthButton;
    private JButton    aSouthButton;
    private JButton    aWestButton;
    private JButton    aEastButton;
    private JButton    aNWButton;
    private JButton    aSEButton;
    private JButton    aBackButton;
    private JButton    aUpButton;
    private JButton    aDownButton;
    
    public JLabel     aTimerLabel;
    private Timer     aGameTimer;
    private int       timeRemaining = 480;

    
    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     * 
     * @param pText The text to print.
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     * 
     * @param pText The text to print.
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     * 
     * @param pImageName The name of the image file to display.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "images/" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the entry field.
     * 
     * @param pOnOff If true, enables input; if false, disables input.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
            this.aGameTimer.stop();
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "Stranded" );
        this.aEntryField = new JTextField( 34 );

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(420, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );
        
        JLayeredPane vImagePlusTimer = new JLayeredPane();
        vImagePlusTimer.setPreferredSize(new Dimension(800, 600));
        
        this.aImage = new JLabel();
        this.aImage.setBounds(0, 0, 800, 600);
        vImagePlusTimer.add(this.aImage,0);
        
        this.aTimerLabel = new JLabel("00:00", JLabel.CENTER); 
        this.aTimerLabel.setFont(new Font("Arial", Font.BOLD, 20));  
        this.aTimerLabel.setForeground(Color.WHITE);
        this.aTimerLabel.setOpaque(true);
        this.aTimerLabel.setBackground(Color.BLACK);
        this.aTimerLabel.setBounds(350, 570, 100, 30);
        this.aTimerLabel.setVisible(false);
        vImagePlusTimer.add(this.aTimerLabel,1);

        this.aGameTimer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timeRemaining--;
                int vMinutes = timeRemaining / 60;
                int vSeconds = timeRemaining % 60;
                aTimerLabel.setText(String.format("%02d:%02d", vMinutes, vSeconds));
                
                if (timeRemaining <= 0)
                {
                    println("\nCritical alert !!! The ship has entered the atmosphere of the nearby planet.");
                    println("Hull integrity failing... Brace for impact !");
                    println("\nYou tried your best, but time has run out.\nThe ship succumbs to gravity, plummeting towards the surface.");
                    println("Your journey ends here. Game Over.");
                    enable( false );
                }
            }
        });
        this.aGameTimer.start();
        
        
        JPanel vRightPanel = new JPanel();
        GridLayout vGrid = new GridLayout(3, 3);
        vRightPanel.setLayout(vGrid);
        vGrid.setHgap(4);
        vGrid.setVgap(4);
        
        this.aNWButton = new JButton("🡤");
        this.aNWButton.setBackground(Color.BLACK);
        this.aNWButton.setForeground(Color.WHITE);
        this.aNWButton.addActionListener(this);
        vRightPanel.add(this.aNWButton);
        
        this.aNorthButton = new JButton("🡡");
        this.aNorthButton.setBackground(Color.BLACK);
        this.aNorthButton.setForeground(Color.WHITE);
        this.aNorthButton.addActionListener(this);
        vRightPanel.add(this.aNorthButton);
    
        this.aUpButton = new JButton("Up");
        this.aUpButton.setBackground(Color.BLACK);
        this.aUpButton.setForeground(Color.WHITE);
        this.aUpButton.addActionListener(this);
        vRightPanel.add(this.aUpButton);
    
        this.aWestButton = new JButton("🡠");
        this.aWestButton.setBackground(Color.BLACK);
        this.aWestButton.setForeground(Color.WHITE);
        this.aWestButton.addActionListener(this);
        vRightPanel.add(this.aWestButton);
        
        this.aBackButton = new JButton("Back");
        this.aBackButton.setBackground(Color.BLACK);
        this.aBackButton.setForeground(Color.WHITE);
        this.aBackButton.addActionListener(this);
        vRightPanel.add(this.aBackButton);
    
        this.aEastButton = new JButton("🡢");
        this.aEastButton.setBackground(Color.BLACK);
        this.aEastButton.setForeground(Color.WHITE);
        this.aEastButton.addActionListener(this);
        vRightPanel.add(this.aEastButton);
    
        this.aDownButton = new JButton("Down");
        this.aDownButton.setBackground(Color.BLACK);
        this.aDownButton.setForeground(Color.WHITE);
        this.aDownButton.addActionListener(this);
        vRightPanel.add(this.aDownButton);
    
        this.aSouthButton = new JButton("🡣");
        this.aSouthButton.setBackground(Color.BLACK);
        this.aSouthButton.setForeground(Color.WHITE);
        this.aSouthButton.addActionListener(this);
        vRightPanel.add(this.aSouthButton);
    
        this.aSEButton = new JButton("🡦");
        this.aSEButton.setBackground(Color.BLACK);
        this.aSEButton.setForeground(Color.WHITE);
        this.aSEButton.addActionListener(this);
        vRightPanel.add(this.aSEButton);
        
        
        JPanel vLeftPanel = new JPanel();
        vLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints vGBC = new GridBagConstraints();
        vGBC.gridx = 0;
        vGBC.weightx = 1.0;
        vGBC.weighty = 1.0;
        vGBC.fill = GridBagConstraints.BOTH;
        
        this.aInvButton = new JButton("Inventory");
        this.aInvButton.setBackground(Color.BLACK);
        this.aInvButton.setForeground(Color.WHITE);
        this.aInvButton.addActionListener(this);
        vLeftPanel.add(this.aInvButton,vGBC);
        
        this.aHelpButton = new JButton("Help");
        this.aHelpButton.setBackground(Color.BLACK);
        this.aHelpButton.setForeground(Color.WHITE);
        this.aHelpButton.addActionListener(this);
        vLeftPanel.add(this.aHelpButton,vGBC);
        
        this.aQuitButton = new JButton("Quit");
        this.aQuitButton.setBackground(Color.BLACK);
        this.aQuitButton.setForeground(Color.WHITE);
        this.aQuitButton.addActionListener(this);
        vLeftPanel.add(this.aQuitButton,vGBC);
        
        
        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add(vImagePlusTimer, BorderLayout.NORTH);
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );
        vPanel.add( vLeftPanel, BorderLayout.WEST);
        vPanel.add( vRightPanel, BorderLayout.EAST);
    
        
        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );

        // to end program when window is closed
        this.aMyFrame.addWindowListener(
            new WindowAdapter() { // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
        } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for buttons and entry textfield.
     * 
     * @param pE The ActionEvent triggered by the user's interaction.
     */
    @Override public void actionPerformed( final ActionEvent pE ) 
    {
        if (pE.getSource() == this.aInvButton) 
        {
            this.aEngine.interpretCommand("inventory");
        }
        if (pE.getSource() == this.aHelpButton) 
        {
            this.aEngine.interpretCommand("help");
        }
        if (pE.getSource() == this.aQuitButton) 
        {
            this.aEngine.interpretCommand("quit");
        }
        if (pE.getSource() == this.aNWButton) 
        {
            this.aEngine.interpretCommand("go northwest");
        }
        if (pE.getSource() == this.aNorthButton) 
        {
            this.aEngine.interpretCommand("go north");
        }
        if (pE.getSource() == this.aUpButton) 
        {
            this.aEngine.interpretCommand("go upstairs");
        }
        if (pE.getSource() == this.aDownButton) 
        {
            this.aEngine.interpretCommand("go downstairs");
        }
        if (pE.getSource() == this.aWestButton) 
        {
            this.aEngine.interpretCommand("go west");
        }
        if (pE.getSource() == this.aEastButton) 
        {
            this.aEngine.interpretCommand("go east");
        }
        if (pE.getSource() == this.aBackButton) 
        {
            this.aEngine.interpretCommand("back");
        }
        if (pE.getSource() == this.aSouthButton) 
        {
            this.aEngine.interpretCommand("go south");
        }
        if (pE.getSource() == this.aSEButton) 
        {
            this.aEngine.interpretCommand("go southeast");
        }
        else if (pE.getSource() == this.aEntryField)
        {
            this.processCommand();
        }
    } // actionPerformed(.)

    /**
     * A command has been entered in the entry field.  
     * Read the command and do whatever is necessary to process it.
     */
    private void processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText( "" );

        this.aEngine.interpretCommand( vInput );
    } // processCommand()
} // UserInterface 
