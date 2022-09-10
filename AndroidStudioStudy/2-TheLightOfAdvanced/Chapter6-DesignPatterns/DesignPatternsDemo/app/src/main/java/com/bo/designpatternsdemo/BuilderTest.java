package com.bo.designpatternsdemo;

import android.nfc.Tag;
import android.util.Log;

public class BuilderTest {
    private static String TAG="BuilderTest";

    class Computer{
        private String cpu;//cpu
        private String mainBoard;//主板
        private String ram;//内存

        public String getCpu() {
            return cpu;
        }

        public void setCpu(String cpu) {
            this.cpu = cpu;
        }

        public String getMainBoard() {
            return mainBoard;
        }

        public void setMainBoard(String mainBoard) {
            this.mainBoard = mainBoard;
        }

        public String getRam() {
            return ram;
        }

        public void setRam(String ram) {
            this.ram = ram;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Computer{");
            sb.append("cpu='").append(cpu).append('\'');
            sb.append(", mainBoard='").append(mainBoard).append('\'');
            sb.append(", ram='").append(ram).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    abstract class Builder{
        public abstract void buildCpu(String cpu);
        public abstract void buildMainBroad(String mainBroad);
        public abstract void buildRam(String ram);
        public abstract Computer createComputer();
    }

    class ComputerBuilder extends Builder{
        private Computer computer=new Computer();

        @Override
        public void buildCpu(String cpu) {
            computer.setCpu(cpu);
        }

        @Override
        public void buildMainBroad(String mainBroad) {
            computer.setMainBoard(mainBroad);
        }

        @Override
        public void buildRam(String ram) {
            computer.setRam(ram);
        }

        @Override
        public Computer createComputer() {
            return computer;
        }
    }

    class Worker{
        Builder builder=null;

        public Worker(Builder builder) {
            this.builder = builder;
        }

        public Computer createComputer(String cpu,String mainBroad,String ram){
            this.builder.buildMainBroad(cpu);
            this.builder.buildCpu(mainBroad);
            this.builder.buildRam(ram);
            return builder.createComputer();
        }
    }

    public static void start(){
        BuilderTest builderTest = new BuilderTest();
        ComputerBuilder computerBuilder = builderTest.new ComputerBuilder();
        Worker worker = builderTest.new Worker(computerBuilder);
        Computer computer = worker.createComputer("i7-6700", "哈哈", "三星");
        Log.d(TAG,computer.toString());
    }
}


