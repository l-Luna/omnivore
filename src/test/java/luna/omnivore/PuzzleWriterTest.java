package luna.omnivore;

import luna.omnivore.puzzle.Puzzle;
import org.junit.jupiter.api.Test;

public class PuzzleWriterTest{
	
	@Test
	void testRoundtrip(){
		Puzzle critellium = Puzzle.fromResource(PuzzleWriterTest.class, "OM2024_W7_Critellium.puzzle");
		Puzzle critellium2 = Puzzle.fromBytes(critellium.toBytes());
		assert critellium.equals(critellium2);
	}
	
	@Test
	void testProductionRoundtrip(){
		Puzzle amalgam = Puzzle.fromResource(PuzzleWriterTest.class, "OM2024_W4_Dental_Amalgam.puzzle");
		Puzzle amalgam2 = Puzzle.fromBytes(amalgam.toBytes());
		assert amalgam.equals(amalgam2);
	}
}