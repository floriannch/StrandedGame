import java.util.HashMap;
import java.util.Set;

/**
 * Class ItemList - Represents a collection of items.
 * It allows adding, removing, and retrieving items in the list.
 * The class also provides methods to get the total weight of items
 * and return a string representation of the items in the list.
 * 
 * @author Florian NELCHA
 */
public class ItemList
{
    // ### Attributes ###
    
    private HashMap <String, Item> aItems;
    
    // ### Constructor ###
    
    /**
     * Constructs an empty item list.
     */
    public ItemList()
    {
        this.aItems = new HashMap<>();
    } //ItemList()

    // ### Getters ###
    
    /**
     * Returns the item having the specified name.
     * 
     * @param  pName The name of the item we look for.
     * @return The item having the specified name.
     */
    public Item getItem(final String pName) 
    {
        return this.aItems.get(pName);
    } //getItem()
        
    /**
     * Creates a keySet of all the Items in the list, then constructs
     * a string of it.
     * 
     * @return A string with all available Items.
     */
    public String getItemString()
    {        
        if (this.aItems.isEmpty()) 
        {
            return "No items.";
        }
        
        String vItemsStr = "";
        
        Set<String> vKeys = this.aItems.keySet();
        for (String vI : vKeys) 
        {
            vItemsStr += vI + " ";
        }
        
        return vItemsStr;
    } //getItemString()
    
    /**
     * Returns the total weight of all the items in the list.
     * 
     * @return The total weight of the items.
     */
    public double getTotalWeight()
    {
        double vTotal = 0.0;
        for (Item vI : this.aItems.values())
        {
            vTotal += vI.getWeight();
        }
        return vTotal;
    } //getTotalWeight()
    
    // ### Other methods ###
    
    /**
     * Adds an Item into the list
     * 
     * @param pItem Item to add in the list.
     */
    public void addItem(final Item pItem)
    {
        this.aItems.put(pItem.getName(), pItem);
    } //addItems()
    
    /**
     * Remove the specified Item from the list
     * 
     * @param pItemName Name of the Item to remove from the list.
     */
    public void removeItem(final String pItemName)
    {
        if (this.aItems.containsKey(pItemName))
            this.aItems.remove(pItemName);
    } //removeItem()
}
