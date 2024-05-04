package luna.omnivore.solution;

import org.jetbrains.annotations.Nullable;

public enum PartType{
	input("input"), output("out-std"), polymerOutput("out-rep"),
	arm("arm1"), biArm("arm2"), triArm("arm3"), hexArm("arm6"), pistonArm("piston"),
	track("track"), berlo("baron"),
	equilibrium("glyph-marker"), bonding("bonder"), multiBonding("bonder-speed"), unbonding("unbonder"), calcification("glyph-calcification"),
	projection("glyph-projection"), purification("glyph-purification"),
	duplication("glyph-duplication"), animismus("glyph-life-and-death"),
	unification("glyph-unification"), dispersion("glyph-dispersion"),
	triplexBonding("bonder-prisma"),
	disposal("glyph-disposal"),
	conduit("pipe");
	
	private static final PartType[] values = values();
	
	public final String name;
	
	PartType(String name){
		this.name = name;
	}
	
	public static @Nullable PartType fromName(String name){
		for(PartType value : values)
			if(value.name.equals(name))
				return value;
		return null;
	}
}
