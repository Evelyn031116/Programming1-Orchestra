package people.musicians;

import people.Person;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Implement the methods in Musician interface and create member variables following the coursework specification
 */
public class Violinist extends Person implements Musician{

    public int instrumentID = 41;
    public List<Integer> notes;
    public Iterator<Integer> nextNote;
    public Integer seat;
    // Set the volume when playing softly
    public int softVolume = 50;
    // Set the volume when playing loudly
    public int loudVolume = 100;
    // Judge whether be played softly or not
    public boolean soft;
    public SoundSystem soundSystem;
    public int loudness;
    public Violinist(String name) {
        super(name);
    }
    /**
     * Initialize seat and set instrumentID
     * @param seat The seat of each musician (Following the coursework specification)
     */
    @Override
    public void setSeat(int seat) {
        this.seat = seat;
    }
    /**
     *
     * @throws MidiUnavailableException when a requested MIDI component cannot be opened or created because it is unavailable
     */
    @Override
    public void playNextNote() throws MidiUnavailableException {
        soundSystem.setInstrument(seat, instrumentID);
        if(nextNote == null){
            return;
        }
        if (nextNote.hasNext()){
            int no = nextNote.next();
            this.soundSystem.playNote(this.seat, no, this.loudness);
        }
    }

    @Override
    public int getInstrumentID() {
        return instrumentID;
    }
    /**
     *
     * @param name inherited the name of cellist
     * @param soundSystem a SoundSystem
     */
    public Violinist(String name,SoundSystem soundSystem){
        super(name);
        this.soundSystem = soundSystem;
        this.notes = new ArrayList<>();
    }
    public Violinist(String name,SoundSystem soundSystem,int seat){
        super(name);
        this.name = name;
        this.seat = seat;
        this.soundSystem = soundSystem;
        this.notes = new ArrayList<>();
    }
    /**
     *
     * @param no a new int[] which is the collection of 'no's
     * @param soft judge whether be played softly or not
     */
    public void readScore(int[] no, boolean soft){
        // Traversing no to add each note into notes
        for (int i : no){
            notes.add(i);
        }
        // Judge whether be played softly or not and set the volume according to the result
        if (soft) this.loudness = this.softVolume;
        else this.loudness = this.loudVolume;
        nextNote = notes.iterator();
    }


}
