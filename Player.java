import java.util.Stack;
import java.util.HashMap;
import java.util.Set;

/**
 * The Player class represents a player in the game.
 * A player has a name, a current room, a collection of carried items,
 * and a stack of visited rooms to facilitate navigation.
 * The player can carry items up to a specified maximum weight.
 * The player can interact with the environment by picking up and dropping items,
 * and eating food.
 * 
 * @author Florian NELCHA
 */
public class Player
{
    // ### Attributes ###
    private String        aName;
    private Room          aCurrentRoom;
    private double        aInvWeight;
    private double        aMaxWeight;
    public  Stack<Room>   aVisitedRooms;
    private Item          aItemInHand;
    private ItemList      aInventory;
    private UserInterface aGui;
    
    // ### Constructor ###
    
    /**
     * Constructs a new player with a name and starting room.
     * The player's inventory is initialized with zero weight and a maximum weight of 10.0.
     * 
     * @param pName The name of the player.
     * @param pStartRoom The starting room for the player.
     */
    public Player(final String pName, final Room pStartRoom)
    {
        this.aName = pName;
        this.aCurrentRoom = pStartRoom;

        this.aInvWeight = 0.0;
        this.aMaxWeight = 10.0;
        
        this.aVisitedRooms = new Stack<>();
        this.aInventory = new ItemList();
    } // Player()
    
    // ### Setters ###
    
    /**
     * Sets the game graphical user interface.
     * 
     * @param pUserInterface instance of the userInterface class.
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
    } // setGUI()
    
    // ### Getters ###
    
    /**
     * Gets the player's name.
     * 
     * @return The name of the player.
     */
    public String getPlayerName() 
    {
        return this.aName;
    } // getPlayerName()
    
    /**
     * Gets the current room the player is in.
     * 
     * @return The current room.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    } // getCurrentRoom()
    
    /**
     * Gets the total weight of the items in the player's inventory.
     * 
     * @return The total weight of the inventory.
     */
    public double getInvWeight()
    {
        return this.aInventory.getTotalWeight();
    } // getInvWeight()
    
    /**
     * Returns a string representation of the player's inventory.
     * 
     * @return A string displaying the items in the inventory and their total weight.
     */
    public String getInvString()
    {
        return "Inventory : " + this.aInventory.getItemString() + "\nTotal weight: " + this.getInvWeight() + " (max " + this.aMaxWeight + ")";
    } // getInvString()
    
    // ### Other methods ###
    
    /**
     * Moves the player to a new room and adds the 
     * current room to the "visited rooms" history.
     * 
     * @param pR The room the player is moving to.
     */
    public void goRoom(final Room pR)
    {
        this.aVisitedRooms.push(this.aCurrentRoom);
        this.aCurrentRoom = pR;
    } // goRoom()
    
     /**
     * Moves the player back to the previous room (if it's accessible)
     * using the "visited rooms" history.
     */
    public void goBack()
    {
        Room vPreviousRoom = this.aVisitedRooms.pop();
        if (!this.aCurrentRoom.isExit(vPreviousRoom))
        {
            this.aGui.println("You cannot go back this way.");
            return;
        }
        this.aCurrentRoom = vPreviousRoom;
    } // goBack()
    
    /**
     * Allows the player to pick up an item from the current room and add it to their inventory.
     * The player cannot carry the item if it exceeds their maximum weight capacity.
     * 
     * @param pItemName The name of the item to be picked up.
     */
    public void take(final String pItemName)
    {
        Item vItem = this.aCurrentRoom.getItem(pItemName);
        if (vItem == null)
        {
            this.aGui.println("There is no item with that name here.");
            return;
        }
        
        double vNewWeight = this.getInvWeight() + vItem.getWeight();
        
        if (vNewWeight > this.aMaxWeight)
        {
            this.aGui.println("Maximum weight exceeded. You cannot carry that item.");
            return;
        }
        
        this.aInventory.addItem(vItem);
        this.aCurrentRoom.removeItem(pItemName);
        this.aGui.println(pItemName + " picked up.");
        
        if(pItemName.equals("watch"))
        {
            this.aGui.aTimerLabel.setVisible(true);
        }
    } // take()
    
