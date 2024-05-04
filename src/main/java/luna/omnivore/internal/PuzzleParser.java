package luna.omnivore.internal;

import luna.omnivore.model.*;
import luna.omnivore.puzzle.Permissions;
import luna.omnivore.puzzle.ProductionInfo;
import luna.omnivore.puzzle.Puzzle;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PuzzleParser{
	
	public static @NotNull Puzzle parse(DataInputStream stream) throws IOException{
		if(stream.readInt() != 3)
			throw new ParseException("Invalid puzzle data: not an Opus Magnum puzzle file!");
		
		Puzzle puzzle = new Puzzle(CsParser.readString(stream));
		puzzle.creatorSteamId = stream.readLong();
		puzzle.permissions = Permissions.fromBits(stream.readLong());
		puzzle.inputs = CsParser.readList(stream, PuzzleParser::readMolecule);
		puzzle.outputs = CsParser.readList(stream, PuzzleParser::readMolecule);
		puzzle.outputMultiplier = stream.readInt();
		
		if(stream.readBoolean()){
			boolean shrinkLeft = stream.readBoolean();
			boolean shrinkRight = stream.readBoolean();
			boolean isolation = stream.readBoolean();
			List<ProductionInfo.Chamber> chambers = CsParser.readList(stream, s ->
					new ProductionInfo.Chamber(readByteHexIndex(s), ProductionInfo.ChamberType.fromName(CsParser.readString(s))));
			List<ProductionInfo.Conduit> conduits = CsParser.readList(stream, s ->
					new ProductionInfo.Conduit(readByteHexIndex(s), readByteHexIndex(s), CsParser.readList(s, PuzzleParser::readByteHexIndex)));
			List<ProductionInfo.Vial> vials = CsParser.readList(stream, s ->
					new ProductionInfo.Vial(readByteHexIndex(s), s.readBoolean(), s.readInt()));
			puzzle.productionInfo = new ProductionInfo(shrinkLeft, shrinkRight, isolation, chambers, conduits, vials);
		}
		
		return puzzle;
	}
	
	private static Molecule readMolecule(DataInputStream stream) throws IOException{
		int atomCount = stream.readInt();
		Map<HexIndex, AtomType> atoms = new HashMap<>(atomCount);
		for(int i = 0; i < atomCount; i++){
			AtomType ty = AtomType.fromId(stream.readByte());
			HexIndex idx = readByteHexIndex(stream);
			atoms.put(idx, ty);
		}
		List<Bond> bonds = CsParser.readList(stream, s ->
				new Bond(BondType.fromBits(s.readByte()), readByteHexIndex(s), readByteHexIndex(s)));
		return new Molecule(atoms, bonds);
	}
	
	private static HexIndex readByteHexIndex(DataInputStream stream) throws IOException{
		return new HexIndex(stream.readByte(), stream.readByte());
	}
}