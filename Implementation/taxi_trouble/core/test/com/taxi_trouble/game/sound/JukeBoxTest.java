package com.taxi_trouble.game.sound;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JukeBoxTest {

@Mock Audio Sound;
@Mock Music music;

TaxiJukebox taxi;


public void startup(){
	taxi.loadMusic("sound/bobmar.mp3", "bobi");
	
}


	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
