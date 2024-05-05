package luna.omnivore;

import luna.omnivore.model.AtomType;
import luna.omnivore.puzzle.Puzzle;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PuzzleParserTest{
	
	@Test
	void testBasic(){
		Puzzle critellium = Puzzle.fromResource(PuzzleParserTest.class, "OM2024_W7_Critellium.puzzle");
		assert critellium.name.equals("CRITELLIUM") : "Wrong name: \"" + critellium.name + "\"";
		assert critellium.creatorSteamId == 76561198081535586L : "Wrong creator Steam ID: " + critellium.creatorSteamId;
		assert critellium.inputs.size() == 1 : "Wrong number of inputs: " + critellium.inputs.size();
		assert critellium.outputs.size() == 1 : "Wrong number of outputs: " + critellium.outputs.size();
		assert critellium.permissions.toBits() == 0b111110000101101011100001111 : "Wrong permissions: 0b" + Long.toString(critellium.permissions.toBits(), 2);
		
		int numInputAtoms = critellium.inputs.getFirst().atoms().size();
		assert numInputAtoms == 4 : "Wrong number of atoms in input: " + numInputAtoms;
		Collection<AtomType> inputAtomTypes = critellium.inputs.getFirst().atoms().values();
		assert new HashSet<>(inputAtomTypes).equals(Set.of(AtomType.quicksilver, AtomType.lead, AtomType.tin, AtomType.iron))
				: "Wrong set of atom types in input: " + inputAtomTypes;
		
		int numOutputAtoms = critellium.outputs.getFirst().atoms().size();
		assert numOutputAtoms == 8 : "Wrong number of atoms in output: " + numOutputAtoms;
		Collection<AtomType> outputAtomTypes = critellium.outputs.getFirst().atoms().values();
		assert new HashSet<>(outputAtomTypes).equals(Set.of(AtomType.quicksilver, AtomType.silver, AtomType.gold))
				: "Wrong set of atom types in output: " + outputAtomTypes;
	}
}