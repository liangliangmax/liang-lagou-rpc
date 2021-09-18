package com.liang.buffer;

import java.nio.ByteBuffer;

public class CreateBufferDemo {

    public static void main(String[] args) {

//        ByteBuffer allocate = ByteBuffer.allocate(5);
//
//        int capacity = allocate.capacity();
//        System.out.println(capacity);
//
//        for (int i = 0; i < capacity; i++) {
//            System.out.println(allocate.get());
//        }
//
//        //超过长度就会报错
////        System.out.println(allocate.get());
//
//
//        //这是个由内容的缓冲区
//        ByteBuffer wrap = ByteBuffer.wrap("liang".getBytes());
//        for (int i = 0; i < wrap.capacity(); i++) {
//
//            System.out.println(wrap.get());
//        }

        System.out.println("====================================");


        ByteBuffer allocate = ByteBuffer.allocate(10);

        allocate.put("123456".getBytes());

        byte[] array = allocate.array();

        System.out.println(new String(array));


        //复位重读
        allocate.rewind();

        byte[] bytes = new byte[4];

        allocate.get(bytes);

        System.out.println(new String(bytes));


    }
}
