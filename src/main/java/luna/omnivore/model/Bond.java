package luna.omnivore.model;

public record Bond(BondType type, HexIndex start, HexIndex end){
	
	public Bond rotate(HexIndex around, int turns){
		return new Bond(type, start.rotate(around, turns), end.rotate(around, turns));
	}
	
	public Bond translate(HexIndex by){
		return new Bond(type, start.add(by), end.add(by));
	}
}