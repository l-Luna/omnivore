package luna.omnivore.solution;

import luna.omnivore.model.HexIndex;

import java.util.List;

public record Part(
		PartType type,
		HexIndex position,
		int rotation,
		int armNumber,
		int armLength,
		int ioIndex,
		int conduitIndex,
		List<HexIndex> trackHexes,
		List<HexIndex> conduitHexes,
		List<Instruction> instructions
){}