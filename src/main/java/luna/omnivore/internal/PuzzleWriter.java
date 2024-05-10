package luna.omnivore.internal;

import luna.omnivore.model.AtomType;
import luna.omnivore.model.Bond;
import luna.omnivore.model.HexIndex;
import luna.omnivore.model.Molecule;
import luna.omnivore.puzzle.ProductionInfo;
import luna.omnivore.puzzle.Puzzle;

import java.io.IOException;
import java.util.Map;

public final class PuzzleWriter{

	public static void write(Puzzle puzzle, CsOutputStream stream) throws IOException{
		stream.writeInt(3);
		stream.writeString(puzzle.name);
		stream.writeLong(puzzle.creatorSteamId);
		stream.writeLong(puzzle.permissions.toBits());
		
		stream.writeInt(puzzle.inputs.size());
		for(Molecule input : puzzle.inputs)
			writeMolecule(input, stream);
		stream.writeInt(puzzle.outputs.size());
		for(Molecule output : puzzle.outputs)
			writeMolecule(output, stream);
		
		stream.writeInt(puzzle.outputMultiplier);
		
		stream.writeBoolean(puzzle.productionInfo != null);
		if(puzzle.productionInfo != null){
			ProductionInfo pi = puzzle.productionInfo;
			stream.writeBoolean(pi.shrinkLeft());
			stream.writeBoolean(pi.shrinkRight());
			stream.writeBoolean(pi.isolation());
			
			stream.writeInt(pi.chambers().size());
			for(ProductionInfo.Chamber chamber : pi.chambers()){
				stream.writeByte((byte)chamber.position().u());
				stream.writeByte((byte)chamber.position().v());
				stream.writeString(chamber.type().name);
			}
			
			stream.writeInt(pi.conduits().size());
			for(ProductionInfo.Conduit conduit : pi.conduits()){
				stream.writeByte((byte)conduit.positionA().u());
				stream.writeByte((byte)conduit.positionA().v());
				stream.writeByte((byte)conduit.positionB().u());
				stream.writeByte((byte)conduit.positionB().v());
				
				stream.writeInt(conduit.positions().size());
				for(HexIndex pos : conduit.positions()){
					stream.writeByte((byte)pos.u());
					stream.writeByte((byte)pos.v());
				}
			}
			
			stream.writeInt(pi.vials().size());
			for(ProductionInfo.Vial vial : pi.vials()){
				stream.writeByte((byte)vial.position().u());
				stream.writeByte((byte)vial.position().v());
				stream.writeBoolean(vial.top());
				stream.writeInt(vial.count());
			}
		}
		
		stream.flush();
	}
	
	private static void writeMolecule(Molecule mol, CsOutputStream stream) throws IOException{
		stream.writeInt(mol.atoms().size());
		for(Map.Entry<HexIndex, AtomType> entry : mol.atoms().entrySet()){
			stream.writeByte((byte)(entry.getValue().ordinal() + 1));
			stream.writeByte((byte)entry.getKey().u());
			stream.writeByte((byte)entry.getKey().v());
		}
		
		stream.writeInt(mol.bonds().size());
		for(Bond bond : mol.bonds()){
			stream.writeByte(bond.type().toBits());
			stream.writeByte((byte)bond.start().u());
			stream.writeByte((byte)bond.start().v());
			stream.writeByte((byte)bond.end().u());
			stream.writeByte((byte)bond.end().v());
		}
	}
}