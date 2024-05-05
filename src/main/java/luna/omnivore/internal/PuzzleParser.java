package luna.omnivore.internal;

import luna.omnivore.model.*;
import luna.omnivore.puzzle.Permissions;
import luna.omnivore.puzzle.ProductionInfo;
import luna.omnivore.puzzle.Puzzle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PuzzleParser{
	
	public static @NotNull Puzzle parse(CsInputStream stream) throws IOException{
		int version = stream.readInt();
		if(version != 3)
			throw new ParseException("Not an Opus Magnum puzzle file: missing or wrong version number: expected 3, got " + version + "!");
		
		Puzzle puzzle = new Puzzle(stream.readString());
		puzzle.creatorSteamId = stream.readLong();
		puzzle.permissions = Permissions.fromBits(stream.readLong());
		puzzle.inputs = stream.readList(PuzzleParser::readMolecule);
		puzzle.outputs = stream.readList(PuzzleParser::readMolecule);
		puzzle.outputMultiplier = stream.readInt();
		
		if(stream.readBoolean()){
			boolean shrinkLeft = stream.readBoolean();
			boolean shrinkRight = stream.readBoolean();
			boolean isolation = stream.readBoolean();
			List<ProductionInfo.Chamber> chambers = stream.readList(s ->
					new ProductionInfo.Chamber(readByteHexIndex(s), ProductionInfo.ChamberType.fromName(s.readString())));
			List<ProductionInfo.Conduit> conduits = stream.readList(s ->
					new ProductionInfo.Conduit(readByteHexIndex(s), readByteHexIndex(s), s.readList(PuzzleParser::readByteHexIndex)));
			List<ProductionInfo.Vial> vials = stream.readList(s ->
					new ProductionInfo.Vial(readByteHexIndex(s), s.readBoolean(), s.readInt()));
			puzzle.productionInfo = new ProductionInfo(shrinkLeft, shrinkRight, isolation, chambers, conduits, vials);
		}
		
		return puzzle;
	}
	
	private static Molecule readMolecule(CsInputStream stream) throws IOException{
		int atomCount = stream.readInt();
		Map<HexIndex, AtomType> atoms = new HashMap<>(atomCount);
		for(int i = 0; i < atomCount; i++){
			AtomType ty = AtomType.fromId(stream.readByte());
			HexIndex idx = readByteHexIndex(stream);
			atoms.put(idx, ty);
		}
		List<Bond> bonds = stream.readList(s -> new Bond(BondType.fromBits(s.readByte()), readByteHexIndex(s), readByteHexIndex(s)));
		return new Molecule(atoms, bonds);
	}
	
	private static HexIndex readByteHexIndex(CsInputStream stream) throws IOException{
		return new HexIndex(stream.readByte(), stream.readByte());
	}
}