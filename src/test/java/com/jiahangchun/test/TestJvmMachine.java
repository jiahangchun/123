package com.jiahangchun.test;

import com.sun.tools.attach.VirtualMachine;

import java.util.Properties;

public class TestJvmMachine {
    public static void main(String[] args) throws Exception {
        VirtualMachine virtualMachine=  VirtualMachine.attach("48558");
        Properties properties=virtualMachine.getSystemProperties();
        virtualMachine.detach();
    }
}
