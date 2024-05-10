package luna.omnivore;

import luna.omnivore.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolutionWriterTest{
	
	@Test
	void testRoundtrip(){
		Solution critellium = Solution.fromResource(SolutionWriterTest.class, "critellium-OM2024_W7_Critellium.solution");
		byte[] reconstructed = critellium.toBytes();
		assert critellium.equals(Solution.fromBytes(reconstructed)) : "Failed to reconstruct Critellium solution";
	}
}