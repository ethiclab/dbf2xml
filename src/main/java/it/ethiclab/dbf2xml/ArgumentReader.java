package it.ethiclab.dbf2xml;

public class ArgumentReader {
    private final String[] args;
    private int pointer;

    public ArgumentReader(String[] args) {
        this.args = args;
        this.pointer = 0;
    }

    public String readArg() {
        return args.length > this.pointer ? args[pointer++] : null;
    }
}
