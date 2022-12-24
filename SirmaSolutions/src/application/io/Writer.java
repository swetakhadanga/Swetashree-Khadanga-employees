package application.io;

public interface Writer {

	void write(String output);

	//  void writeLine(String output);
	// void writeLine(String format, Object... params);
	void write(String format, Object... params);
	void write(Object... ob);

}
