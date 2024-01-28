package org.example.rtda.heap;

/**
 * 实例
 */
public class Instance implements Cloneable{
    public final Class clazz;
    private Instance superInstance;

    // for class obj
    private Class metaClass;


    public Instance(Class clazz) {
        this.clazz = clazz;
    }


    public void setSuperInstance(Instance superInstance) {
        this.superInstance = superInstance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return clazz.name + "@" + this.hashCode();
    }

    public void setMetaClass(Class metaClass) {
        this.metaClass = metaClass;
    }

}
