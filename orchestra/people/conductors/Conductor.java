package people.conductors;

import music.Composition;
import music.MusicScore;
import orchestra.Orchestra;
import people.Person;
import people.musicians.Musician;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Iterator;

public class Conductor extends Person {
    public ArrayList<Musician> musiciansPlayNotes = new ArrayList<>();
    public SoundSystem soundSystem;
    Orchestra orchestra = new Orchestra();

    /**
     * create a constructor
     * @param name
     * @param soundSystem
     */
    public Conductor(String name, SoundSystem soundSystem){
        super(name);
        this.soundSystem = soundSystem;
    }

    /**
     * Add musician into musiciansPlayNotes which represents a collection of musicians who will play the notes
     * @param musician
     */
    public void registerMusician(Musician musician){
        try {
            musiciansPlayNotes.add(musician);
        }catch(NullPointerException e){
            System.out.println("Wrong");
        }
    }

    /**
     * Arrange the play of a composition
     * @param composition a composition
     * @throws MidiUnavailableException when a requested MIDI component cannot be opened or created because it is unavailable
     */
    public void playComposition(Composition composition) throws MidiUnavailableException {

        MusicScore[] scores = composition.getScores();
        // Traversing musiciansPlayNotes and scores
        for (MusicScore score: scores)
            for (Musician musician : musiciansPlayNotes) {

                // Make musicians play their own musicscore (assign scores to musicians)

                if (!orchestra.isSeated(musician)&&musician.getInstrumentID() == score.getInstrumentID()) {
                    orchestra.sitDown(musician);
                    musician.readScore(score.getNotes(), score.isSoft());
                    break;
                }
            }
        for (int i = 0; i < composition.getLength(); i++) {
            orchestra.playNextNote();
            // Stop
            try {
                Thread.sleep(composition.getNoteLength());
            } catch (InterruptedException e) {
            }
        }
        soundSystem.init();
        for (Musician musician: musiciansPlayNotes){
            orchestra.standUp(musician);
        }
    }

    /** Additional method, make each musician has 50 percentage chance to leave the band
     *
     * @param m a musician
     */
    public void leave(Musician m){
        musiciansPlayNotes.remove(m);
    }
}
