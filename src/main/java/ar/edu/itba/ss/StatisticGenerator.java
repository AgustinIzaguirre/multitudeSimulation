package ar.edu.itba.ss;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticGenerator {
    public static void main(String[] args) throws IOException, InvalidArgumentException {
        double internalWallRadius = 2;
        double externalWallRadius = 4;
        generateFundamentalDiagramOutput(internalWallRadius,externalWallRadius, 10);
    }

    public static void generateFundamentalDiagramOutput(double internalWallRadius, double externalWallRadius, int runs) throws IOException, InvalidArgumentException {
        String path = "Output/Results/";
        double totalArea = Math.PI * (Math.pow(externalWallRadius, 2) - Math.pow(internalWallRadius, 2));
        for(int particleQuantity = 5; particleQuantity <= 50; particleQuantity += 5) {
            double density = totalArea / particleQuantity;
            for(int i = 0; i < runs; i++) {
                String resultFilePath = path + density + "_ext" + externalWallRadius + "_int" + internalWallRadius + "_q" + particleQuantity + "_run" + i + ".txt";
                FileWriter resultFile = new FileWriter(resultFilePath);
                BufferedWriter resultWriter = new BufferedWriter(resultFile);
                resultWriter.write("time,positionx,positiony,speedProjected,radius,density\n");
                ContractileParticleModel contractileParticleModel = new ContractileParticleModel(internalWallRadius, externalWallRadius, particleQuantity, 0);
                contractileParticleModel.performWritingOutput(100, 30, resultWriter, density);
                resultWriter.close();
            }
        }

    }
}
