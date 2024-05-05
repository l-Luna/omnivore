package luna.omnivore.internal;

import luna.omnivore.model.HexIndex;
import luna.omnivore.model.ParseException;
import luna.omnivore.solution.*;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class SolutionParser{
	
	public static @NotNull Solution parse(DataInputStream stream) throws IOException{
		int version = stream.readInt();
		if(version != 7)
			throw new ParseException("Not an Opus Magnum solution file: missing or wrong version number: expected 7, got " + version + "!");
		
		Solution solution = new Solution(CsParser.readString(stream), CsParser.readString(stream));
		
		int numMetrics = stream.readInt();
		solution.metrics = switch(numMetrics){
			case 0 -> null;
			case 4 -> {
				int zero = stream.readInt();
				if(zero != 0)
					throw new ParseException("Invalid solution file metrics: expected 0, got " + zero + "!");
				int cycles = stream.readInt();
				int one = stream.readInt();
				if(one != 1)
					throw new ParseException("Invalid solution file metrics: expected 1, got " + one + "!");
				int cost = stream.readInt();
				int two = stream.readInt();
				if(two != 2)
					throw new ParseException("Invalid solution file metrics: expected 2, got " + two + "!");
				int area = stream.readInt();
				int three = stream.readInt();
				if(three != 3)
					throw new ParseException("Invalid solution file metrics: expected 3, got " + three + "!");
				int instructions = stream.readInt();
				yield new Metrics(cycles, cost, area, instructions);
			}
			default -> throw new ParseException("Invalid number of metrics " + numMetrics + "!");
		};
		
		solution.parts = CsParser.readList(stream, s -> {
			String partName = CsParser.readString(s);
			PartType partType = PartType.fromName(partName);
			if(partType == null)
				throw new ParseException("Invalid solution file part type with name \"" + partName + "\"!");
			byte one = s.readByte();
			if(one != 1)
				throw new ParseException("Invalid solution file part: expected 1, got " + one + "!");
			HexIndex position = readIntHexIndex(s);
			int armLength = s.readInt();
			int rotation = s.readInt();
			int ioIndex = s.readInt();
			List<Instruction> instructions = CsParser.readList(s, s2 -> {
				int index = s2.readInt();
				char instrId = (char)s2.readByte();
				InstructionType type = InstructionType.fromId(instrId);
				if(type == null)
					throw new ParseException("Invalid solution file instruction with ID '" + instrId + "'!");
				return new Instruction(type, index);
			});
			List<HexIndex> trackHexes = partType == PartType.track
					? CsParser.readList(s, SolutionParser::readIntHexIndex)
					: List.of();
			int armNumber = s.readInt() + 1;
			int conduitIndex = partType == PartType.conduit ? s.readInt() : 0;
			List<HexIndex> conduitHexes = partType == PartType.conduit
					? CsParser.readList(s, SolutionParser::readIntHexIndex)
					: List.of();
			return new Part(partType, position, rotation, armNumber, armLength, ioIndex, conduitIndex, trackHexes, conduitHexes, instructions);
		});
		
		return solution;
	}
	
	private static HexIndex readIntHexIndex(DataInputStream stream) throws IOException{
		return new HexIndex(stream.readInt(), stream.readInt());
	}
}