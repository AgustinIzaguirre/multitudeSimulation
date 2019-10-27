MeanSpeed = zeros(1,10);
StdSpeed = zeros(1,10);
Density = zeros(1,10);
totalArea = pi * 12;
densities = 47;
runs = 4;


for i = 0 : 1 : densities - 1
  CurrentSpeed = [];
  i

  for j = 0 : 1 : runs - 1
    filePath = strcat("Output/Results/density_", num2str(i), "_run", num2str(j), ".txt");
    A = csvread(filePath);
    CurrentSpeed = [CurrentSpeed A(:, 4)'];
    currentDensity = A(2, 6);
  endfor

  MeanSpeed(i + 1) = mean(CurrentSpeed);
  StdSpeed(i + 1) = std(CurrentSpeed);
  Density(i + 1) = currentDensity;
endfor

MeanSpeed
StdSpeed
Density
# Fundamental Diagram
scatter(Density, MeanSpeed,"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, MeanSpeed, StdSpeed, "k");
print -djpg ./FundamentalDiagram.jpg;


