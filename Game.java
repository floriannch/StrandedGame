/**
 *  Class Game - Main class, creates the necessary implementation objects and starts the game off.
 * 
 *  To play this game, create an instance of this class.
 * 
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2.0 (Jan 2003) DB edited (2019)
 */

public class Game
{
    private UserInterface aGui;
    private GameEngine aEngine;

    /**
     * Create the game and initialise its internal map. 
     * Create the inerface and link to it.
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    }
}
