package com.studying.bytecode.asm;

import com.studying.util.LoggerUtil;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 通过asm生成类的字节码
 */
public class GeneratorClass {

    public static void main(String[] args) throws Exception {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "com/asm3/Comparable", null, "java/lang/Object", new String[]{"com/asm3/Mesurable"});
        //定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
                "GREATER", "I", null, new Integer(1)).visitEnd();
        //定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); //使cw类已经完成
        //将cw转换成字节数组写到文件里面去
        byte[] data = cw.toByteArray();
        File file = new File("Comparable.class");
        FileOutputStream fout = new FileOutputStream(file);
        try {
            fout.write(data);
        } catch (Exception e) {
            LoggerUtil.logger.error("file output error", e);
        } finally {
            fout.close();
        }
    }
}