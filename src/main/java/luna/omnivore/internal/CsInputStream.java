package luna.omnivore.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsInputStream{
	
	private final InputStream inner;
	
	public CsInputStream(InputStream inner){
		this.inner = inner;
	}
	
	public byte readByte() throws IOException{
		return (byte)(inner.read());
	}
	
	public int readInt() throws IOException{
		byte b1 = readByte(), b2 = readByte(), b3 = readByte(), b4 = readByte();
		return fromBytes(b4, b3, b2, b1);
	}
	
	public long readLong() throws IOException{
		byte b1 = readByte(), b2 = readByte(), b3 = readByte(), b4 = readByte(), b5 = readByte(), b6 = readByte(), b7 = readByte(), b8 = readByte();
		return fromBytes(b8, b7, b6, b5, b4, b3, b2, b1);
	}
	
	public boolean readBoolean() throws IOException{
		return readByte() != 0;
	}
	
	public <U> List<U> readList(IoFunction<U> f) throws IOException{
		int l = readInt();
		List<U> ret = new ArrayList<>(l);
		for(int i = 0; i < l; i++)
			ret.add(f.apply(this));
		return ret;
	}
	
	public interface IoFunction<U>{
		U apply(CsInputStream stream) throws IOException;
	}
	
	// adapted from:
	// https://github.com/google/guava/blob/master/android/guava/src/com/google/common/primitives/Ints.java#L332
	private static int fromBytes(byte b1, byte b2, byte b3, byte b4){
		return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
	}
	
	// adapted from:
	// https://github.com/google/guava/blob/master/android/guava/src/com/google/common/primitives/Longs.java#L316
	private static long fromBytes(byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8){
		return (b1 & 0xFFL) << 56
				| (b2 & 0xFFL) << 48
				| (b3 & 0xFFL) << 40
				| (b4 & 0xFFL) << 32
				| (b5 & 0xFFL) << 24
				| (b6 & 0xFFL) << 16
				| (b7 & 0xFFL) << 8
				| (b8 & 0xFFL);
	}
	
	// adapted from:
	// https://github.com/F43nd1r/omsp/blob/master/parser/src/commonMain/kotlin/com/faendir/om/parser/csharp/CSharpBinaryReader.kt
	public int readVarInt() throws IOException{
		int count = 0;
		int shift = 0;
		long b;
		do{
			b = inner.read();
			count |= (int)((b & 0b01111111) << shift);
			shift += 7;
		}while((b & 0b10000000) != 0);
		return count;
	}
	
	public String readString() throws IOException{
		int length = readVarInt();
		byte[] bytes = inner.readNBytes(length);
		return new String(bytes, StandardCharsets.UTF_8);
	}
}