import marytts.LocalMaryInterface;
import marytts.MaryInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SpeakingText {
    private MaryInterface speaking;
    private String voiceCurr = null;

    public SpeakingText(String voice) throws Exception {
        this.speaking = new LocalMaryInterface();
        this.speaking.setVoice(voice);

        voiceCurr = voice;
    }

    public void setVoice(String voice) {
        this.speaking.setVoice(voice);

        voiceCurr = voice;
    }

    private void saveVoice() {
        try {
            FileWriter fileWriter = new FileWriter("resources/voice.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(this.voiceCurr);

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getSavedVoice() {
        try {
            FileReader fileReader = new FileReader("resources/voice.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String voice = bufferedReader.readLine();

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return voice;
    }

    public void loadVoice() {
        String savedVoice = getSavedVoice();

        if (savedVoice == null) return;

        this.voice.setVoice(savedVoice);
    }


    private AudioInputStream getTextAudio(String mainText) {
        return this.speaking.generateAudio(mainText);
    }

    public void speakText(String mainText) {
        Clip clip = AudioSystem.getClip();

        AudioInputStream textAudio = getTextAudio(mainText);

        clip.open(audio);
        clip.start();
    }
}
