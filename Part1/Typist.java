/**
 * Write a description of class Typist here.
 *
 * Starter code generously abandoned by Ty Posaurus, your predecessor,
 * who typed with two fingers and considered that "good enough".
 * He left a sticky note: "the slide-back thing is optional probably".
 * It is not optional. Good luck.
 *
 * @author Waseem Hemat Ghadari
 * @version 1.1
 */
public class Typist
{
    private String name;
    private char symbol;
    private int progress;
    private boolean burnout = false; // intialised
    private int burnoutTurnsRemaining;
    private double accuracy;
    
    private boolean mistype = false; // intialised

    // Constructor of class Typist
    /**
     * Constructor for objects of class Typist.
     * Creates a new typist with a given symbol, name, and accuracy rating.
     *
     * @param typistSymbol  a single Unicode character representing this typist (e.g. '①', '②', '③')
     * @param typistName    the name of the typist (e.g. "TURBOFINGERS")
     * @param typistAccuracy the typist's accuracy rating, between 0.0 and 1.0
     */
    protected Typist(char typistSymbol, String typistName, double typistAccuracy)
    {
        setSymbol(typistSymbol);
        this.name = typistName;
        setAccuracy(typistAccuracy);
    }


    // Methods of class Typist

    /**
     * Sets this typist into a burnout state for a given number of turns.
     * A burnt-out typist cannot type until their burnout has worn off.
     *
     * @param turns the number of turns the burnout will last
     */
    protected void burnOut(int turns)
    {
        if (turns > 0)
        {
            burnout = true;
            burnoutTurnsRemaining = turns;
        }
    }
    
    /**
     * Reduces the remaining burnout counter by one turn.
     * When the counter reaches zero, the typist recovers automatically.
     * Has no effect if the typist is not currently burnt out.
     */
    protected void recoverFromBurnout()
    {
        if (burnout)
        {
            burnoutTurnsRemaining--;
        }
        if (burnoutTurnsRemaining == 0)
        {
            burnout = false;
        }
    }

    /**
     * Returns the typist's accuracy rating.
     *
     * @return accuracy as a double between 0.0 and 1.0
     */
    protected double getAccuracy()
    {
        return this.accuracy;
    }

    /**
     * Returns the typist's current progress through the passage.
     * Progress is measured in characters typed correctly so far.
     * Note: this value can decrease if the typist mistypes.
     *
     * @return progress as a non-negative integer
     */
    protected int getProgress()
    {
        return this.progress;
    }

    /**
     * Returns the name of the typist.
     *
     * @return the typist's name as a String
     */
    protected String getName()
    {
        return this.name;
    }

    /**
     * Returns the character symbol used to represent this typist.
     *
     * @return the typist's symbol as a char
     */
    protected boolean isMistype()
    {
        return this.mistype;
    }

    /**
     * Returns the character symbol used to represent this typist.
     *
     * @return the typist's symbol as a char
     */
    protected void setMistype(boolean status)
    {
        mistype = status;
    }

    /**
     * Returns the character symbol used to represent this typist.
     *
     * @return the typist's symbol as a char
     */
    protected char getSymbol()
    {
        return this.symbol;
    }

    /**
     * Returns the number of turns of burnout remaining.
     * Returns 0 if the typist is not currently burnt out.
     *
     * @return burnout turns remaining as a non-negative integer
     */
    protected int getBurnoutTurnsRemaining()
    {
        return burnoutTurnsRemaining;
    }

    /**
     * Resets the typist to their initial state, ready for a new race.
     * Progress returns to zero, burnout is cleared entirely.
     */
    protected void resetToStart()
    {
        progress = 0;
        burnout = false;
        burnoutTurnsRemaining = 0;
    }

    /**
     * Returns true if this typist is currently burnt out, false otherwise.
     *
     * @return true if burnt out
     */
    protected boolean isBurntOut()
    {
        return burnout;
    }

    /**
     * Advances the typist forward by one character along the passage.
     * Should only be called when the typist is not burnt out.
     */
    protected void typeCharacter()
    {
        if (! burnout)
            progress++;
    }

    /**
     * Moves the typist backwards by a given number of characters (a mistype).
     * Progress cannot go below zero — the typist cannot slide off the start.
     *
     * @param amount the number of characters to slide back (must be positive)
     */
    protected void slideBack(int amount)
    {
        if (progress >= amount)
            progress -= amount;
        else
            progress = 0;
    }

    /**
     * Sets the accuracy rating of the typist.
     * Values below 0.0 should be set to 0.0; values above 1.0 should be set to 1.0.
     *
     * @param newAccuracy the new accuracy rating
     */
    protected void setAccuracy(double newAccuracy)
    {
        if (newAccuracy < 0.0)
            this.accuracy = 0.0;
        else if (newAccuracy > 1.0)
            this.accuracy = 1.0;
        else
            this.accuracy = newAccuracy;
    }

    /**
     * Sets the symbol used to represent this typist.
     *
     * @param newSymbol the new symbol character
     */
    protected void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }

}