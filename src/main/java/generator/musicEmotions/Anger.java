package generator.musicEmotions;

import jm.JMC;
import jm.music.data.*;
import java.util.Random;

/**
 * Created by marti on 2017.07.04..
 */
public class Anger implements JMC, Composable {

    private Score score;
    private Part inst = new Part("musicEmotions.Anger", new Random().nextInt(127), 6);
    private Part chords = new Part("AngerChords", new Random().nextInt(127), 7);
    private Phrase phrBass = new Phrase();
    private int bars = 4;
    private int tempo;
    private double start;
    private int[] minorPitches = {A2, A2, A2, B2, C3, C3, D3, E2, F2, F2, G2, A3};
    private double[] melodyRhythm = {SQ - SQ / 3, SQT - SQT / 3, SQ - SQ / 3};
    private Random rand = new Random();


    public Anger(Score score) {
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
        Phrase phr = new Phrase();
        setPhraseStart(phr);
        setPhraseStart(phrBass);
        int size = 0;

        while (size <= bars * 10) {
            int randPitch = rand.nextInt(minorPitches.length);
            int randPitchRhythm = rand.nextInt(melodyRhythm.length);
            Note n = new Note(minorPitches[randPitch], melodyRhythm[randPitchRhythm], rand.nextInt(127 - 120) + 100);
            phr.addNote(n);
            size++;
        }
        phr.setTempo(this.tempo);
        inst.addPhrase(phr);
    }


    private void makeChords() {
        CPhrase cp = new CPhrase(0.0);
        double[][] rhythmArray = {{SQ - SQ / 3, SQ - SQ / 3, SQ - SQ / 3, SQ - SQ / 3}, {SQ - SQ / 3, Q - Q / 3, SQ - SQ / 3, Q - SQ / 3}};
        for (short i = 0; i < bars + 2; i++) {
            int x = (int) (Math.random() * 2);
            System.out.println("chord var x is " + x);
            if (x == 0) {
                int[] pitchArray = {f3, a3, b3};
                for (int rhythm = 0; rhythm < rhythmArray[x].length; rhythm++) {
                    cp.addChord(pitchArray, rhythmArray[x][rhythm]);
                }
            } else if (x == 1) {
                int[] pitchArray = {b2, c3, f3};
                for (int rhythm = 0; rhythm < rhythmArray[x].length; rhythm++) {
                    cp.addChord(pitchArray, rhythmArray[x][rhythm]);
                }
            } else {
                int[] pitchArray = {d3, f3, a3};
                cp.addChord(pitchArray, Q);
            }
        }
        cp.setTempo(this.tempo);
        chords.addCPhrase(cp);

    }

    public void completeScore() {
        makeMelodyWithScale();
        makeChords();
        score.addPart(inst);
        score.addPart(chords);
    }
}
