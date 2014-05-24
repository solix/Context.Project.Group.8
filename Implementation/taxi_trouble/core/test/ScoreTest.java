import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.taxi_trouble.game.properties.ScoreBoard;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
@RunWith(MockitoJUnitRunner.class)
public class ScoreTest {
@Mock ScoreBoard score;
@Mock BitmapFont b;
	
	

	@Test
	public void incrementTest(){
		score.incrScore();
		verify(score).incrScore();
	}
	

	@Test
	public void Scoretest() {
		
		when(score.getScore()).thenReturn(0);
		assertEquals(0,score.getScore());

	
	}

}
