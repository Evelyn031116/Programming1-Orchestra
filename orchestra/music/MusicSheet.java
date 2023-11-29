package music;

import java.util.ArrayList;
import java.util.List;

public class MusicSheet implements Composition{
    public String name;
    public String tempo;
    public int length;
    public List<MusicScore> scores;

    /**
     * Create a constructor
     * @param name
     * @param tempo
     * @param length
     */
    public MusicSheet(String name, String tempo, int length) {
        this.name = name;
        this.tempo = tempo;
        this.length = length;
        this.scores = new ArrayList<>();
    }


    public int getNoteLength() {
        return switch (tempo) {
            case "Larghissimo" -> 1500;
            case "Lento" -> 1000;
            case "Andante" -> 500;
            case "Moderato" -> 300;
            case "Allegro" -> 175;
            case "Presto" -> 150;
            default -> 0;
        };
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMIDI(String str){
        int MIDI = 0;
        // There are two lengths of strings, one with a length of two which just like a white key of piano, another one with a length of three just like a black key
        if (str.length() < 4) {
            // Calculate the position of the first bit of the string (the letter) relative to A to get the position in the group
            int index = (str.charAt(0) - 'A' + 5) % 7;
            int position;
            // When string contains '#' or 'b'
            if (str.length() == 3) {
                // When string contains '#'
                if (str.charAt(1) == '#') {
                    // The specific algorithm is in the readme.txt
                    position = index < 2 ? (2 * index + 1) : (2 * index);
                } else {
                    // When string contains 'b'
                    position = index < 3 ? (2 * index - 1) : (2 * index - 2);
                }
            } else
                position = index < 2 ? (2 * index) : (2 * index - 1);
            // Assume C1-B1 as the first group, calculate the group number in which the note is located and use this group number to multiply 12 and plus 24 which is the MIDI of C1
            MIDI = position + (str.charAt(str.length() - 1) - '1') * 12 + 24;
        }
        return MIDI;
    }

    /**
     * add scores into list
     * @param instrumentName
     * @param notes
     * @param soft
     */
    public void addScore(String instrumentName, List<String> notes, boolean soft){
        int[] noteNumbers = new int[notes.size()];
        for(int i = 0; i < notes.size(); i++){
            noteNumbers[i] = this.getMIDI(notes.get(i));
        }
        this.scores.add(new MusicScore(instrumentName, noteNumbers, soft));
    }
    public int getLength(){
        return length;
    }
    public MusicScore[] getScores(){
        MusicScore[] scores1 = new MusicScore[scores.size()];
        scores.toArray(scores1);
        return scores1;
    }
}