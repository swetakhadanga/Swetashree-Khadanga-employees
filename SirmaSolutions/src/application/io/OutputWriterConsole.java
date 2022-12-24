package application.io;

import application.io.Writer;

// Here are the format of the output which will be printed in console
public class OutputWriterConsole implements Writer {

    @Override
    public void write(String output) {
        System.out.print(output);
    }

  /*  @Override
    public void writeLine(String output) {
        System.out.println(output);
    }*/

  /*  @Override
    public void writeLine(String format, Object... params) {
        System.out.println(String.format(format, params));
    }*/

	@Override
	public void write(Object... ob) {
		System.out.println(ob.toString());
	}
	
	@Override
	public void write(String format, Object... params) {
		System.out.println(String.format(format, params));
	}
    
}

