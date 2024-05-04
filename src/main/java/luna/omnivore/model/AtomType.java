package luna.omnivore.model;

public enum AtomType{
	salt,
	air, earth, fire, water,
	quicksilver,
	gold, silver, copper, iron, tin, lead,
	vitae, mors,
	repeat,
	quintessence;
	
	private static final AtomType[] values = values();
	
	public static AtomType fromId(byte id){
		return values[id];
	}
}