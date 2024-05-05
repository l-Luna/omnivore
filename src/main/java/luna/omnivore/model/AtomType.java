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
		if(id == 0 || id > values.length)
			throw new ParseException("Invalid atom type ID: " + id);
		return values[id - 1];
	}
}