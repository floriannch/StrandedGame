 

/**
 * Class CommandWords - Holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // ### Attributes ###

    // a constant array that will hold all valid command words
    private final String[] aValidCommands;

    // ### Constructor ###
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        this.aValidCommands = new String[14];
        this.aValidCommands[0] = "go";
        this.aValidCommands[1] = "look";
        this.aValidCommands[2] = "help";
        this.aValidCommands[3] = "quit";
        this.aValidCommands[4] = "eat";
        this.aValidCommands[5] = "back";
        this.aValidCommands[6] = "test";
        this.aValidCommands[7] = "take";
        this.aValidCommands[8] = "drop";
        this.aValidCommands[9] = "inventory";
        this.aValidCommands[10] = "fire";
        this.aValidCommands[11] = "charge";
        this.aValidCommands[12] = "use";
        this.aValidCommands[13] = "win";
    } // CommandWords()
    
    // ### Getter ###
    
    /**
     * Prints all valid commands to System.out.
     * 
     * @return A list of all the valid commands.
    */
    public String getCommandList()
    {
        String vCommandList = "";
        for(String vCommand : this.aValidCommands)
        {
            vCommandList += (vCommand + " ");
        }
        return vCommandList;
    } // showAll()
    
    // ### Other Methods ###
    
    /**
     * Check whether a given String is a valid command word. 
     * 
     * @param pString Command to be checked.
     * 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    {
        for ( int vI=0; vI<this.aValidCommands.length; vI++ ) {
            if ( this.aValidCommands[vI].equals( pString ) )
                return true;
        } // for
        // if we get here, the string was not found in the commands :
        return false;
    } // isCommand()
} // CommandWords