    /**
     * Drops an item from the player's inventory into the current room.
     * 
     * @param pItemName The name of the item to be dropped.
     */
    public void drop(final String pItemName)
    {
        Item vItem = this.aInventory.getItem(pItemName);
        if (vItem == null)
        {
            this.aGui.println("You are not carrying that item.");
            return;
        }
        this.aInventory.removeItem(pItemName);
        this.aCurrentRoom.addItem(vItem);
        this.aGui.println(pItemName + " dropped.");
        
        if(pItemName.equals("watch"))
        {
            this.aGui.aTimerLabel.setVisible(false);
        }
    } // drop()
    
    /**
     * When called, the player uses the specified item.
     * Only works if the player wants to use a "usable" item, in the right room.
     * 
     * @param pItemName The name of the item to be used.
     * @return True if the item has correctly been used, false otherwise.
     */
    public boolean use(final String pItemName)
    {
        Item vItem = this.aInventory.getItem(pItemName);
        if (vItem == null)
        {
            this.aGui.println("You are not carrying that item.");
            return false;
        }
        
        if (pItemName.equals("sensor") && this.aInventory.getItem("toolbox") == null)
        {
            this.aGui.println("You need a toolbox to place the sensor.");
            return false;
        }
        
        if (pItemName.equals("sensor") || pItemName.equals("fuse") || pItemName.equals("fuel"))
        {
            this.aInventory.removeItem(pItemName);
            return true;
        }
        else
        {
            this.aGui.println("You cannot use this.");
            return false;
        }
    } // use()
    
    /**
     * Allows the player to eat an item, modifying the player's attributes
     * based on the item.
     * 
     * @param pItemName The name of the item to be eaten.
     */
    public void eat(final String pItemName) 
    {
        Item vItem = this.aInventory.getItem(pItemName);
        if (vItem == null) 
        {
            this.aGui.println("You are not carrying that item.");
            return;
        }
    
        if (pItemName.equals("boost_pill")) 
        {
            this.aMaxWeight *= 2;
            this.aInventory.removeItem(pItemName);
            this.aGui.println("You ate the Boost Pill! Your carrying capacity has doubled to " + this.aMaxWeight + ".");
        } 
        else if (pItemName.equals("chips"))
        {
            this.aInventory.removeItem(pItemName);
            this.aGui.println("You ate a pack of Orbitos ! You are not hungry anymore.");
        }
        else
        {
            this.aGui.println("You cannot eat this.");
        }
    } // eat()
    
    /**
     * Charges the beamer in the player's inventory with the current 
     * room, if the player is carrying it. Otherwise, a message is displayed.
     * 
     * @param pItemName The room to be charged in the beamer.
     */
    public void chargeBeamer(final String pItemName)
    {
        Item vItem = this.aInventory.getItem(pItemName);
        if (vItem == null) 
        {
            this.aGui.println("You are not carrying the beamer.");
            return;
        }
        
        Beamer vB = (Beamer)vItem;
        
        vB.charge(this.aCurrentRoom);
        this.aGui.println("Beamer charged.");
    }
    
    /**
     * Fires the beamer in the player's inventory to teleport them to the charged 
     * room, if the player is carrying it.
     * If its not charged or not in the player's inventory, a message is displayed.
     * 
     * @param pItemName The room to be teleported in.
     */
    public void fireBeamer(final String pItemName)
    {
        Item vItem = this.aInventory.getItem(pItemName);
        if (vItem == null) 
        {
            this.aGui.println("You are not carrying the beamer.");
            return;
        }
        
        Beamer vB = (Beamer)vItem;

        Room vFiredRoom = vB.fire();
        if(vFiredRoom == null)
        {
            this.aGui.println("The beamer is not charged.");
            return;
        }
        this.aCurrentRoom = vFiredRoom;
        this.aGui.println("Teleported " + this.aCurrentRoom.getLongDescription().substring(8));
        this.aGui.showImage(this.aCurrentRoom.getImageName());
    }
}
