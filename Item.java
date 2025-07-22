/**
 * Class Item - Represents an object that can be placed in a Room.
 * Each Item has a name, a description, and a weight.
 *
 * @author Florian NELCHA
 */
public class Item
{
    // ### Attributes ###

    private String aName;
    private String aDesc;
    private double aWeight;

    // ### Constructor ###
    
    /**
     * Constructs a new Item with the specified name, description, and weight.
     * 
     * @param pName The name of the item.
     * @param pDesc A detailed description of the item.
     * @param pWeight The weight of the item.
     */
    public Item(final String pName, final String pDesc, final double pWeight)
    {
        this.aName = pName;
        this.aDesc = pDesc;
        this.aWeight = pWeight;
    } // Item()
    
    // ### Getters ###
    
    /**
     * Gets the name of the item.
     * 
     * @return The name of the item.
     */
    public String getName() 
    {
        return this.aName;
    } // getName() 
    
    /**
     * Gets the description of the item.
     * 
     * @return The description of the item.
     */
    public String getDescription() 
    {
        return this.aDesc;
    } // getDescription() 
    
    /**
     * Gets the weight of the item.
     * 
     * @return The weight of the item.
     */
    public double getWeight()
    {
        return this.aWeight;
    } // getWeight()
    
    /**
     * Returns a detailed string description of the item, including its weight.
     * 
     * @return A formatted string describing the item.
     */
    public String getItemString()
    {
        return "This is " + this.aDesc + " (Weight: " + this.aWeight + ")";
    } // getItemString()
}
