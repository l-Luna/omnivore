package luna.omnivore.model;

public record HexIndex(int u, int v){
	
	public static final HexIndex ORIGIN = new HexIndex(0, 0);
}