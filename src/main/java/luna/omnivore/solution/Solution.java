package luna.omnivore.solution;

import luna.omnivore.internal.CsInputStream;
import luna.omnivore.internal.CsOutputStream;
import luna.omnivore.internal.SolutionParser;
import luna.omnivore.internal.SolutionWriter;
import luna.omnivore.model.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Solution{
	
	public @NotNull String puzzleName, name;
	
	public @Nullable Metrics metrics;
	
	public @NotNull List<Part> parts = new ArrayList<>();
	
	public Solution(@NotNull String puzzleName, @NotNull String name){
		this.puzzleName = puzzleName;
		this.name = name;
	}
	
	public String toString(){
		return "Solution[" +
				"puzzleName='" + puzzleName + '\'' +
				", name='" + name + '\'' +
				", metrics=" + metrics +
				", parts=" + parts +
				']';
	}
	
	public boolean equals(Object o){
		return this == o
				|| o instanceof Solution solution
				&& puzzleName.equals(solution.puzzleName)
				&& name.equals(solution.name)
				&& Objects.equals(metrics, solution.metrics)
				&& parts.equals(solution.parts);
	}
	
	public int hashCode(){
		return Objects.hash(puzzleName, name, metrics, parts);
	}
	
	// factories
	
	public static @NotNull Solution fromFile(@NotNull String path) throws IOException{
		return fromInputStream(new BufferedInputStream(Files.newInputStream(Path.of(path))));
	}
	
	public static @NotNull Solution fromBytes(byte @NotNull [] bytes){
		try{
			return fromInputStream(new ByteArrayInputStream(bytes));
		}catch(IOException e){
			throw new ParseException("Reached end of data prematurely!", e);
		}
	}
	
	public static @NotNull Solution fromResource(@NotNull Class<?> domain, @NotNull String name){
		InputStream stream = domain.getClassLoader().getResourceAsStream(name);
		if(stream == null)
			throw new IllegalArgumentException("Specified resource doesn't exist!");
		try{
			return fromInputStream(stream);
		}catch(IOException e){
			throw new ParseException("Could not read, or reached the end of data prematurely!", e);
		}
	}
	
	public static @NotNull Solution fromInputStream(@NotNull InputStream stream) throws IOException{
		return SolutionParser.parse(new CsInputStream(stream));
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
		SolutionWriter.write(this, new CsOutputStream(stream));
	}
}