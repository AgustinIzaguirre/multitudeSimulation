package ar.edu.itba.ss;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticGenerator {
    public static void main(String[] args) throws IOException, InvalidArgumentException {
        double[] densities = {0.088,0.177,0.354,0.531,0.708,0.885,1.062,1.239,1.416,1.593,1.770,1.947,2.124,2.301,
                                2.478,2.655,2.832,3.009,3.186,3.363,3.540,3.717,3.894,4.071,4.248,4.425,4.602,4.779,
                                4.956,5.133,5.310,5.487,5.664,5.841,6.018,6.195,6.372,6.549,6.726,6.903,7.080,7.257,
                                7.434,7.611,7.788,7.965,8.142};
        double internalWallRadius = 2;
        double externalWallRadius = 7;
        double beta = 0.9;
        generateFundamentalDiagramOutput(internalWallRadius,externalWallRadius, 4, densities, beta);
    }

    public static void generateFundamentalDiagramOutput(double internalWallRadius, double externalWallRadius, int runs,
                                                        double[] densities, double beta) throws IOException, InvalidArgumentException {
        String path = "Output/Results/ancho5/";
        double totalArea = Math.PI * (Math.pow(externalWallRadius, 2) - Math.pow(internalWallRadius, 2));
        for(int i = 0; i < densities.length; i++) {
            double density = densities[i];
            int particleQuantity = Math.max((int)Math.floor(density * totalArea), 1);
            for(int j = 0; j < runs; j++) {
                String resultFilePath = path + "density_" + i + "_run" + j + ".txt";
                FileWriter resultFile = new FileWriter(resultFilePath);
                BufferedWriter resultWriter = new BufferedWriter(resultFile);
                resultWriter.write("time,positionx,positiony,speedProjected,radius,density\n");
                ContractileParticleModel contractileParticleModel = new ContractileParticleModel(internalWallRadius, externalWallRadius, particleQuantity, 0);
                contractileParticleModel.setBeta(beta);
                contractileParticleModel.performWritingOutput(100, 30, resultWriter, density);
                resultWriter.close();
            }
        }

    }
}
