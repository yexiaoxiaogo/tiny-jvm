package org.example.classfile;

import java.io.DataInputStream;

public class MyDataInputStream extends DataInputStream {
    public MyDataInputStream(MyByteArrayInputStream in) {
        super(in);
    }
}
