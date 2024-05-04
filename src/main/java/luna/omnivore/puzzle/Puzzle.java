package luna.omnivore.puzzle;

import luna.omnivore.internal.PuzzleParser;
import luna.omnivore.model.Molecule;
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
	
	// factories
	
	public static @NotNull Puzzle fromFile(@NotNull String path) throws IOException{
		return fromInputStream(new BufferedInputStream(Files.newInputStream(Path.of(path))));
	}
	
	public static @NotNull Puzzle fromBytes(byte @NotNull [] bytes){
		return fromInputStream(new ByteArrayInputStream(bytes));
	}
	
	public static @NotNull Puzzle fromResource(@NotNull Class<?> domain, @NotNull String name){
		InputStream stream = domain.getClassLoader().getResourceAsStream(name);
		if(stream == null)
			throw new IllegalArgumentException("Specified resource doesn't exist!");
		return fromInputStream(stream);
	}
	
	public static @NotNull Puzzle fromInputStream(@NotNull InputStream stream){
		return PuzzleParser.parse(new DataInputStream(stream));
	}
}