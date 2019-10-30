MeanSpeed = zeros(1,47);
StdSpeed = zeros(1,47);
Density = zeros(1,47);
totalArea = pi * 12;
densities = 47;
runs = 4;
BetaMeanSpeed = zeros(10, 47);
BetaStdSpeed = zeros(10, 47);

for beta = 1 : 1 : 10
  #if (beta == 9)
    for i = 0 : 1 : densities - 1
      CurrentSpeed = [];
      i

      for j = 0 : 1 : runs - 1
        filePath = strcat("Output/Results/beta0_", num2str(beta), "/density_", num2str(i), "_run", num2str(j), ".txt");
        A = csvread(filePath);
        CurrentSpeed = [CurrentSpeed A(:, 4)'];
        currentDensity = A(2, 6);
      endfor

      MeanSpeed(i + 1) = mean(CurrentSpeed);
      StdSpeed(i + 1) = std(CurrentSpeed);
      Density(i + 1) = currentDensity;
    endfor
    BetaMeanSpeed(beta, :) = MeanSpeed(1,:);
    BetaStdSpeed(beta, :) = StdSpeed(1,:);
  #endif
endfor

MeanSpeed
StdSpeed
Density
# Fundamental Diagram
scatter(Density, BetaMeanSpeed(9,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, BetaMeanSpeed(9,:), BetaStdSpeed(9,:), "k");
print -djpg ./FundamentalDiagram.jpg;


