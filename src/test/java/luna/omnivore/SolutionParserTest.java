package luna.omnivore;

import luna.omnivore.solution.Instruction;
import luna.omnivore.solution.Metrics;
import luna.omnivore.solution.Part;
import luna.omnivore.solution.Solution;
import org.junit.jupiter.api.Test;

public class SolutionParserTest{
	
	@Test
	void testBasic(){
		Solution critellium = Solution.fromResource(SolutionParserTest.class, "critellium-OM2024_W7_Critellium.solution");
		assert critellium.puzzleName.equals("OM2024_W7_Critellium") : "Wrong puzzle name: " + critellium.puzzleName;
		assert critellium.name.equals("COMPLEXITY THEORY? IT SEEMS PRETTY SIMPLE TO ME") : "Wrong solution name: " + critellium.name;
		assert critellium.metrics != null : "Wrong solution metrics: not solved";
		assert critellium.metrics.equals(new Metrics(2619, 910, 645, 107)) : "Wrong solution metrics: " + critellium.metrics;
		assert critellium.parts.size() == 49 : "Wrong number of parts: " + critellium.parts.size();
		for(Part part : critellium.parts){
			int maxInstrIdx = -1;
			for(Instruction i : part.instructions()){
				if(i.index() <= maxInstrIdx)
					throw new AssertionError("Wrong instructions order: went from " + maxInstrIdx + " to " + i.index());
				maxInstrIdx = i.index();
			}
		}
	}
}