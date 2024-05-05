package luna.omnivore.puzzle;

import luna.omnivore.internal.CsInputStream;
import luna.omnivore.internal.PuzzleParser;
import luna.omnivore.model.Molecule;
import luna.omnivore.model.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
}