package luna.omnivore.model;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public record HexIndex(int u, int v){
	
	public static final HexIndex ORIGIN = new HexIndex(0, 0);
	
	/**
	 * The implicit third coordinate of this position.
	 */
	public int w(){
		return -u -v;
	}
	
	public HexIndex add(HexIndex other){
		return new HexIndex(u + other.u, v + other.v);
	}
	
	public HexIndex sub(HexIndex other){
		return new HexIndex(u - other.u, v - other.v);
	}
	
	public int dist(HexIndex other){
		return max(max(abs(other.u), abs(other.v)), abs(other.w()));
	}
	
	public HexIndex rotateCw(){
		return new HexIndex(-v, -w());
	}
	
	public HexIndex rotateCcw(){
		return new HexIndex(-w(), -u);
	}
	
	public HexIndex rotate(HexIndex around, int turns){
		HexIndex diff = sub(around);
		turns %= 6;
		while(turns != 0){
			if(turns > 0){
				diff = diff.rotateCw();
				turns--;
			}else{
				diff = diff.rotateCcw();
				turns++;
			}
		}
		return diff.add(around);
	}
}