package luna.omnivore;

import luna.omnivore.solution.Solution;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SolutionWriterTest{
	
	@Test
	void testRoundtrip() throws IOException{
		byte[] original = SolutionWriterTest.class
				.getClassLoader()
				.getResourceAsStream("critellium-OM2024_W7_Critellium.solution")
				.readAllBytes();
		Solution critellium = Solution.fromBytes(original);
		byte[] reconstructed = critellium.toBytes();
		assert critellium.equals(Solution.fromBytes(reconstructed)) : "Failed to reconstruct Critellium solution";
	}
}