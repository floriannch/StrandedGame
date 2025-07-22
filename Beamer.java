/**
 * Class Beamer - Represents a teleportation device in the game.
 *
 * It can be charged with a specific room and later fired to teleport
 * the player back to the charged room. A beamer can be recharged after use.
 *
 * The Beamer class extends the {@link Item} class to integrate with the game's item system.
 * 
 * @author Florian NELCHA
 */
public class Beamer extends Item 
{
    // ### Attributes ###
    
    private Room aChargedRoom;
    private boolean aIsCharged;
    
    // ### Constructor ###
    
    /**
     * Constructs a new unloaded Beamer with all the attributes of its super class, .
     * 
     * @param pN The name of the Beamer.
     * @param pD The description of the Beamer.
     * @param pW The weight of the Beamer.
     */
    public Beamer(final String pN, final String pD, final double pW)
    {
        super(pN,pD,pW);
        this.aIsCharged = false;        
    }
    
    // ### Other Methods ###
    
    /**
     * Charges the beamer with the given room.
     * @param pR The room to charge the beamer with.
     */
    public void charge(final Room pR) 
    {
        this.aChargedRoom = pR;
        this.aIsCharged = true;
    }

    /**
     * Fires the beamer, returning the charged room.
     * @return The room the beamer is charged with, or null if not charged.
     */
    public Room fire() 
    {
        if (!this.aIsCharged) 
        {
            return null;
        }
        Room vTeleportRoom = this.aChargedRoom;
        this.aChargedRoom = null;
        this.aIsCharged = false;
        return vTeleportRoom;
    }

    /**
     * Checks if the beamer is charged.
     * @return True if charged, false otherwise.
     */
    public boolean isCharged()
    {
        return this.aIsCharged;
    }
}
