package com.taxi_trouble.game.sound;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.taxi_trouble.game.model.entities.Taxi;

/**
 * <h1>TaxiJukebox</h1> In this class all the SoundFx of the game will be
 * processed. TaxiJukebox consist of a list of sounds and (background) music
 * with their unique name.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public final class TaxiJukebox {
	private static HashMap<String, Sound> sounds;
	private static HashMap<String, Music> musics;

	private TaxiJukebox() {};

	static {
		sounds = new HashMap<String, Sound>();
		musics = new HashMap<String, Music>();
	}

	public static void loadSound(String path, String name) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, sound);
	}

	public static void loadMusic(String path, String name) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
		musics.put(name, music);
	}

	public static void playSound(String name) {
	    if (sounds.get(name) != null) {
	        sounds.get(name).play();
	    }
	}

	public static void loopSound(String name) {
	    if (sounds.get(name) != null) {
	        long id = sounds.get(name).play();
	        sounds.get(name).setLooping(id, true);
	    }
	}

	public static void stopSound(String name) {
	    if (sounds.get(name) != null) {
	        sounds.get(name).stop();
	    }
	}

	public static void playMusic(String name) {
	    if (musics.get(name) != null) {
	        musics.get(name).play();
	    }
	}

	public static void loopMusic(String musicname, boolean t) {
	    if (musics.get(musicname) != null) {
	        musics.get(musicname).setLooping(t);
	    }
	}

	public static void stopMusic(String name) {
	    if (musics.get(name) != null) {
	        musics.get(name).stop();
	    }
	}

	public static void stopALLSounds() {
		for (Sound s : sounds.values()) {
			s.stop();
		}
	}

	public static void stopALLMusic() {
		for (Music m : musics.values()) {
			m.stop();
		}
	}

	public static void setPitch(String name, float pitchvalue) {
	    if (sounds.get(name) != null) {
	        long id = sounds.get(name).play();
	        sounds.get(name).setPitch(id, pitchvalue);
	    }
	}

	public static void setMusicVolume(String name, float volume) {
	    if (musics.get(name) != null) {
	        musics.get(name).setVolume(volume);
	    }
	}

	public static boolean isPlaying(String musicname) {
	    if (musics.get(musicname) != null) {
	        return musics.get(musicname).isPlaying();
	    }
	    return false;
	}

	public static void createMusicInGame(String path, String musicName) {
		TaxiJukebox.loadMusic(path, musicName);
	}

	public static void dispose(String name) {
	    if (musics.get(name) != null) {
	        musics.get(name).dispose();
	    }
	}

	public static void taxiEngineSound(Taxi taxi, String name) {
		float pitch=1f;
		float maxPitch=1.5f;
		float normalPitch=1f;
		for (int i = 1; i < (int) taxi.getSpeedKMH(); i++){
			pitch = taxi.getSpeedKMH() / 5;
			if (pitch > maxPitch) pitch = maxPitch;
			if (pitch < normalPitch) pitch = normalPitch;
		}
		setPitch(name, pitch);
	}
}
