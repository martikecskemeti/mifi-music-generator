import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;

import java.util.Random;

/**
 * Created by marti on 2017.07.03..
 */
public class Joy implements JMC, Composable {

    private Score score;
    private Part inst = new Part("Joy", 0, 0);
    private Part chords = new Part("JoyChords", 0, 1);
    private Part bass = new Part("JoyBass", new Random().nextInt(127), 2);
    private Part bassChords = new Part("JoyBassChords", new Random().nextInt(127), 3);
    private int bars = 4;
    private double[] melodyRhythm = {SQ, Q, C};
    private double[][] bassRhythmPatterns = {{SQ, Q, C, SQ}, {0.75, SQ, Q, SQ, SQ}, {SQ, SQ, 0.75, SQ, Q}, {SQ, SQ, SQ, SQ, SQ, SQ, SQ, SQ}};
    private int[][] bassPitchChords = {{d3, g3, b3}, {e3, g3, c3}, {f3, a3, c4}, {c3, e3, g3}};
    private int tempo;
    private double start;
    private Random rand = new Random();
    private int[] keyArray = {-3, 0, 3, 4};
    private int randKey;


    public Joy(Score score) {
        this.score = score;
        this.tempo = 60;
        this.start = this.score.getEndTime();
        this.randKey = rand.nextInt(keyArray.length);
        this.inst.setInstrument(new Random().nextInt(127));
        this.chords.setInstrument(new Random().nextInt(127));
        this.bass.setInstrument(new Random().nextInt(127));
    }

    private void setPhraseStart(Phrase phrase) {
        if (this.start == 0.0) {
            phrase.setStartTime(0.0);
        } else {
            phrase.setStartTime(this.start - 1);
        }
    }

    private void setCPhraseStart(CPhrase cp) {
        if (this.start == 0.0) {
            cp.setStartTime(0.0);
            System.out.println("joy1cp" + this.start);
        } else {
            cp.setStartTime(this.start - 1);
            System.out.println("joy2cp" + this.start);
        }
    }

    private void chooseMelody() {
        int x = rand.nextInt(2);
        if (x == 0) {
            makeMelodyWithScale();
        } else {
            makeMelodyWithPitchChecking();
        }
        System.out.println("melody: " + x);
    }

    private void makeMelodyWithScale() {
        Phrase phr = new Phrase();
        setPhraseStart(phr);
        int[] majorPitches = {C4, C4, D4, E4, E4, G4, G4, A4, C5, C5, D5, E5, E5, G5, G5, A5, C5, REST, REST};
        int length = 0;

        while (length <= bars * 8) {
            int randomPitch = rand.nextInt(majorPitches.length);
            int randomRhythm = rand.nextInt(melodyRhythm.length);
            Note n = new Note(majorPitches[randomPitch], melodyRhythm[randomRhythm]);
            phr.addNote(n);
            length++;
        }
        phr.setTempo(this.tempo);
        Mod.accent(phr, 4.0);
        randomTransposePhrase(phr);
        inst.addPhrase(phr);
        score.addPart(inst);
    }

    private void makeMelodyWithPitchChecking() {
        Phrase phr = new Phrase();
        setPhraseStart(phr);
        int temp, newPitch; //variable to store random number
        int[] mode = {0, 2, 4, 5, 7, 9, 11, 12}; //C major scale degrees
        int prevPitch = 60;
        for (int i = 0; i < bars * 8; ) {
            int randRit = rand.nextInt(melodyRhythm.length);
            // generate a random number up to two octaves (or so) above middle C.
            temp = (int) (Math.random() * 14 - 7);
            // smooth the melodic contour
            newPitch = prevPitch + temp;
            //check that it is a note in the mode (C major in this case)
            for (short j = 0; j < mode.length; j++) {
                if (newPitch % 12 == mode[j] && newPitch > 0 && newPitch < 127) {
                    // if it is then add it to the phrase and move to the next note in the phrase
                    if (i == bars * 8 - 4) {
                        Note n = new Note(newPitch, M, (int) (Math.random() * 50 + 60));
                        phr.addNote(n);
                    } else {
                        Note n = new Note(newPitch, melodyRhythm[randRit], (int) (Math.random() * 50 + 60));
                        phr.addNote(n);
                    }
                    prevPitch = newPitch;
                    i++;
                }
            }
        }
        phr.setTempo(this.tempo);
        Mod.accent(phr, 4.0);
        randomTransposePhrase(phr);
        inst.addPhrase(phr);
        score.addPart(inst);
    }

