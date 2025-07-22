import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - Represents a location in the game.
 * 
 * A Room is a specific location in the game's map. Each room has a description
 * and can be connected to other rooms in various directions.
 * 
 * 
 * @author Florian NELCHA
 */
public class Room
{
    // ### Attributes ###
    
    private String aDescription;
    private HashMap <String,Room> aExits;
    private String aImageName;
    private ItemList aItems;
    
    // ### Constructor ###
    
    /**
     * Constructor to initialize the room with a description, an image, and two HashMaps with
     * its exits and Items.
     * 
     * @param pD Description of the room.
     * @param pI Location of the room's image.
     */
    public Room(final String pD, final String pI)
    {
        this.aDescription = pD;
        this.aExits = new HashMap <String,Room>();
        this.aImageName = pI;
        this.aItems = new ItemList();
    } // Room()
    
    // ### Getters ###
    
    /**
     * Gets the description of the room.
     * 
     * @return Description of the room.
     */
    public String getDescription()
    {
        return this.aDescription;
    } // getDescription()
    
    /**
     * Gets the room in the specified direction if it exists.
     * 
     * @param  pDirection The direction in which to look for an exit.
     * @return The neighboring room in the specified direction, or null
     * if it doesn't exist.
     */
    public Room getExit(final String pDirection)
    {
        if (this.aExits.containsKey(pDirection))
        {
            return this.aExits.get(pDirection);
        }
        return null;
    } // getExit()
    
    /**
     * Creates a keySet of all the exits from the current room, then constructs
     * a string of it.
     * 
     * @return A string with all available exit directions.
     */
    public String getExitString()
    {
        String vExitString = "Exits :";
        Set<String> vKeys = this.aExits.keySet();
        for(String vExit : vKeys) 
        {
            vExitString += " " + vExit;
        }
        
        return vExitString;
    } // getExitString()
    
    /**
     * Returns a description of the current room, of the form : 
     *      You are in the Control Room.
     *      Exits : south
     * 
     * @return A description of the room, and its exits
     */
    public String getLongDescription()
    {
        return "You are " + this.aDescription + "\n" + getExitString() + "\n" + getItemString();
    } // getLongDescription()
    
    /**
     * Returns the name of the Room's image.
     * 
     * @return The name of the Room's image.
     */
    public String getImageName()
    {
         return this.aImageName;
    } //getImageName()
    
    /**
     * Returns the item having the specified name.
     * 
     * @param  pName The name of the item we look for.
     * @return The item having the specified name.
     */
    public Item getItem(final String pName) 
    {
        return this.aItems.getItem(pName);
    } //getItem()
    
    /**
     * Creates a keySet of all the Items in the current room, then constructs
     * a string of it.
     * 
     * @return A string with all available Items.
     */
    public String getItemString()
    {
        return "Items in the room : " + this.aItems.getItemString();
    } //getItemString()
    
    // ### Setters ###
    
    /**
     * Sets a new description for the room.
     * 
     * @param pNewD New description to set.
     */
    public void updateDescription(final String pNewD)
    {
        this.aDescription = pNewD;
    } //updateDescription()
    
    /**
     * Sets an exit for the specified direction to a neighboring room.
     * 
     * @param pDirection Direction of the exit.
     * @param pNeighbor  The neighboring room in that direction.
     */
    public void setExits(final String pDirection, 
                        final Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    } //setExits()
    
    /**
     * Adds an Item in the room
     * 
     * @param pItem Item to add in the room.
     */
    public void addItem(final Item pItem)
    {
        this.aItems.addItem(pItem);
    } //addItems()
    
    /**
     * Remove the specified Item from the room
     * 
     * @param pItemName Name of the Item to remove from the room.
     */
    public void removeItem(final String pItemName)
    {
        this.aItems.removeItem(pItemName);
    } //removeItem()
    
    // ### Other Methods ###
    
    /**
     * Checks if the given room is one of the exits of this room.
     * 
     * @param pRoom The room to check.
     * @return True if the room is an exit, false otherwise.
     */
    public boolean isExit(final Room pRoom)
    {
        return this.aExits.containsValue(pRoom);
    } // isExit()
} // Room
