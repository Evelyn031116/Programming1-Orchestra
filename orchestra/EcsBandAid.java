import music.Composition;
import music.MusicScore;
import music.MusicSheet;
import people.Person;
import people.conductors.Conductor;
import people.musicians.Cellist;
import people.musicians.Musician;
import people.musicians.Pianist;
import people.musicians.Violinist;
import utils.SoundSystem;

import javax.sound.midi.MidiUnavailableException;
import java.io.*;
import java.util.*;

public class EcsBandAid {
    public HashSet<Musician> allM = new HashSet<>();
    public ArrayList<Composition> allC = new ArrayList<>();
    public ArrayList<Musician> invitedM = new ArrayList<>();
    public Iterator<Musician> leave;
    private utils.SoundSystem SoundSystem;
    public Conductor conductor = new Conductor("str", SoundSystem);
    public BufferedReader mFin;
    public BufferedReader cFin;
    public Musician musician;

    /**
     *
     * @param soundSystem
     * @param allM collection of all musicians
     * @param allC collection of all compositions
     * @throws MidiUnavailableException when a requested MIDI component cannot be opened or created because it is unavailable
     */
    public EcsBandAid(SoundSystem soundSystem, HashSet<Musician> allM, ArrayList<Composition> allC) throws MidiUnavailableException {
        this.conductor = new Conductor("str", soundSystem);
        this.allM = allM;
        this.allC = allC;
        this.SoundSystem = new SoundSystem();
    }

    /**
     * invite musicians to play for a year
     * @throws MidiUnavailableException when a requested MIDI component cannot be opened or created because it is unavailable
     */

    public void performForAYear() throws MidiUnavailableException {
        int size = allC.size();
        leave = invitedM.iterator();
        // List of three chosen compositions
        ArrayList<Composition> chosenC = new ArrayList<Composition>();
        // Randomly choose three compositions
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            int index = random.nextInt(size);
            chosenC.add(allC.get(index));
        }
        for (Musician musician : allM) {
            // Register musicians
            conductor.registerMusician(musician);
            // Add invited musicians into list
            invitedM.add(musician);
        }
        // Play chosen compositions
        for (Composition composition : chosenC) {
            conductor.playComposition(composition);
        }
        // Use leave method, let every musician has 50 percentage chance to leave the band
        Iterator<Musician> it = invitedM.iterator();
        while(it.hasNext()){
            Musician m = it.next();
            int number = (int)(Math.random() * 100);
            if (number < 50) {
                // Remove left musicians
                conductor.leave(m);
                System.out.println(((Person) m).getName() + " left");
                it.remove();

            }
        }
    }
    public EcsBandAid(String mFileName, String cFileName) {
        try {
            mFin = new BufferedReader(new FileReader(mFileName));
            cFin = new BufferedReader(new FileReader(cFileName));

        } catch (FileNotFoundException e) {
            System.out.println("Wrong");
        }
    }

    /**
     * Read file
     * @return
     */
    public String getMLine() {
        String nextLine = null;
        try {
            nextLine = mFin.readLine();
        } catch (IOException e) {
            System.out.println("Wrong");
        }
        return nextLine;
    }

    /**
     * Judge whether or not the file is ready
     * @return
     */
    public boolean mFileIsReady() {
        if (mFin == null) {
            return false;
        }
        try {
            return mFin.ready();
        } catch (IOException e) {
            System.out.println("Wrong");
            return false;
        }
    }

    /**
     * Read file
     * @return
     */
    public String getCLine() {
        String nextLine = null;
        try {
            nextLine = cFin.readLine();
        } catch (IOException e) {
            System.out.println("Wrong");
        }
        return nextLine;
    }

    /**
     * Judge whether or not file is ready
     * @return
     */
    public boolean cFileIsReady() {
        if (cFin == null) {
            return false;
        }
        try {
            return cFin.ready();
        } catch (IOException e) {
            System.out.println("Wrong");
            return false;
        }
    }

    /**
     * split the line and add musicians needed into the collection of all musicians which is called allM
     * @return allM
     * @throws MidiUnavailableException
     */
    public HashSet<Musician> getMusicians() throws MidiUnavailableException {
        SoundSystem soundSystem = new SoundSystem();
        String line = "";
        while (mFileIsReady()) {
            line = this.getMLine();
            if (line != null) {
                // delete " ", use () to split
                String[] split = line.replaceAll(" ", "").split("[()]");
                // mName represents musician's name, mType represents the instrument type the musician is playing
                String mName = split[0];
                String mType = split[1];
//                System.out.println(mName + " " + mType);
                if (mType.equals("Piano")) {
                    Pianist pianist = new Pianist(mName, soundSystem);
                    allM.add(pianist);
                }
                if (mType.equals("Cello")) {
                    Cellist cellist = new Cellist(mName, soundSystem);
                    allM.add(cellist);
                }
                if (mType.equals("Violin")) {
                    Violinist violinist = new Violinist(mName, soundSystem);
                    allM.add(violinist);
                }
            } else {
                break;
            }
        }
        if (line != null) {
            System.out.println(allM);
        }
        return allM;
    }

    /**
     * split the line and add compositions needed into the collection of all compositions which is called allC
     * @return
     */
    public ArrayList<Composition> getCompositions() {
        String line;
        line = this.getCLine();
        while (cFileIsReady()) {
            if (line != null) {
                String name1 = null;
                String tempo1 = null;
                int length1 = 0;
                // Split the first three line
                for (int i = 0; i < 3; i++) {
                    // Use " : " to split the line
                    String[] split = line.replaceAll(" ","").split(":");
                    String index1 = split[0];
                    String index2 = split[1];

                    if (index1.equals("Name")) {
                        name1 = index2;
                    }
                    if (index1.equals("Tempo")) {
                        tempo1 = index2;
                    }
                    length1 = 0;
                    if (index1.equals("Length")) {
                        length1 = Integer.parseInt(index2);
                    }
                    line = this.getCLine();
                }
                MusicSheet musicSheet = new MusicSheet(name1, tempo1, length1);
                allC.add(musicSheet);
                // Use "{}" to split the last lines
                while (line != null && !line.contains("Name")) {
                    String[] split = line.split(" [{}]");
                    // Delete " "
                    String former = split[0].replaceAll(" ", "");
                    String later = split[1].replaceAll(" ", "");
                    split = former.split(",");

                    String index1 = split[0];
                    String index2 = split[1];
                    // Use "," to split the later part
                    String[] index3 = later.split(",");
                    musicSheet.addScore(index1, List.of(index3), index2.equals("soft"));
                    allC.add(musicSheet);
                    line = this.getCLine();
                }
            }
        }
        return allC;
    }

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, MidiUnavailableException{
        String mFileName = args[0];
        String cFileName = args[1];
        // Get the years required to perform for
        int yearsN = Integer.parseInt(args[2]);
        utils.SoundSystem soundSystem = new SoundSystem();
        EcsBandAid bandAid = new EcsBandAid(mFileName, cFileName);
        EcsBandAid bandAid1 = new EcsBandAid(soundSystem,bandAid.getMusicians(), bandAid.getCompositions());
        // Perform for ten years
        for (int i = 1; i <= yearsN; i++) {
//            soundSystem.setSilentMode(true);
            bandAid1.performForAYear();
            System.out.println("Has performed for " + i + " years");
        }

    }
}