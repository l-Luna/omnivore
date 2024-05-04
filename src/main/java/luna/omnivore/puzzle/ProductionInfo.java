package luna.omnivore.puzzle;

import luna.omnivore.model.HexIndex;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ProductionInfo(
		boolean shrinkLeft,
		boolean shrinkRight,
		boolean isolation,
		List<Chamber> chambers,
		List<Conduit> conduits,
		List<Vial> vials
){

	public record Chamber(HexIndex position, ChamberType type){}
	
	public enum ChamberType{
		small("Small"), smallWide("SmallWide"), smallWider("SmallWider"),
		medium("Medium"), mediumWide("MediumWide"),
		large("Large");
		
		private static final ChamberType[] values = values();
		
		public final String name;
		
		ChamberType(String name){
			this.name = name;
		}
		
		public static @Nullable ChamberType fromName(String name){
			for(ChamberType value : values)
				if(value.name.equals(name))
					return value;
			return null;
		}
	}
	
	public record Conduit(HexIndex positionA, HexIndex positionB, List<HexIndex> positions){}
	
	public record Vial(HexIndex position, boolean top, int count){}
}