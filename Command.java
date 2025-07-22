/**
 * Class Command - Represents a command issued by the player in the game.
 * 
 * A Command object stores a primary command word and, optionally, a second word
 * that provides additional details.
 * 
 * 
 * @author Florian NELCHA
 */
public class Command
{
    // ### Attributes ###

    private String aCommandWord;
    private String aSecondWord;
    
    // ### Constructor ###
    
    /**
     * Constructor that creates a Command object with a command word and an optional
     * second word.
     * 
     * @param pW1 The main command word.
     * @param pW2 An optional second word (e.g., direction or item).
     */
    public Command(final String pW1,final String pW2)
    {
        this.aCommandWord = pW1;
        this.aSecondWord = pW2;
    } // Command()
    
    // ### Getters ###
    
    /**
     * Gets the main command word.
     * 
     * @return The main command word.
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    } // getCommandWord()
    
    /**
     * Gets the secondary word if available.
     * 
     * @return The secondary word, or null if it does not exist.
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    } // getSecondWord()
    
    // ### Other methods ###
    
    /**
     * Checks if the command includes a secondary word.
     * 
     * @return true if there is a secondary word, false otherwise.
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null;
    } // hasSecondWord()
    
    /**
     * Checks if the command is unknown (if it has no main command word).
     * 
     * @return true if the command word is null, false otherwise.
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    } // isUnknown()
} // Command
