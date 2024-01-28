package org.example;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        // 解析 -cp 和 主类
        Args cmd = Args.parseArgs(args);

        // 运行 jvm
        VirtualMachine vm = new VirtualMachine();
        vm.run(cmd);
    }
}
