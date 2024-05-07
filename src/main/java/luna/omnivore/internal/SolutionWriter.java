package luna.omnivore.internal;

import luna.omnivore.model.HexIndex;
import luna.omnivore.solution.Instruction;
import luna.omnivore.solution.Part;
import luna.omnivore.solution.PartType;
import luna.omnivore.solution.Solution;

import java.io.IOException;

public final class SolutionWriter{

	public static void write(Solution solution, CsOutputStream stream) throws IOException{
		stream.writeInt(7);
		stream.writeString(solution.puzzleName);
		stream.writeString(solution.name);
		
		if(solution.metrics != null){
			// 4 metrics, 0 marker, cycles, 1 marker, cost, 2 marker, area, 3 marker, instructions
			stream.writeInt(4);
			stream.writeInt(0);
			stream.writeInt(solution.metrics.cycles());
			stream.writeInt(1);
			stream.writeInt(solution.metrics.cost());
			stream.writeInt(2);
			stream.writeInt(solution.metrics.area());
			stream.writeInt(3);
			stream.writeInt(solution.metrics.instructions());
		}else
			stream.writeInt(0);
		
		stream.writeInt(solution.parts.size());
		for(Part part : solution.parts){
			stream.writeString(part.type().name);
			stream.writeByte((byte)1);
			stream.writeInt(part.position().u()); stream.writeInt(part.position().v());
			stream.writeInt(part.armLength());
			stream.writeInt(part.rotation());
			stream.writeInt(part.ioIndex());
			
			stream.writeInt(part.instructions().size());
			for(Instruction instr : part.instructions()){
				stream.writeInt(instr.index());
				stream.writeByte((byte)instr.type().id);
			}
			
			if(part.type() == PartType.track){
				stream.writeInt(part.trackHexes().size());
				for(HexIndex hex : part.trackHexes()){
					stream.writeInt(hex.u());
					stream.writeInt(hex.v());
				}
			}
			
			stream.writeInt(part.armNumber() - 1);
			
			if(part.type() == PartType.conduit){
				stream.writeInt(part.conduitIndex());
				stream.writeInt(part.conduitHexes().size());
				for(HexIndex hex : part.conduitHexes()){
					stream.writeInt(hex.u());
					stream.writeInt(hex.v());
				}
			}
		}
		
		stream.flush();
	}
}