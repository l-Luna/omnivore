package luna.omnivore.model;

import java.util.*;
import java.util.stream.Collectors;

public record Molecule(Map<HexIndex, AtomType> atoms, Set<Bond> bonds){
	
	public Molecule(){
		this(new HashMap<>(), new HashSet<>());
	}
	
	public Molecule rotate(HexIndex around, int turns){
		Map<HexIndex, AtomType> newAtoms = new HashMap<>(atoms.size());
		for(Map.Entry<HexIndex, AtomType> entry : atoms.entrySet())
			newAtoms.put(entry.getKey().rotate(around, turns), entry.getValue());
		Set<Bond> newBonds = bonds.stream().map(x -> x.rotate(around, turns)).collect(Collectors.toSet());
		return new Molecule(newAtoms, newBonds);
	}
}