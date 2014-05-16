package com.taxitrouble.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * 
 * @author 5oheil
 * 
 */
public class SoundBank {
	public static Sound testSound;

	public static void load() {
		testSound = Gdx.audio.newSound(Gdx.files.internal("audio1.Wav"));
	}
}
