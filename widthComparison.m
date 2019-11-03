load betasSpeed.mat
load betasStd.mat
load widthMeanSpeed.mat
load widthStdSpeed.mat

Densities = [0.088,0.177,0.354,0.531,0.708,0.885,1.062,1.239,1.416,1.593,1.770,1.947,2.124,2.301,2.478,2.655,2.832,3.009,3.186,3.363,3.540,3.717,3.894,4.071,4.248,4.425,4.602,4.779,4.956,5.133,5.310,5.487,5.664,5.841,6.018,6.195,6.372,6.549,6.726,6.903,7.080,7.257,7.434,7.611,7.788,7.965,8.142];

# Fundamental Diagram
ancho1 = scatter(Densities, WidthMeanSpeed(1,:),"k");
hold on;
ancho2 = scatter(Densities, BetaMeanSpeed(9,:),"r");
ancho3 = scatter(Densities, WidthMeanSpeed(3,:),"g");
ancho4 = scatter(Densities, WidthMeanSpeed(4,:),"b");
ancho5 = scatter(Densities, WidthMeanSpeed(5,:),"10");
#errorbar(Densities, WidthMeanSpeed(1,:), WidthStdSpeed(1,:), "k")
#errorbar(Densities, BetaMeanSpeed(9,:), BetaStdSpeed(9,:), "r")
#errorbar(Densities, WidthMeanSpeed(3,:), WidthStdSpeed(3,:), "g")
#errorbar(Densities, WidthMeanSpeed(4,:), WidthStdSpeed(4,:), "b")
#errorbar(Densities, WidthMeanSpeed(5,:), WidthStdSpeed(5,:), "10")


xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
h = legend([ancho1,ancho2,ancho3,ancho4,ancho5],{'Ancho de 1 m','Ancho de 2 m','Ancho de 3 m','Ancho de 4 m','Ancho de 5 m'});

set(h, "fontsize", 20);
print -djpg ./WidthDifference.jpg
close();