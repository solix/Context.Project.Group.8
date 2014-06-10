package com.taxi_trouble.game.sound;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**<h1> TaxiJukebox </h1>
 * In this class all the SoundFx of the game will be processed.
 * TaxiJukebox consist of a list of sounds and (background) music with their unique name.
 *
 * @author Computer Games Project Group 8
 *
 */
public final class TaxiJukebox {
    private static HashMap<String,Sound> sounds;
    private static HashMap<String,Music> musics;
    
    final String pufx="sound/powerupfx.ogg"; 
    
    private TaxiJukebox() {
    	TaxiJukebox.loadSound(pufx, "pufx");
    };

    static{
        sounds = new HashMap<String,Sound>();
        musics = new HashMap<String,Music>();
    }
    
    public static void loadFx(){
    	
    }


    public static void loadSound(String path,String name) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(name, sound);
    }

    public static void loadMusic(String path,String name) {
        Music music= Gdx.audio.newMusic(Gdx.files.internal(path));
        musics.put(name,music);
    }

    public static void playSound(String name){
        sounds.get(name).play();
    }

    public static void loopSound(String name){
        sounds.get(name).play();
    }

    public static void stopSound(String name){
        sounds.get(name).stop();
    }

    public static void playMusic(String name){
        musics.get(name).play();
    }

    public static void loopMusic(String musicname,boolean t){
        musics.get(musicname).setLooping(true);
        musics.get(musicname).play();
    }

    public static void stopMusic(String name){
        musics.get(name).stop();
    }

    public static void stopALLSounds() {
        for (Sound s:sounds.values()) {
            s.stop();
        }
    }

    public static void stopALLMusic() {
        for (Music m:musics.values()) {
            m.stop();
        }
    }

    public static void setPitch(String name,float pitchvalue){
        long id=sounds.get(name).play();
        sounds.get(name).setPitch(id, pitchvalue);
    }

    public static void setMusicVolume(String name,float volume){
        musics.get(name).setVolume(volume);
    }

    public static boolean isPlaying(String musicname){
        return musics.get(musicname).isPlaying();
    }

    public static void createMusicInGame(String path,String musicName){
        TaxiJukebox.loadMusic(path,musicName);
    }

    public static void dispose(String name){
		musics.get(name).dispose();
	}
    
  
}
