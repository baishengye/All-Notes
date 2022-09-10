package com.bo.designpatternsdemo;

/**
 * 工厂方法产生的是工厂
 */
public class FactoryMethod{/**
     * 抽象工厂
     */
    abstract class ComputerFactory {
        public abstract <T extends Computer> T createComputer(Class<T> clazz);
    }

    /**
     *具体工厂
     */
    class FSKComputerFactory extends ComputerFactory{

        @Override
        public <T extends Computer> T createComputer(Class<T> clazz) {
            Computer computer=null;
            String className=clazz.getName();

            try{
                //通过反射来生产不同厂家的计算机
                computer=(Computer)Class.forName(className).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return (T)computer;
        }
    }

    public void createComputer(){
        ComputerFactory computerFactory = new FSKComputerFactory();
        Computer computer = computerFactory.createComputer(HpComputer.class);
        computer.start();
    }
}


