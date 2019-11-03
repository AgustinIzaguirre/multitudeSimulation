VelocityTimes = 1.0 : 5.0 : 100;
Densities = zeros(1,3);
MeanSpeed = zeros(3, 20);
StdSpeed = zeros(3, 20);

particlesQuantity = 3;
filePath = "Output/Results/velocity/density_1_run0.txt"
A = csvread(filePath);
Densities(1,1) = A(2,6);

for i = 1 : 1 : 20
  CurrentSpeed = [];
  j = particlesQuantity * (i - 1) + 1;
  for particle = 1 : 1 : particlesQuantity
    CurrentSpeed = [CurrentSpeed A(j , 4)];
    j = j + 1;
  endfor
  MeanSpeed(1, i) = mean(CurrentSpeed);
  StdSpeed(1, i) = std(CurrentSpeed);
endfor

particlesQuantity = 133;
filePath = "Output/Results/velocity/density_20_run0.txt"
A = csvread(filePath);
Densities(1,2) = A(2,6);

for i = 1 : 1 : 20
  CurrentSpeed = [];
  j = particlesQuantity * (i - 1) + 1;
  for particle = 1 : 1 : particlesQuantity
    CurrentSpeed = [CurrentSpeed A(j , 4)];
    j = j + 1;
endfor
  MeanSpeed(2, i) = mean(CurrentSpeed);
  StdSpeed(2, i) = std(CurrentSpeed);
endfor

particlesQuantity = 266;
filePath = "Output/Results/velocity/density_40_run0.txt"
A = csvread(filePath);
Densities(1,3) = A(2,6);

for i = 1 : 1 : 20
  CurrentSpeed = [];
  j = particlesQuantity * (i - 1) + 1;
  for particle = 1 : 1 : particlesQuantity
    CurrentSpeed = [CurrentSpeed A(j , 4)];
    j = j + 1;
  endfor
  MeanSpeed(3, i) = mean(CurrentSpeed);
  StdSpeed(3, i) = std(CurrentSpeed);
endfor

MeanSpeed

#use one MeanSpeed with three rows for each Density
denstity1 = scatter(VelocityTimes, MeanSpeed(1,:), "k");
hold on;
denstity2 = scatter(VelocityTimes, MeanSpeed(2,:), "b");
denstity3 = scatter(VelocityTimes, MeanSpeed(3,:), "r");

xlabel("Tiempo (s)", "fontsize", 20);
ylabel("Velocidad (m / s)", "fontsize", 20);
#errorbar(VelocityTimes, MeanSpeed(1,:), StdSpeed(1,:), "k");
#errorbar(VelocityTimes, MeanSpeed(2,:), StdSpeed(2,:), "b");
#errorbar(VelocityTimes, MeanSpeed(3,:), StdSpeed(3,:), "r");

h = legend([denstity1,denstity2,denstity3],{'3 partículas','133 partículas','266 partículas'}, "location", "northeastoutside");
set(h, "interpreter", "latex");

print -djpg ./velocityDiagram.jpg;

#do for 20 and 40