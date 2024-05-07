package luna.omnivore.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class CsOutputStream{
	
	private final OutputStream inner;
	
	public CsOutputStream(OutputStream inner){
		this.inner = inner;
	}
	
	public void writeByte(byte b) throws IOException{
		inner.write(b);
	}
	
	public void writeInt(int i) throws IOException{
		for(int z = 0; z < 4; z++){
			writeByte((byte)(i & 0xFF));
			i >>= 8;
		}
	}
	
	public void writeLong(long l) throws IOException{
		for(int z = 0; z < 8; z++){
			writeByte((byte)(l & 0xFF));
			l >>= 8;
		}
	}
	
	public void writeBoolean(boolean b) throws IOException{
		writeByte((byte)(b ? 1 : 0));
	}
	
	public void writeVarInt(int i) throws IOException{
		do{
			writeByte((byte)(i & 0b01111111));
			i >>= 7;
		}while((i & 0b10000000) != 0);
	}
	
	public void writeString(String s) throws IOException{
		writeVarInt(s.length());
		inner.write(s.getBytes(StandardCharsets.UTF_8));
	}
	
	public void flush() throws IOException{
		inner.flush();
	}
}