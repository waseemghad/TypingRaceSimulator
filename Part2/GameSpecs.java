package part2;

/**
 * Stores game specifications chosen by the user
 * 
 * @author Waseem Ghadari
 * @version 1.0
 */
public class GameSpecs {
    private static final double DEFAULT_MISTYPE_BASE_CHANCE = 0.3;
    private static final int DEFAULT_SLIDE_BACK_AMOUNT = 2;
    private static final int DEFAULT_BURNOUT_DURATION = 5;
    private static final double DEFAULT_BURNOUT_BASE_CHANCE = 0.3;
    private static final double DEFAULT_BURNOUT_ACCURACY_DECREASE = 0.005;
    private static final double DEFAULT_WINNER_ACCURACY_INCREASE = 0.1;
    private static final double[] DEFAULT_PRESET_ACCURACIES = {0.70, 0.90, 0.30, 0.60, 0.40};

    private int passageLength;
    private int seatCount;
    private boolean Autocorrect;
    private boolean caffieneMode;
    private boolean nightShift;
    private int[] chosenTypists;
    private String[] characterNames;
    private char[] characterSymbols;
    private double mistypeBaseChance;
    private int slideBackAmount;
    private int burnoutDuration;
    private double burnoutBaseChance;
    private double burnoutAccuracyDecrease;
    private double winnerAccuracyIncrease;
    private double[] presetAccuracies;
    
    public GameSpecs() {
        this.passageLength = 40;    // default value
        this.seatCount = 6;     // maximum default value (temporary)
        this.Autocorrect = false;    // default value
        this.caffieneMode = false;    // default value
        this.nightShift = false;    // default value
        this.mistypeBaseChance = DEFAULT_MISTYPE_BASE_CHANCE;
        this.slideBackAmount = DEFAULT_SLIDE_BACK_AMOUNT;
        this.burnoutDuration = DEFAULT_BURNOUT_DURATION;
        this.burnoutBaseChance = DEFAULT_BURNOUT_BASE_CHANCE;
        this.burnoutAccuracyDecrease = DEFAULT_BURNOUT_ACCURACY_DECREASE;
        this.winnerAccuracyIncrease = DEFAULT_WINNER_ACCURACY_INCREASE;
        this.presetAccuracies = DEFAULT_PRESET_ACCURACIES.clone();
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
     * Sets the seat count and declares array 
     * containing values that correspond to the
     * Typist preset that each player is to choose
     * 
     * @param seatCount the number of typists playing
     * @param chosenTypists array of numbers corr. to the typist each player chose
     */
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
        this.chosenTypists = new int[seatCount];
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
     * Sets value for a specific player's Typist preset
     * The value of Typists range from 0 to 4 incl.,
     * with -1 being value for no Typist
     * 
     * @param seatIndex the seat/player index
     * @param chosenTypists array of numbers corr. to the typist each player chose
     */
    public void setTypist(int seatIndex, int Typist) {
        this.chosenTypists[seatIndex] = Typist;
    }
    
    /**
     * Returns the chosen Typist for a specific seat
     * 
     * @param seatIndex the seat/player index
     * @param chosenTypists array of numbers corr. to the typist each player chose
     * @return the Typist corresponding to the player
     */
    public int getTypist(int seatIndex) {
        return chosenTypists[seatIndex];
    }
        
    /**
     * Sets default value (-1) for the chosen preset
     * of a typist seat, indicating no character has been chosen yet
     * 
     * @param seatIndex the seat/typist index
     * @param chosenTypists array of numbers corr. to the typist each player chose
     */
    public void nullifyCharacterPreset(int seatIndex) {
        this.chosenTypists[seatIndex] = -1;
    }

    public double getMistypeBaseChance() {
        return mistypeBaseChance;
    }

    public void setMistypeBaseChance(double mistypeBaseChance) {
        this.mistypeBaseChance = mistypeBaseChance;
    }

    public int getSlideBackAmount() {
        return slideBackAmount;
    }

    public void setSlideBackAmount(int slideBackAmount) {
        this.slideBackAmount = slideBackAmount;
    }

    public int getBurnoutDuration() {
        return burnoutDuration;
    }

    public void setBurnoutDuration(int burnoutDuration) {
        this.burnoutDuration = burnoutDuration;
    }

    public double getBurnoutBaseChance() {
        return burnoutBaseChance;
    }

    public void setBurnoutBaseChance(double burnoutBaseChance) {
        this.burnoutBaseChance = burnoutBaseChance;
    }

    public double getBurnoutAccuracyDecrease() {
        return burnoutAccuracyDecrease;
    }

    public void setBurnoutAccuracyDecrease(double burnoutAccuracyDecrease) {
        this.burnoutAccuracyDecrease = burnoutAccuracyDecrease;
    }

    public double getWinnerAccuracyIncrease() {
        return winnerAccuracyIncrease;
    }

    public void setWinnerAccuracyIncrease(double winnerAccuracyIncrease) {
        this.winnerAccuracyIncrease = winnerAccuracyIncrease;
    }

    public double getPresetAccuracy(int index) {
        return presetAccuracies[index];
    }

    public void setPresetAccuracies(double[] presetAccuracies) {
        this.presetAccuracies = presetAccuracies.clone();
    }

    public String getCharacterName(int index) {
        return characterNames[index];
    }

    public void declareCharacterNamesArray(int seatCount) {
        this.characterNames = new String[seatCount];
    }

    public void declareCharacterSymbolsArray(int seatCount) {
        this.characterSymbols = new char[seatCount];
    }

    public void setCharacterName(int seatIndex, String name) {
        this.characterNames[seatIndex] = name;
    }

    public void nullifyCharacterName(int seatIndex) {
        this.characterNames[seatIndex] = null;
    }

    public char getCharacterSymbol(int index) {
        return characterSymbols[index];
    }

    public void setCharacterSymbol(int seatIndex, char symbol) {
        this.characterSymbols[seatIndex] = symbol;
    }

    public void nullifyCharacterSymbol(int seatIndex) {
        this.characterSymbols[seatIndex] = '\0';
    }

    public double roundAccuracy(double accuracy) {
        return Math.round(accuracy * 100.0) / 100.0;
    }
}
