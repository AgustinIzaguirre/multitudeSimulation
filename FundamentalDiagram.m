MeanSpeed = zeros(1,10);
StdSpeed = zeros(1,10);
Density = zeros(1,10);
totalArea = pi * 12;


filePath = "Output/Results/5_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(1) = mean(CurrentSpeed);
StdSpeed(1) = std(CurrentSpeed);
Density(1) = 5 / totalArea;


filePath = "Output/Results/10_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(2) = mean(CurrentSpeed);
StdSpeed(2) = std(CurrentSpeed);
Density(2) = 10 / totalArea;


filePath = "Output/Results/15_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(3) = mean(CurrentSpeed);
StdSpeed(3) = std(CurrentSpeed);
Density(3) = 15 / totalArea;

filePath = "Output/Results/20_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(4) = mean(CurrentSpeed);
StdSpeed(4) = std(CurrentSpeed);
Density(4) = 20 / totalArea;

filePath = "Output/Results/25_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(5) = mean(CurrentSpeed);
StdSpeed(5) = std(CurrentSpeed);
Density(5) = 25 / totalArea;
filePath = "Output/Results/30_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(6) = mean(CurrentSpeed);
StdSpeed(6) = std(CurrentSpeed);
Density(6) = 30 / totalArea;

filePath = "Output/Results/35_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(7) = mean(CurrentSpeed);
StdSpeed(7) = std(CurrentSpeed);
Density(7) = 35 / totalArea;


filePath = "Output/Results/40_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(8) = mean(CurrentSpeed);
StdSpeed(8) = std(CurrentSpeed);
Density(8) = 40 / totalArea;

filePath = "Output/Results/45_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(9) = mean(CurrentSpeed);
StdSpeed(9) = std(CurrentSpeed);
Density(9) = 45 / totalArea;

filePath = "Output/Results/50_ext4.0_int2.0_run0.txt";
A = csvread(filePath);
CurrentSpeed = A(:, 4);
CurrentDensity = A(:, 6)
MeanSpeed(10) = mean(CurrentSpeed);
StdSpeed(10) = std(CurrentSpeed);
Density(10) = 50 / totalArea;



# Fundamental Diagram
scatter(Density, MeanSpeed,"k");
xlabel("Densidad (1 / m^2)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
hold on;
errorbar(Density, MeanSpeed, StdSpeed, "k");
print -djpg ./FundamentalDiagram.jpg;


