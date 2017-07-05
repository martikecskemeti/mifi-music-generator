package musicEmotions;

import jm.JMC;
import jm.music.data.*;
import musicEmotions.Composable;

import java.util.Random;

/**
 * Created by marti on 2017.07.04..
 */
public class Fear implements JMC, Composable {

    private Score score;
    private Part inst = new Part("musicEmotions.Fear", new Random().nextInt(127), 5);
    private Part chords = new Part("FearChords", new Random().nextInt(127), 6);
    private Phrase phr = new Phrase();
    private Phrase phrBass = new Phrase();
    private int bars = 4;
    private int tempo;
    private double start;
    private int[] minorPitches = {A3, B3, C4, D4, E4, F4, G4, A4, REST};
    private double[] rhythm = {C, Q, SQ, SQ, SQ, SQ, SQ, SQ, 0.15, 0.15, EIGHTH_NOTE_TRIPLET, DOTTED_EIGHTH_NOTE, DOTTED_SIXTEENTH_NOTE};
    private int[] minorBass = {E4, D4, A2};
    private Random rand = new Random();


    public Fear(Score score) {
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
        while (size <= bars * 8) {
            Note n = new Note(rand.nextInt(127 - 20) + 20, rhythm[rand.nextInt(rhythm.length)]);
            Note b = new Note(rand.nextInt(127 - 20) + 20, rhythm[rand.nextInt(rhythm.length)]);
            phr.addNote(n);
            phrBass.addNote(b);
            size++;
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
