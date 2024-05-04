package luna.omnivore.solution;

import org.jetbrains.annotations.Nullable;

public enum InstructionType{
	blank(' '),
	grab('G'), drop('g'),
	rotateClockwise('R'), rotateAnticlockwise('r'),
	extend('E'), retract('e'),
	pivotClockwise('P'), pivotAnticlockwise('p'),
	advance('A'), retreat('a'),
	periodOverride('O'), reset('X'), repeat('C'); //  continues to be incredible
	
	private static final InstructionType[] values = values();
	
	public final char id;
	
	InstructionType(char id){
		this.id = id;
	}
	
	public static @Nullable InstructionType fromId(char id){
		for(InstructionType value : values)
			if(value.id == id)
				return value;
		return null;
	}
}