package org.example;

public class Args {
    private static final String MINUS_CP = "-cp";

    public String classpath = ".";
    public String clazz;

    public static Args parseArgs(String... cliArgs) {
        Args args = new Args();

        for (int i = 0; i < cliArgs.length; i++) {
            final String tmp = cliArgs[i];
            if (tmp.equals(MINUS_CP)) {
                final String cp = cliArgs[++i];
                args.classpath = cp;
                args.clazz = cliArgs[++i];
                break;
            }

        }

        return args;
    }

}
