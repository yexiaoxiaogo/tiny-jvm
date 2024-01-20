package org.example.classfile.attribute;

import org.example.classfile.Attribute;

public class LineNumberTable extends Attribute {
    public final Line[] lines;

    public LineNumberTable(Line[] lines) {
        this.lines = lines;
    }

    public static class Line {

        public final int startPc;
        public final int lineNumber;

        public Line(int startPc, int lineNumber) {
            this.startPc = startPc;
            this.lineNumber = lineNumber;
        }
    }
}
