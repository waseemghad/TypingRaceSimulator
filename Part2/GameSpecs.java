package part2;

/**
 * Stores game specifications chosen by the user
 * 
 * @author Waseem Ghadari
 * @version 1.0
 */
public class GameSpecs {
    private int passageLength;
    private int seatCount;
    private boolean Autocorrect;
    private boolean caffieneMode;
    private boolean nightShift;
    private int[] chosenCharacter;
    
    public GameSpecs() {
        this.passageLength = 40;    // default value
        this.seatCount = 6;     // maximum default value (temporary)
        this.Autocorrect = false;    // default value
        this.caffieneMode = false;    // default value
        this.nightShift = false;    // default value
    }

    public int controlledSetPassageLength (String passLenStr) {
        if (passLenStr.equals("short"))
            return 20;
        else if (passLenStr.equals("medium"))
            return 40;
        else if (passLenStr.equals("long"))
            return 80;
        else if (passLenStr.equals("custom"))
            return -1;  // needs custom input
        else
            throw new IllegalArgumentException("Invalid passage length: " + passLenStr);
    }
    
    public int getPassageLength() {
        return passageLength;
    }
    
    public void setPassageLength(int passageLength) {
        this.passageLength = passageLength;
    }
    
    public int getSeatCount() {
        return seatCount;
    }
    
    /**
     * Sets the seat count and declares array of characters
     * correspoonding to the seat count
     * 
     * @param seatCount the number of typists playing
     */
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
        chosenCharacter = new int[seatCount];
    }
    
    public boolean isAutocorrect() {
        return Autocorrect;
    }
    
    public void setAutocorrect(boolean autocorrect) {
        this.Autocorrect = autocorrect;
    }
    
    public boolean isCaffieneMode() {
        return caffieneMode;
    }
    
    public void setCaffieneMode(boolean caffieneMode) {
        this.caffieneMode = caffieneMode;
    }
    
    public boolean isNightShift() {
        return nightShift;
    }
    
    public void setNightShift(boolean nightShift) {
        this.nightShift = nightShift;
    }

    /**
     * Sets value for a specific seat's character preset
     * The value of characters range from 0 to 4,
     * with -1 being value for no character
     * 
     * @param seatIndex the seat/typist index
     * @param characterPreset the value of the chosen character preset
     */
    public void setChosenCharacter(int seatIndex, int characterPreset) {
        this.chosenCharacter[seatIndex] = characterPreset;
    }
    
    /**
     * Returns the chosen character preset for a specific seat
     * 
     * @param seatIndex the seat/typist index
     * @return the character preset for that seat
     */
    public int getChosenCharacter(int seatIndex) {
        return chosenCharacter[seatIndex];
    }
        
    /**
     * Sets default value (-1) for the chosen preset
     * of a typist seat, indicating no character has been chosen yet
     * 
     * @param seatIndex the seat/typist index
     */
    public void nullifyCharacterPreset(int seatIndex) {
        this.chosenCharacter[seatIndex] = -1;
    }
}
