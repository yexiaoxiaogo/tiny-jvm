package org.example.classfile;

import java.io.ByteArrayInputStream;

public class MyByteArrayInputStream extends ByteArrayInputStream {
    public MyByteArrayInputStream(byte[] buf) {
        super(buf);
    }
}
