package people.musicians;

import javax.sound.midi.MidiUnavailableException;

public interface Musician {
    /**
     * Create the abstract methods setSeat, readScore, playNextNote following the coursework specification
     *
     * @param seat The seat of each musician (Following the coursework specification)
     */
    public abstract void setSeat(int seat);
    public abstract void readScore(int[] notes, boolean soft);
    public abstract void playNextNote() throws MidiUnavailableException;
    public int getInstrumentID();
}
