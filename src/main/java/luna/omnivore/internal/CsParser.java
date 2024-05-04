package luna.omnivore.internal;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class CsParser{
	
	// adapted from:
	// https://github.com/F43nd1r/omsp/blob/master/parser/src/commonMain/kotlin/com/faendir/om/parser/csharp/CSharpBinaryReader.kt
	public static int readVarInt(InputStream stream) throws IOException{
		int count = 0;
		int shift = 0;
		long b;
		do{
			b = stream.read();
			count |= (int)((b & 0b01111111) << shift);
			shift += 7;
		}while((b & 0b10000000) != 0);
		return count;
	}
	
	public static String readString(InputStream stream) throws IOException{
		int length = readVarInt(stream);
		byte[] bytes = stream.readNBytes(length);
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	public static <U> List<U> readList(DataInputStream stream, IoFunction<U> f) throws IOException{
		int l = stream.readInt();
		List<U> ret = new ArrayList<>(l);
		for(int i = 0; i < l; i++)
			ret.add(f.apply(stream));
		return ret;
	}
	
	public interface IoFunction<U>{
		U apply(DataInputStream stream) throws IOException;
	}
}