    private void chooseChords() {
        int x = rand.nextInt(2);
        if (x == 0) {
            makeChords();
            score.addPart(chords);

        } else if (x == 1) {
            makeBass();
            makeBassChords();
            score.addPart(bass);
            score.addPart(bassChords);
        }
        System.out.println("chords: " + x);
    }

    private void makeChords() {
        CPhrase cp = new CPhrase();
        setCPhraseStart(cp);
        double[][] bassRhythm = {{M}};
        int[][] pitches = {{c3}, {e3}, {g3}, {c4}};

        for (int i = 0; i < bars * 2 + 2; i++) {
            int x = (int) (Math.random() * 2);
            if (x == 0) {
                int randInt = rand.nextInt(bassRhythm.length);
                int randIntPitches = rand.nextInt(bassPitchChords.length);
                for (int j = 0; j < bassRhythm[randInt].length; j++) {
                    cp.addChord(bassPitchChords[randIntPitches], bassRhythm[randInt][j]);
                }
            } else {
                int randIntRhythm = rand.nextInt(bassRhythmPatterns.length);
                for (int j = 0; j < bassRhythmPatterns[randIntRhythm].length; j++) {
                    int randIntPitch = rand.nextInt(pitches.length);
                    cp.addChord(pitches[randIntPitch], bassRhythmPatterns[randIntRhythm][j]);
                }
            }
        }
        cp.setTempo(this.tempo);
        randomTransposeCPhrase(cp);
        chords.addCPhrase(cp);
        chords.setDynamic(60);

    }

    private void makeBassChords() {
        CPhrase cp = new CPhrase();
        setCPhraseStart(cp);
        for (short i = 0; i < bars * 2 + 2; i++) {
            int randInt = rand.nextInt(bassRhythmPatterns.length);
            int randIntPitches = rand.nextInt(bassPitchChords.length);
            for (int j = 0; j < bassRhythmPatterns[randInt].length; j++) {
                cp.addChord(bassPitchChords[randIntPitches], bassRhythmPatterns[randInt][j]);
            }
        }
        cp.setTempo(this.tempo);
        randomTransposeCPhrase(cp);
        bassChords.addCPhrase(cp);
        bassChords.setDynamic(45);
    }

    private void makeBass() {
        Phrase p = new Phrase();
        setPhraseStart(p);
        int[] pitches = {c3, e3, g3, c4};
        Random rand = new Random();
        for (short i = 0; i < bars * 2 + 2; i++) {
            int randIntRhythm = rand.nextInt(bassRhythmPatterns.length);
            for (int j = 0; j < bassRhythmPatterns[randIntRhythm].length; j++) {
                int randIntPitch = rand.nextInt(pitches.length);
                p.addNote(new Note(pitches[randIntPitch], bassRhythmPatterns[randIntRhythm][j]));
            }
        }
        bass.setTempo(this.tempo);
        randomTransposePhrase(p);
        bass.addPhrase(p);
        bass.setDynamic(50);
    }

    private void randomTransposePhrase(Phrase phrase) {
        Mod.transpose(phrase, keyArray[this.randKey], MAJOR_SCALE, C4);
    }

    private void randomTransposeCPhrase(CPhrase cp) {
        Mod.transpose(cp, keyArray[this.randKey], MAJOR_SCALE, C4);
    }


    public void completeScore() {
        //chooseMelody();
        makeMelodyWithScale();
        chooseChords();


    }

}
