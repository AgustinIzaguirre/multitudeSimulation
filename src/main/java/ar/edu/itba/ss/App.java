package ar.edu.itba.ss;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InvalidArgumentException, IOException {
//        double totalArea = Math.PI * 12;
//        double diskArea = Math.PI * Math.pow(0.32,2);
//        System.out.println("total area: " + totalArea + "\n");
//        System.out.println("disk area: " + diskArea + "\n");
//        System.out.println("max disk quantity: " + Math.floor(totalArea / diskArea) + "\n");
        ContractileParticleModel contractileParticleModel = new ContractileParticleModel(2, 4, 500 , 0);
        contractileParticleModel.perform(100);
    }
}
