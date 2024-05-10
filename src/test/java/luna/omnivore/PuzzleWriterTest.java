package luna.omnivore;

import luna.omnivore.puzzle.Puzzle;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PuzzleWriterTest{
	
	@Test
	void testRoundtrip() throws IOException{
		byte[] original = PuzzleWriterTest.class
				.getClassLoader()
				.getResourceAsStream("OM2024_W7_Critellium.puzzle")
				.readAllBytes();
		Puzzle critellium = Puzzle.fromBytes(original);
		byte[] reconstructed = critellium.toBytes();
		Puzzle critellium2 = Puzzle.fromBytes(reconstructed);
		assert critellium.equals(critellium2);
	}
}