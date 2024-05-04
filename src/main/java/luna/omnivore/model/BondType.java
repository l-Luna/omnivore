package luna.omnivore.model;

public record BondType(boolean normal, boolean red, boolean black, boolean yellow){
	
	public static final BondType NORMAL = new BondType(true, false, false, false);
	public static final BondType FULL_TRIPLEX = new BondType(false, true, true, true);
	
	public static BondType fromBits(byte b){
		return new BondType((b & 1) != 0, (b & 2) != 0, (b & 4) != 0, (b & 8) != 0);
	}
	
	public byte toBits(){
		byte b = 0;
		if(normal) b |= 1;
		if(red) b |= 2;
		if(black) b |= 4;
		if(yellow) b |= 8;
		return b;
	}
}