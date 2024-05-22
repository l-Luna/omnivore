package luna.omnivore.model;

import java.util.*;

public record Molecule(Map<HexIndex, AtomType> atoms, Set<Bond> bonds){
	
	public Molecule(){
		this(new HashMap<>(), new HashSet<>());
	}
}