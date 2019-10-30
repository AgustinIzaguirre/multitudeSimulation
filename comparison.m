load betasSpeed.mat
load betasStd.mat

Densities = [0.088,0.177,0.354,0.531,0.708,0.885,1.062,1.239,1.416,1.593,1.770,1.947,2.124,2.301,2.478,2.655,2.832,3.009,3.186,3.363,3.540,3.717,3.894,4.071,4.248,4.425,4.602,4.779,4.956,5.133,5.310,5.487,5.664,5.841,6.018,6.195,6.372,6.549,6.726,6.903,7.080,7.257,7.434,7.611,7.788,7.965,8.142];
ExpectedSpeed = [0.915,0.881,0.817,0.758,0.704,0.655,0.610,0.569,0.532,0.498,0.468,0.441,0.417,0.396,0.377,0.360,0.345,0.333,0.321,0.312,0.303,0.296,0.290,0.284,0.279,0.275,0.271,0.268,0.264,0.261,0.258,0.255,0.251,0.247,0.243,0.239,0.234,0.228,0.222,0.215,0.208,0.200,0.191,0.182,0.172,0.162,0.151];
Betas = 0.1 : 0.1 : 1.0;


Error = [];
for beta = 1 : 1 : 10
  currentError = 0;
  for i = 1 : 1 : 47
  currentError = currentError + (ExpectedSpeed(i) - BetaMeanSpeed(beta,i)).^ 2;
  endfor
  currentError = currentError / 47;
  Error = [Error, currentError];
endfor

Error
figure(1)
scatter(Betas, Error);
xlabel("Valores de beta", "fontsize", 20);
ylabel("Error cuadratico medio", "fontsize", 20);

print -djpg ./BetaError.jpg;


Betas = [0.7,0.8 0.9 1.0]
Error = [Error(7) Error(8) Error(9) Error(10)]
figure(2)
scatter(Betas, Error);
xlabel("Valores de beta", "fontsize", 20);
ylabel("Error cuadratico medio", "fontsize", 20);

print -djpg ./BetaErrorAcercamiento.jpg;


figure(2)
scatter(Densities, BetaMeanSpeed(8,:));
hold on;

predtechenski = scatter(Densities, ExpectedSpeed)

xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Densities, BetaMeanSpeed(8,:), BetaStdSpeed(8,:), "k");
h = legend([predtechenski],"Predtechenskii and Milinskii");
set(h, "fontsize", 20);
print -djpg ./diference.jpg;
