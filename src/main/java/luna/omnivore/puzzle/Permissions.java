package luna.omnivore.puzzle;

public record Permissions(
	boolean arm,
	boolean multiArm,
	boolean pistonArm,
	boolean track,
	boolean bonder,
	boolean unbonder,
	boolean multiBonder,
	boolean triplexBonder,
	boolean calcification,
	boolean duplication,
	boolean projection,
	boolean purification,
	boolean animismus,
	boolean disposal,
	boolean unificationAndDispersion,
	boolean grabAndTurn,
	boolean drop,
	boolean reset,
	boolean repeat,
	boolean pivot,
	boolean berlo
){
	
	public static final Permissions DEFAULT = new Permissions(
			true, true, true, true, true, true, true, // all the basic tools
			false, true, false, false, false, false, false, false, // none of the specialized glyphs
			true, true, true, true, true, // all of the instructions
			false // none of the specialized mechanisms
	);
	
	public static Permissions fromBits(long bits){
		return new Permissions(
				(bits & 0x00000001) != 0,
				(bits & 0x00000002) != 0,
				(bits & 0x00000004) != 0,
				(bits & 0x00000008) != 0,
				(bits & 0x00000100) != 0,
				(bits & 0x00000200) != 0,
				(bits & 0x00000400) != 0,
				(bits & 0x00000800) != 0,
				(bits & 0x00001000) != 0,
				(bits & 0x00002000) != 0,
				(bits & 0x00004000) != 0,
				(bits & 0x00008000) != 0,
				(bits & 0x00010000) != 0,
				(bits & 0x00020000) != 0,
				(bits & 0x00040000) != 0,
				(bits & 0x00400000) != 0,
				(bits & 0x00800000) != 0,
				(bits & 0x01000000) != 0,
				(bits & 0x02000000) != 0,
				(bits & 0x04000000) != 0,
				(bits & 0x10000000) != 0
		);
	}
	
	public long toBits(){
		long bits = 0;
		if(arm) bits |= 0x00000001;
		if(multiArm) bits |= 0x00000002;
		if(pistonArm) bits |= 0x00000004;
		if(track) bits |= 0x00000008;
		if(bonder) bits |= 0x00000100;
		if(unbonder) bits |= 0x00000200;
		if(multiBonder) bits |= 0x00000400;
		if(triplexBonder) bits |= 0x00000800;
		if(calcification) bits |= 0x00001000;
		if(duplication) bits |= 0x00002000;
		if(projection) bits |= 0x00004000;
		if(purification) bits |= 0x00008000;
		if(animismus) bits |= 0x00010000;
		if(disposal) bits |= 0x00020000;
		if(unificationAndDispersion) bits |= 0x00040000;
		if(grabAndTurn) bits |= 0x00400000;
		if(drop) bits |= 0x00800000;
		if(reset) bits |= 0x01000000;
		if(repeat) bits |= 0x02000000;
		if(pivot) bits |= 0x04000000;
		if(berlo) bits |= 0x10000000;
		return bits;
	}
}