import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;

import java.util.Random;

/**
 * Created by marti on 2017.07.03..
 */
public class Sad implements JMC, Composable {

    private Score score;
    private Part inst = new Part("Sad", 0, 4);
    private Part chords = new Part("SadChords", 0, 5);
    private Phrase phr = new Phrase();
    private Phrase phrBass = new Phrase();
    private int bars = 4;
    private int tempo;
    private double start;
    private int[] minorPitches = {A3, B3, C4, D4, E4, F4, G4, A4, REST};
    private double[] rhythm = {C};
    private int[] minorBass = {E4, D4, A2};
    private Random rand = new Random();


    public Sad(Score score) {
        this.score = score;
        this.tempo = 60;
        this.start = this.score.getEndTime();
        this.inst.setInstrument(new Random().nextInt(127));
        this.chords.setInstrument(new Random().nextInt(127));
    }

    private void setPhraseStart(Phrase phrase) {
        if (this.start == 0.0) {
            phrase.setStartTime(0.0);
        } else {
            phrase.setStartTime(score.getEndTime() - 1.0);

        }
    }

    private void setCPhraseStart(CPhrase cp) {
        if (this.start == 0.0) {
            cp.setStartTime(0.0);
        } else {
            cp.setStartTime(score.getEndTime() - 1.0);
        }
    }

    private void makeMelodyWithScale() {
        setPhraseStart(phr);
        setPhraseStart(phrBass);
        int size = 0;

        while (size <= bars * 4) {
            int randPitch = rand.nextInt(minorPitches.length);
            int randBass = rand.nextInt(minorBass.length);
            int randPitchRhythm = rand.nextInt(rhythm.length);
            if (size % 4 == 0) {
                Note n = new Note(minorPitches[randPitch], rhythm[randPitchRhythm]);
                Note b = new Note(minorBass[randBass], SB);
                phr.addNote(n);
                phrBass.addNote(b);
                size++;
            } else {
                Note n = new Note(minorPitches[randPitch], CROTCHET);
                phr.addNote(n);
                size++;
            }
        }

        inst.addPhrase(phr);
        chords.addPhrase(phrBass);
    }

    public void completeScore() {
        makeMelodyWithScale();
        score.addPart(inst);
        score.addPart(chords);
    }
}
