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
    
    public GameSpecs(String passageLengthString) {
        this.passageLength = allocatePassageLength(passageLengthString);
    }

    public int allocatePassageLength (String passLenStr) {
        if (passLenStr.equals("short"))
            return 20;
        else if (passLenStr.equals("medium"))
            return 40;
        else if (passLenStr.equals("long"))
            return 80;
        else if (passLenStr.equals("custom"))
            return 0;  // needs custom input
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
    
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
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
}
