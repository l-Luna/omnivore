package luna.omnivore.model;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public record Molecule(Map<HexIndex, AtomType> atoms, Set<Bond> bonds){
	
	public Molecule(){
		this(new HashMap<>(), new HashSet<>());
	}
	
	public @Nullable AtomType getAtom(HexIndex at){
		return atoms.get(at);
	}
	
	public Molecule rotate(HexIndex around, int turns){
		if(turns == 0)
			return this;
		Map<HexIndex, AtomType> newAtoms = new HashMap<>(atoms.size());
		for(Map.Entry<HexIndex, AtomType> entry : atoms.entrySet())
			newAtoms.put(entry.getKey().rotate(around, turns), entry.getValue());
		Set<Bond> newBonds = bonds.stream().map(x -> x.rotate(around, turns)).collect(Collectors.toSet());
		return new Molecule(newAtoms, newBonds);
	}
	
	public Molecule translate(HexIndex by){
		Map<HexIndex, AtomType> newAtoms = new HashMap<>(atoms.size());
		for(Map.Entry<HexIndex, AtomType> entry : atoms.entrySet())
			newAtoms.put(entry.getKey().add(by), entry.getValue());
		Set<Bond> newBonds = bonds.stream().map(x -> x.translate(by)).collect(Collectors.toSet());
		return new Molecule(newAtoms, newBonds);
	}
	
	public Molecule normalizeTranslation(){
		// find the top-most atom and translate it to 0v, and the left-most and translate it to 0u
		int minU = atoms.keySet().stream().mapToInt(HexIndex::u).min().getAsInt();
		int minV = atoms.keySet().stream().mapToInt(HexIndex::v).min().getAsInt();
		return translate(new HexIndex(-minU, -minV));
	}
	
	public boolean equalsIgnoreTranslation(Molecule other){
		return equals(other) || normalizeTranslation().equals(other.normalizeTranslation());
	}
	
	public boolean equalsIgnoreRotation(Molecule other){
		for(int i = 0; i < 6; i++)
			if(rotate(HexIndex.ORIGIN, i).equalsIgnoreTranslation(other))
				return true;
		return false;
	}
}