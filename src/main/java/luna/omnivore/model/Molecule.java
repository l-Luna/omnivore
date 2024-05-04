package luna.omnivore.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Molecule(Map<HexIndex, AtomType> atoms, List<Bond> bonds){
	
	public Molecule(){
		this(new HashMap<>(), new ArrayList<>());
	}
}