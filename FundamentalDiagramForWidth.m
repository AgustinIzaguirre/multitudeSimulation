MeanSpeed = zeros(1,47);
StdSpeed = zeros(1,47);
Density = zeros(1,47);
totalArea = pi * 12;
densities = 47;
runs = 4;
WidthMeanSpeed = zeros(5,47);
WidthStdSpeed = zeros(5,47);

for ancho = 1 : 1 : 5
  if (ancho != 2)
    for i = 0 : 1 : densities - 1
      CurrentSpeed = [];
      i

      for j = 0 : 1 : runs - 1
        filePath = strcat("Output/Results/ancho", num2str(ancho), "/density_", num2str(i), "_run", num2str(j), ".txt");
        A = csvread(filePath);
        CurrentSpeed = [CurrentSpeed A(:, 4)'];
        currentDensity = A(2, 6);
      endfor
      MeanSpeed(i + 1) = mean(CurrentSpeed);
      StdSpeed(i + 1) = std(CurrentSpeed);
      Density(i + 1) = currentDensity;
    endfor
    WidthMeanSpeed(ancho,:) = MeanSpeed(1,:);
    WidthStdSpeed(ancho,:) = StdSpeed(1,:);
  endif
endfor

save widthMeanSpeed.mat WidthMeanSpeed
save widthStdSpeed.mat WidthStdSpeed


# Fundamental Diagram
scatter(Density, WidthMeanSpeed(1,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, WidthMeanSpeed(1,:), WidthStdSpeed(1,:), "k");
print -djpg ./FundamentalDiagramWidth1.jpg
close();

# Fundamental Diagram
scatter(Density, WidthMeanSpeed(2,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, WidthMeanSpeed(2,:), WidthStdSpeed(2,:), "k");
print -djpg ./FundamentalDiagramWidth2.jpg
close();

# Fundamental Diagram
scatter(Density, WidthMeanSpeed(3,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, WidthMeanSpeed(3,:), WidthStdSpeed(3,:), "k");
print -djpg ./FundamentalDiagramWidth3.jpg
close();

# Fundamental Diagram
scatter(Density, WidthMeanSpeed(4,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, WidthMeanSpeed(4,:), WidthStdSpeed(4,:), "k");
print -djpg ./FundamentalDiagramWidth4.jpg
close();

# Fundamental Diagram
scatter(Density, WidthMeanSpeed(5,:),"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, WidthMeanSpeed(5,:), WidthStdSpeed(5,:), "k");
print -djpg ./FundamentalDiagramWidth5.jpg
close();