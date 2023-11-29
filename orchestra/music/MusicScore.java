package music;

public class MusicScore {
    public String instrumentName = "";
    public int[] notes;
    public boolean soft;

    /** Create constructor
     *
     * @param instrumentName the name of instrument
     * @param notes the array of notes
     * @param soft a boolean to judge whether or not the music should be played softly
     */
    public MusicScore(String instrumentName, int[] notes, boolean soft){
        this.soft = soft;
        this.notes = notes;
        this.instrumentName = instrumentName;
    }

    /** Return the instrument ID
     *
     * @return
     */
    public int getInstrumentID(){
        if (instrumentName.equals("Cello")) {
            return 43;
        } else if (instrumentName.equals("Piano")){
            return 1;
        }
            return 41;
    }

    /** Return the array of notes
     *
     * @return
     */
    public int [] getNotes(){
        return notes;
    }

    /** Return whether or not the music should be played softly
     *
     * @return
     */
    public boolean isSoft(){
        return soft;
    }

}
