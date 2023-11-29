package orchestra;

import people.musicians.Musician;

import javax.sound.midi.MidiUnavailableException;
import java.util.*;

public class Orchestra {
    public HashMap<Integer, Musician> seating;
    // Additional hashmap to record seated people
    public HashMap<Musician, Integer> seatedPeople;
    // Additional hashmap to record empty seats
    public HashSet<Integer> seats;
    public Iterator<Integer> it;
    public boolean isSeated;

    public Orchestra() {
        this.seating = new HashMap<>();
        this.isSeated = true;
        this.seatedPeople = new HashMap<>();
        this.seats = new HashSet<>();
        for (int i=0; i < 16; i++ ){
            seats.add(i);
        }
    }

    /**
     * Delete the musicians who stand up from the hashmap
     * @param musician a musician
     */
    public void standUp(Musician musician) {
        if (!seating.containsValue(musician)) return;
        // Get musicians who stand up
        Integer seat = seatedPeople.get(musician);
        // Delete musicians who stand up from the hashmap of seated people
        seatedPeople.remove(musician);
        seating.remove(seat);
        // Add the seats back to the hashmap of empty seats
        seats.add(seat);
        musician.setSeat(-1);
    }

    /**
     *
     * @throws MidiUnavailableException when a requested MIDI component cannot be opened or created because it is unavailable
     */
    public void playNextNote() throws MidiUnavailableException {
        Set<Integer> seatingKeySet = seating.keySet();
        // Make seated people playing the next note
        for(Integer a : seatingKeySet){
                seating.get(a).playNextNote();
        }
    }

    public int sitDown(Musician musician) {
        // If musician has sit down
        if (seatedPeople.containsKey(musician)) return 2;
        // Set seat for musicians and remove the seats from the hashmap of empty seats
        // If seats is empty means there is no empty seat
        if (seats.isEmpty()) return 1;
        Iterator<Integer> it = seats.iterator();
        int seat = it.next();
        seating.put(seat, musician);
        seatedPeople.put(musician, seat);
        musician.setSeat(seat);
        it.remove();
        return 0;
    }

    public boolean isSeated(Musician musician) {
        return seatedPeople.containsKey(musician);
    }
}
