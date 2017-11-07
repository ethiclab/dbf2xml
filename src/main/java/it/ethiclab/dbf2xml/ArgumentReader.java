package it.ethiclab.dbf2xml;

public class ArgumentReader {
    private final String[] args;
    private int pointer;

    public ArgumentReader(String[] args) {
        this.args = args;
        this.pointer = 0;
    }

    public String readArg(String name) {
        String retval = args.length > this.pointer ? args[pointer++] : null;
        if (retval == null) {
            throw new ApplicationRuntimeException(name + " is mandatory.");
        }
        return retval;
    }
}
