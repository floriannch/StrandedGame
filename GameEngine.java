import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


/**
 *  Class GameEngine - Creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 *  
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class GameEngine
{
    private Parser        aParser;
    private UserInterface aGui;
    private Player        aPlayer;
    
    private Room          aGenerators;
    private Room          aReactors;
    private Room          aControlRoom;
    
    private List<String>  aWinItems = Arrays.asList("fuse","fuel","sensor");
    private List<String>  aUsedItems = new ArrayList();

    
    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.createRooms();
    } // GameEngine()

    /**
     * Sets the game graphical user interface.
     * 
     * @param pUserInterface instance of the userInterface class.
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.aPlayer.setGUI(pUserInterface);
        this.printWelcome();
    } // setGUI()

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.print("\n");
        this.aGui.println("Welcome to Stranded !");
        this.aGui.println("You found yourself in an deserted spacecraft orbiting an unknown planet.");
        this.aGui.println("Your objective: explore the ship, discover what happened,\nand repair the critical systems to get out of here.\n");
        this.aGui.println( "Type 'help' if you need assistance navigating the ship.\n" );
        printLocationInfo();
        if (this.aPlayer.getCurrentRoom().getImageName() != null)
            this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
    } // printWelcome()

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        this.aControlRoom = new Room("in the control room, all the electronic systems and\nconsoles are turned off.","Control_Room.png");
        Room vObservatory = new Room("in the observatory, you gaze through large windows and see\na planet, growing bigger and bigger as the time passes","Observatory.png");
                
        Room vLab = new Room("in the laboratory, there's various tools and experimental objects\nlying on tables, hinting at recent research activity.","Lab.png");
        Room vCanteen = new Room("in the canteen, empty tables and chairs give the impression\nof a recently abandoned space. You can also find food here.","Canteen.png");
        Room vQuarters = new Room("in the crew quarters and find yourself facing three rooms.","Quarters.png");
        Room vQ1 = new Room("in your quarters, you awaken surrounded by sparse, familiar\nbelongings and a sense of urgency to uncover what happened.","Q1.png");
        Room vQ2 = new Room("in the second room, you notice scattered\nclothes and signs of a hasty exit.","Q2.png"); //nathan
        Room vQ3 = new Room("in the third room, there is an eerie silence, as if frozen in time.","Q3.png"); //ana
        
        Room vStorage = new Room("in the storage room, shelves are stacked with supplies\nand components that may be useful for repairs.","Storage.png");
        this.aReactors = new Room("in the reactors room, you hear loud clicking noises,\nand the machinery looks unstable and damaged.","Reactors.png");
        this.aGenerators = new Room("in the generator room, equipment for energy generation\nlies dormant, waiting for reactivation.","Generators.png");
        
        Room vLowerDeck = new Room("on the lower deck, the dim lighting and narrow passages\ncreate a tense atmosphere.","Lower_Deck.png");
        Room vMainDeck = new Room("on the main deck, you stand at the crossroads of several\nhallways leading to different parts of the ship.","Main_Deck.png");
        Room vUpperDeck = new Room("on the upper deck, you find the entrance to key rooms,\ncrucial for regaining control of the ship.","Upper_Deck.png");

        
        
        this.aControlRoom.setExits("south",vUpperDeck);
        vObservatory.setExits("north",vUpperDeck);
        
        vLab.setExits("south", vMainDeck);
        //vCanteen.setExits("northwest", vQuarters); (trap door)
        vCanteen.setExits("north", vMainDeck);   
        vQuarters.setExits("southeast", vCanteen);
        vQuarters.setExits("east", vMainDeck);   
        vQuarters.setExits("room1", vQ1);   
        vQuarters.setExits("room2", vQ2);   
        vQuarters.setExits("room3", vQ3);   
        vQ1.setExits("out", vQuarters);                         
        vQ2.setExits("out", vQuarters);                         
        vQ3.setExits("out", vQuarters);             

        vStorage.setExits("east", vLowerDeck);        
        this.aReactors.setExits("north", vLowerDeck);           
        this.aGenerators.setExits("west", vLowerDeck);
        
        vLowerDeck.setExits("east",this.aGenerators);
        vLowerDeck.setExits("south",this.aReactors);
        vLowerDeck.setExits("west",vStorage);
        vLowerDeck.setExits("upstairs",vMainDeck);
        
        vMainDeck.setExits("north",vLab);
        vMainDeck.setExits("south",vCanteen);
        vMainDeck.setExits("west",vQuarters);
        vMainDeck.setExits("downstairs",vLowerDeck);
        vMainDeck.setExits("upstairs",vUpperDeck);
        
        vUpperDeck.setExits("north",this.aControlRoom);
        vUpperDeck.setExits("south",vObservatory);
        vUpperDeck.setExits("downstairs",vMainDeck);
        
        Item vFuse = new Item("fuse","a small metallic fuse, it could help reactivating the generators.", 1.0);
        vStorage.addItem(vFuse);
        
        Item vFlashlight = new Item("flashlight","a flashlight, the batteries are still working", 2.0);
        vQ1.addItem(vFlashlight);
        
        Item vFuel = new Item("fuel","a red gas canister, filled with gasoline. ", 6.0);
        vStorage.addItem(vFuel);
        
        Item vMagicPill = new Item("boost_pill", "a pill of nano-engineered proteins.\nEnhances your strength instantly when consumed.", 0.5);
        vLab.addItem(vMagicPill);
        
        Item vChips = new Item("chips","a pack of Orbitos, some crispy chili-flavoured chips. ", 2.0);
        vCanteen.addItem(vChips);
        
        Item vWatch = new Item("watch","a high-tech gravimetric watch designed for space missions.\nDisplays the time remaining before the ship crashes into the planet.\n",2.0);
        vObservatory.addItem(vWatch);
        
        Item vBeamer = new Item("beamer", "a sophisticated device capable of instant teleportation\nto a memorized location.", 3.0);
        vLab.addItem(vBeamer);
        
        Item vSensor = new Item("sensor", "a high-tech device capable of detecting faults in\nelectronic systems. The reactors won't run without it",2.0);
        vLab.addItem(vSensor);
        
        Item vToolBox = new Item("toolbox", "a toolbox containing various tools like screwdrivers\nand small wrenches. It’s a must-have for any serious repair work.", 4.5);
        this.aControlRoom.addItem(vToolBox);

        this.aPlayer = new Player("Player1",vQ1);
    } // createRooms()

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     * 
     * @param pCommandLine Command line to be processed.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "\n> " + pCommandLine );
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "quit" ) ) 
        {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
        else if (vCommandWord.equals("eat"))
        {
            if ( !vCommand.hasSecondWord() )
                this.aGui.println( "Eat what ?" );
            else
                this.aPlayer.eat(vCommand.getSecondWord());
        }
        else if (vCommandWord.equals("look"))
            this.look(vCommand);
        else if (vCommandWord.equals("back"))
        {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Back what ?" );
            else
                this.goBack();
        }
        else if (vCommandWord.equals("test"))
            this.test(vCommand.getSecondWord());
        else if (vCommandWord.equals("take"))
        {
            if ( !vCommand.hasSecondWord() )
                this.aGui.println( "Take what ?" );
            else
                this.aPlayer.take(vCommand.getSecondWord());
        }
        else if (vCommandWord.equals("drop"))
        {
            if ( !vCommand.hasSecondWord() )
                this.aGui.println( "Drop what ?" );
            else
                this.aPlayer.drop(vCommand.getSecondWord());
        }
        else if (vCommandWord.equals("inventory"))
        {
            this.printInventory();
        }
        else if (vCommandWord.equals("charge"))
        {
            if ( !vCommand.hasSecondWord() || !vCommand.getSecondWord().equals("beamer"))
                this.aGui.println( "Charge what ?" );
            else
                this.aPlayer.chargeBeamer(vCommand.getSecondWord());
        }
        else if (vCommandWord.equals("fire"))
        {
            if ( !vCommand.hasSecondWord() || !vCommand.getSecondWord().equals("beamer"))
                this.aGui.println( "Fire what ?" );
            else
                this.aPlayer.fireBeamer(vCommand.getSecondWord());
        }
        else if(vCommandWord.equals("use"))
        {
            if ( !vCommand.hasSecondWord() )
            {
                this.aGui.println( "Use what ?" );
            }
            else
            {
                use(vCommand.getSecondWord());
            }
        }
        else if(vCommandWord.equals("win"))
        {
            if ( vCommand.hasSecondWord() )
            {
                this.aGui.println( "Win what ?" );
            }
            else
            {
                winGame();
            }
        }
        else
        {
            this.aGui.println("Erreur du programmeur : commande non reconnue !");
        }
    } // interpretCommand()
    
    /**
     * Prints the player's current location and available exits.
     */
    private void printLocationInfo()
    {
        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
    } // printLocationInfo()
    
    /**
     * Prints the player's inventory and its weight.
     */
    private void printInventory()
    {
        this.aGui.println(this.aPlayer.getInvString());
    }
    
    /** 
     * Makes the player look around or at a specific item.
     * 
     * @param pC The command entered by the player, containing an 
     *           optional second word representing the name of the item to look at.
     */
    private void look(final Command pC)
    {
        if (!pC.hasSecondWord()) 
        {
            printLocationInfo();
            return;
        }
        
        String vItemName = pC.getSecondWord();
        Item vItem = this.aPlayer.getCurrentRoom().getItem(vItemName);
        
        if (vItem != null)
        {
            this.aGui.println(vItem.getItemString());
        }
        else
        {
            this.aGui.println("There is no '" + vItemName + "' here.");
        }
    } // look()
        
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        this.aGui.println("You woke up alone in a big spaceship.\nThe place is totally deserted.");
        this.aGui.println("You must gather the information and components needed to repair critical systems.");
        this.aGui.println("\nAvailable commands : "+this.aParser.getCommandString());
    } // printHelp() 

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom( final Command pCommand ) 
    {
        if ( !pCommand.hasSecondWord() ) {
            // if there is no second word, we don't know where to go...
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null )
            this.aGui.println( "There is no door!" );
        else {
            this.aPlayer.goRoom(vNextRoom);
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    } // goRoom()
    
    /** 
     * Makes the player go back to previous room.
     * Can be used infinitely, until the player is back to the first visited room.
     */
    private void goBack()
    {
        if (this.aPlayer.aVisitedRooms.empty()) 
        {
            this.aGui.println("You cannot go back from here! No previous room.");
            return;
        }
        else
        {
            this.aPlayer.goBack();
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
    } // goBack()
    
    /**
     * When called, the player uses the specified item.
     * Only works if the player wants to use a "usable" item, in the right room.
     * 
     * @param pItemName The name of the item to be used.
     */
    public void use(final String pItemName)
    {
        switch(pItemName)
        {
            case "fuse" :
                if (this.aPlayer.getCurrentRoom().equals(this.aGenerators))
                {
                    if (this.aPlayer.use(pItemName) == true)
                    {
                    this.aUsedItems.add(pItemName);
                    this.aGui.println("Used " + pItemName + ". All the electronic systems came back to life !");
                    this.aGenerators.updateDescription("in the generator room, the equipment is now operational\nand full of electricity.");
                    }
                }
                else
                {
                    this.aGui.println("You cannot use this here.");
                }
                break;
            
            case "fuel" :
                if (this.aPlayer.getCurrentRoom().equals(this.aReactors))
                {
                    if (this.aPlayer.use(pItemName) == true)
                    {
                    this.aUsedItems.add(pItemName);
                    this.aGui.println("Used " + pItemName + ". The fuel tank is now full.");
                    }
                }
                else
                {
                    this.aGui.println("You cannot use this here.");
                }
                break;
            
            case "sensor" :
                if (this.aPlayer.getCurrentRoom().equals(this.aReactors))
                {
                    if (this.aPlayer.use(pItemName) == true)
                    {
                    this.aUsedItems.add(pItemName);
                    this.aGui.println("Used " + pItemName + ". The sensor is placed.");
                    }
                }
                else
                {
                    this.aGui.println("You cannot use this here.");
                }
                break;
                
            default :
                this.aPlayer.use(pItemName);
        }
                
        if (this.aUsedItems.containsAll(this.aWinItems))
        {
            this.aReactors.updateDescription("in the reactors room, the machinery is now running\nand the clicking noises are gone.");
            this.aGui.println("\nEverything seems to be working again !");
            this.aGui.println("Head up quickly to the control room and\nregain control of the ship.");
        }
    } // use()
    
    private void winGame()
    {
        if (this.aPlayer.getCurrentRoom().equals(this.aControlRoom) && this.aUsedItems.containsAll(this.aWinItems))
        {
            this.aGui.println("\nCongratulations! You've repaired the critical\nsystems and saved the ship!");
            this.aGui.showImage("win.png");
            endGame();
        }
    }
    
    /**
     * Executes a series of commands from a specified text file.
     * The commands are read line by line from the file and 
     * passed to interpretCommand() for processing.
     *
     * @param  pFileName The name of the file containing the commands to test.
     *                   If the file extension is not ".txt", it will be appended automatically.
     */
    private void test(String pFileName)
    {
        if (pFileName == null) 
        {
            this.aGui.println("Please specify a filename to test.");
            return;
        }
    
        if (!pFileName.endsWith(".txt"))
        {
            pFileName += ".txt";
        }
        
        Scanner vSc;
        try
        {
            vSc = new Scanner(new File(pFileName));
            while(vSc.hasNextLine())
            {
                String vLine = vSc.nextLine();
                this.interpretCommand(vLine);
            }
        }
        catch (final FileNotFoundException pFNFE)
        {
            this.aGui.println("File not found.");
        }
    } // test()
    
    /** 
     * Ends the game when called.
     */
    private void endGame()
    {
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false );
    } // endGame()

}
