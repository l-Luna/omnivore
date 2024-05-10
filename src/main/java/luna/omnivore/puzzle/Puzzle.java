package luna.omnivore.puzzle;

import luna.omnivore.internal.*;
import luna.omnivore.model.Molecule;
import luna.omnivore.model.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Puzzle{
	
	public @NotNull String name;
	
	public long creatorSteamId;
	
	public @NotNull Permissions permissions = Permissions.DEFAULT;
	
	public @NotNull List<Molecule> inputs = new ArrayList<>(), outputs = new ArrayList<>();
	
	public int outputMultiplier = 1;
	
	public @Nullable ProductionInfo productionInfo;
	
	public Puzzle(@NotNull String name){
		this.name = name;
	}
	
	public String toString(){
		return "Puzzle[" +
				"name='" + name + '\'' +
				", creatorSteamId=" + creatorSteamId +
				", permissions=" + permissions +
				", inputs=" + inputs +
				", outputs=" + outputs +
				", outputMultiplier=" + outputMultiplier +
				", productionInfo=" + productionInfo +
				']';
	}
	
	public boolean equals(Object o){
		return this == o
				|| o instanceof Puzzle puzzle
				&& name.equals(puzzle.name)
				&& creatorSteamId == puzzle.creatorSteamId
				&& permissions.equals(puzzle.permissions)
				&& inputs.equals(puzzle.inputs)
				&& outputs.equals(puzzle.outputs)
				&& outputMultiplier == puzzle.outputMultiplier
				&& Objects.equals(productionInfo, puzzle.productionInfo);
	}
	
	public int hashCode(){
		return Objects.hash(name, creatorSteamId, permissions, inputs, outputs, outputMultiplier, productionInfo);
	}
	
	// factories
	
	public static @NotNull Puzzle fromFile(@NotNull String path) throws IOException{
		return fromInputStream(new BufferedInputStream(Files.newInputStream(Path.of(path))));
	}
	
	public static @NotNull Puzzle fromBytes(byte @NotNull [] bytes){
		try{
			return fromInputStream(new ByteArrayInputStream(bytes));
		}catch(IOException e){
			throw new ParseException("Reached the end of data prematurely!", e);
		}
	}
	
	public static @NotNull Puzzle fromResource(@NotNull Class<?> domain, @NotNull String name){
		InputStream stream = domain.getClassLoader().getResourceAsStream(name);
		if(stream == null)
			throw new IllegalArgumentException("Specified resource doesn't exist!");
		try{
			return fromInputStream(stream);
		}catch(IOException e){
			throw new ParseException("Could not read, or reached the end of data prematurely!", e);
		}
	}
	
	public static @NotNull Puzzle fromInputStream(@NotNull InputStream stream) throws IOException{
		return PuzzleParser.parse(new CsInputStream(stream));
	}
	
	// serializers
	
	public void toFile(@NotNull String path) throws IOException{
		toOutputStream(new BufferedOutputStream(Files.newOutputStream(Path.of(path))));
	}
	
	public byte @NotNull [] toBytes(){
		var baos = new ByteArrayOutputStream();
		try{
			toOutputStream(baos);
		}catch(IOException e){
			// ByteArrayOutputStream::write does not throw IOExceptions
			throw new RuntimeException(e);
		}
		return baos.toByteArray();
	}
	
	public void toOutputStream(@NotNull OutputStream stream) throws IOException{
		PuzzleWriter.write(this, new CsOutputStream(stream));
	}
}