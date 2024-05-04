package luna.omnivore.solution;

import luna.omnivore.internal.SolutionParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Solution{
	
	public @NotNull String name, puzzleName;
	
	public @NotNull List<Part> parts = new ArrayList<>();
	
	public @Nullable Metrics metrics;
	
	public Solution(@NotNull String name, @NotNull String puzzleName){
		this.name = name;
		this.puzzleName = puzzleName;
	}
	
	// factories
	
	public static @NotNull Solution fromFile(@NotNull String path) throws IOException{
		return fromInputStream(new BufferedInputStream(Files.newInputStream(Path.of(path))));
	}
	
	public static @NotNull Solution fromBytes(byte @NotNull [] bytes){
		return fromInputStream(new ByteArrayInputStream(bytes));
	}
	
	public static @NotNull Solution fromResource(@NotNull Class<?> domain, @NotNull String name){
		InputStream stream = domain.getClassLoader().getResourceAsStream(name);
		if(stream == null)
			throw new IllegalArgumentException("Specified resource doesn't exist!");
		return fromInputStream(stream);
	}
	
	public static @NotNull Solution fromInputStream(@NotNull InputStream stream){
		return SolutionParser.parse(new DataInputStream(stream));
	}
}