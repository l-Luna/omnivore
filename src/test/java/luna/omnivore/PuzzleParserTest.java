package luna.omnivore;

import luna.omnivore.model.AtomType;
import luna.omnivore.model.ParseException;
import luna.omnivore.puzzle.ProductionInfo;
import luna.omnivore.puzzle.Puzzle;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PuzzleParserTest{
	
	@Test
	void testBasic(){
		Puzzle blank = Puzzle.fromBytes(new byte[]{
				/* version */ 3, 0, 0, 0,
				/* name length */ 0,
				/* creator ID */ 0, 0, 0, 0, 0, 0, 0, 0,
				/* permissions */ 0, 0, 0, 0, 0, 0, 0, 0,
				/* inputs length */ 0, 0, 0, 0,
				/* outputs length */ 0, 0, 0, 0,
				/* output multiplier */ 1, 0, 0, 0,
				/* is production */ 0
		});
		assert blank.name.isEmpty() : "Wrong name for blank puzzle: " + blank.name;
		
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
	
	@Test
	void testProduction(){
		Puzzle dentalAmalgam = Puzzle.fromResource(PuzzleParserTest.class, "OM2024_W4_Dental_Amalgam.puzzle");
		assert dentalAmalgam.name.equals("Dental Amalgam") : "Wrong name: \"" + dentalAmalgam.name + "\"";
		
		ProductionInfo pi = dentalAmalgam.productionInfo;
		assert pi != null : "Missing production info";
		assert !pi.shrinkLeft() : "Wrong shrinkLeft: true";
		assert !pi.shrinkRight() : "Wrong shrinkRight: true";
		assert !pi.isolation() : "Wrong isolation: true";
		assert pi.chambers().size() == 3 : "Wrong number of chambers: " + pi.chambers().size();
		assert pi.conduits().size() == 3 : "Wrong number of conduits: " + pi.conduits().size();
		assert pi.vials().size() == 3 : "Wrong number of vials: " + pi.vials().size();
	}
	
	@Test
	void testFail(){
		try{
			Puzzle.fromBytes(new byte[]{});
			throw new AssertionError("'Successfully' parsed empty puzzle file");
		}catch(ParseException ignored){}
		
		try{
			Puzzle.fromBytes(new byte[]{ 4 });
			throw new AssertionError("'Successfully' parsed puzzle file with wrong version");
		}catch(ParseException ignored){}
		
		try{
			Puzzle.fromBytes(new byte[]{
					/* version */ 3, 0, 0, 0,
					/* name length */ 0,
					/* creator ID */ 0, 0, 0, 0, 0, 0, 0, 0,
					/* permissions */ 0, 0, 0, 0, 0, 0, 0, 0,
					/* inputs length */ 1, 0, 0, 0,
					
					/* input atoms length */ 1, 0, 0, 0,
					/* invalid atom ID */ 64,
					/* atom location */ 0, 0,
					/* input 1 bonds length */ 0, 0, 0, 0,
					
					/* outputs length */ 0, 0, 0, 0,
					/* output multiplier */ 1, 0, 0, 0,
					/* is production */ 0
			});
			throw new AssertionError("'Successfully' parsed puzzle file with invalid atom ID");
		}catch(ParseException ignored){}
	}
}