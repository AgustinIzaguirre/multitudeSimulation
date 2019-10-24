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
        ContractileParticleModel contractileParticleModel = new ContractileParticleModel(2, 6, 2, 1);
        contractileParticleModel.perform(50);
    }
}
