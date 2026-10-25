% author: Michael Sams
clear all; clc;
tic;

% struct def
inp = struct( 'stage', 0, 'dref', 0, 'm0', 0, 'mdot', 0, 'Isp0', 0, 'Ispvac', 0, 'tb', 0,...
                'pc', 0, 'eps', 0, 'n', 0, 'mode', '');
aerData = struct('stage', 0, 'k', 0, 'mach', 0, 'CD0EngON', 0, 'CD0EngOFF', 0, 'CLalpha', 0);
%
% constants
omega = 7.292115*10^-5;    % angular speed of earth's rotation [rad/s]
a = 6378137;            % semi-major axis
b = 6356752.3142;       % semi-minor axis
f = (a-b)/a;            % earth flattening
e = sqrt(f*(2-f));      % first excentricity
gSI = 9.80665;
planet = struct('omega', omega, 'a', a, 'b', b, 'f', f, 'e', e, 'gSI', gSI);

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Atmosphere and gravitational models
AtmModel = 'CIRA2012';
GravModel = 'Newton';
%
% Input
% stage 1
inp(1).stage = 1;          % stage
inp(1).dref = 2.4;        % reference diameter [m]
inp(1).m0 = 66200;          % initial mass [kg]
inp(1).Isp0 = 224.156;      % specific impulse at ground [s]
inp(1).Ispvac = 224.156;    % specitif impulse in vacuum [s]
inp(1).mdot = 500;       % fuel mass flow [kg/m^3]
inp(1).tb = 120;            % burn time [s]
inp(1).pc = 55*10^5;      % chamber pressure [Pa]
inp(1).eps = 9.3;        % A_e/A_t
inp(1).n = 10;              % thermodynamic dof of propellant n=2/(k-1)
inp(1).mode = 'BMTC';      % T0-mode
% stage 2
inp(2).stage = 2;          % stage
inp(2).dref = 1.25;        % reference diameter [m]
inp(2).m0 = 13700;          % initial mass [kg]
inp(2).Isp0 = 230.223;      % specific impulse at ground [s]
inp(2).Ispvac = 230.223;    % specitif impulse in vacuum [s]
inp(2).mdot = 59;       % fuel mass flow [kg/m^3]
inp(2).tb = 200;            % burn time [s]
inp(2).pc = 68*10^5;      % chamber pressure [Pa]
inp(2).eps = 10.32;        % A_e/A_t
inp(2).n = 10;              % thermodynamic dof of propellant
inp(2).mode = 'BMTC';      % T0-mode
% stage 3
inp(3).stage = 3;          % stage
inp(3).dref = 0.88;        % reference diameter [m]
inp(3).m0 = 3550;          % initial mass [kg]
inp(3).Isp0 = 290.49;      % specific impulse at ground [s]
inp(3).Ispvac = 290.49;    % specitif impulse in vacuum [s]
inp(3).mdot = 11.538;       % fuel mass flow [kg/m^3]
inp(3).tb = 260;            % burn time [s]
inp(3).pc = 55*10^5;      % chamber pressure [Pa]
inp(3).eps = 9.3;        % A_e/A_t
inp(3).n = 10;              % thermodynamic dof of propellant
inp(3).mode = 'BMTC';      % T0-mode
% stage 4
inp(4).stage = 4;          % stage
inp(4).dref = 0.6;        % reference diameter [m]
inp(4).m0 = 100;          % initial mass [kg]
inp(4).Isp0 = 0;      % specific impulse at ground [s]
inp(4).Ispvac = 0;    % specitif impulse in vacuum [s]
inp(4).mdot = 0;       % fuel mass flow [kg/m^3]
inp(4).tb = 0;            % burn time [s]
inp(4).pc = 0;      % chamber pressure [Pa]
inp(4).eps = 0;        % A_e/A_t
inp(4).n = 10;              % thermodynamic dof of propellant
inp(4).mode = 'BMTC';      % T0-mode

% calc A_bull & B_bull if inp(stage).mode == BULL
for i=1:size(inp,2)
    if( strcmp(inp(i).mode,'BULL') && inp(i).tb~=0 )
        [A_bull(i), B_bull(i)] = BullCalc(inp(i));
    else
        A_bull(i) = 0;
        B_bull(i) = 0;
    end
end

% Startpunkt: WGS84-Koordinaten
% 48°21'14"N 11°47'10"E - EDDM
Lat = 39.66;  % latitude
Long = 124.705; % longitude

% PHI table
phi_table = [
0, 90;
3, 90;
20, 87;
120, 60;
200, 42;
320, 15;
440, 6;
580, 3];

% Aero-table
% stage 1
aerData(1).stage = 1;
aerData(1).k = 0.0;
aerData(1).mach =       [   0.00, 0.30, 0.60, 0.80, 0.90, 1.00, 1.10, 1.20, 1.30, 1.40, 1.50, 2.00, 3.00, 4.00, 5.00, 6.00, 8.00, 10.00];
aerData(1).CD0EngON =   [   0.33, 0.33, 0.33, 0.34, 0.38, 0.44, 0.45, 0.45, 0.45, 0.44, 0.43, 0.36, 0.27, 0.23, 0.20, 0.19, 0.17, 0.16];
aerData(1).CD0EngOFF =  [   0.36, 0.36, 0.36, 0.37, 0.43, 0.48, 0.49, 0.49, 0.48, 0.47, 0.46, 0.39, 0.30, 0.25, 0.22, 0.20, 0.19, 0.18];
aerData(1).CLalpha =    [   8.30, 8.39, 8.74, 8.98, 9.50, 9.51, 9.76, 8.82, 7.44, 6.80, 6.53, 5.83, 5.44, 4.58, 4.40, 4.30, 4.20, 4.10];
% stage 2
aerData(2).stage = 2;
aerData(2).k = aerData(1).k;
aerData(2).mach = aerData(1).mach;
aerData(2).CD0EngON = aerData(1).CD0EngON;
aerData(2).CD0EngOFF = aerData(1).CD0EngOFF;
aerData(2).CLalpha = aerData(1).CLalpha;
% stage 3
aerData(3).stage = 3;
aerData(3).k = aerData(1).k;
aerData(3).mach = aerData(1).mach;
aerData(3).CD0EngON = aerData(1).CD0EngON;
aerData(3).CD0EngOFF = aerData(1).CD0EngOFF;
aerData(3).CLalpha = aerData(1).CLalpha;
% stage 4
aerData(4).stage = 4;
aerData(4).k = aerData(1).k;
aerData(4).mach = aerData(1).mach;
aerData(4).CD0EngON = aerData(1).CD0EngON;
aerData(4).CD0EngOFF = aerData(1).CD0EngOFF;
aerData(4).CLalpha = aerData(1).CLalpha;
                        
% atmosphere at sea level
[dens0,temp0,p0,a0] = ATMOSPHERE(0, AtmModel);

% timestep
deltat = 0.1; % [s]
% Output-timestep
t_out_incr = 1; % [s]
% Abbruchkrit - Zeit
t_off = 10*10^3;

% initial-values
t_0 = 0.0;                % initial time
h0_E = 0;               % height above WGS84 ellipsoid (E-system)
h_O = 0;                % initial height (O-system)
V0_K = 0;               % initial speed (K-system)
lambda0 = Long*pi/180;  % geodetic longitude
phi0 = Lat*pi/180;      % geodetic latitude
alpha0 = 0*pi/180;      % initial angle-of-attack (A=K)
chi0 = 180*pi/180;        % flight direciton --> reference to true north - 0deg=North, 90deg=East

% set-up initial-vectors
% time
tn = t_0;
% mass
m = 0;
for i = 1:size(inp,2)
    m = m + inp(i).m0;
end
% angle-of-attack
alpha = alpha0;
chi = chi0;
% rocket angle
gamma = PHItable( t_0, phi_table, phi_table(1,2));
% speed: K-system
%V_K_K = [0; 0; V0_K];
% acceleration: K-system
%Vdot_K_K = [0; 0; 0];
% surface position-vector (E-system)
%rS_E = [0; 0; 0];
% rocket position-vector (E-system)
%r_E = [0; 0; 0];
%r_I = [0; 0; 0];
% rocket position-vector (O-system, WGS84)
%WGS84_O = [lambda0; phi0; h0_E+h_O];
% speed of rocket position-vector (O-system, WGS84)
%WGS84dot_O = [0; 0; 0];
% thrust: B-system
F_P_B = [0; 0; 0];
% flown ground distance
s = 0;
% mach-number
Ma = 0;

% Input (System)-Vector
InpVec = [lambda0; phi0; h0_E+h_O; V0_K; chi; gamma; s];

%% Output_variables
out_count = 1;
tn_out(1) = tn;
m_out(1) = m;
PHI_out(1) = PHItable( tn, phi_table, gamma);
alpha_out(1) = alpha;
gamma_out(1) = gamma;
V_out(1) = V0_K;
h_out(1) = 0;
%r_E_out(:,1) = r_E;
%rS_E_out(:,1) = rS_E;
%r_I_out(:,1) = r_I;
%WGS84_out(:,1) = WGS84_O;
F_P_B_out(1) = F_P_B(1,1);
Ma_out(1) = Ma;
s_out(1) = s;

% DGL-Solver
DGLSolver = 'RKV';

% loop
stage = 1;  % beginn with 1st stage
n=1;        % iteration count
tb_stage = inp(stage).tb;
while( InpVec(3,1)>= 0 && tn<t_off )
    
    if( strcmp( DGLSolver, 'VorwEuler' ) )
        % Vorwaerts-Euler
        %try
            [PHI, alpha, InpVec, F_P_B, m, tb_stage, stage] = VorwEuler( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
        %catch exception
        %    break;
        %end
    elseif( strcmp( DGLSolver, 'RKV' ) )
        % Runge-Kutta-4.Ordnung
        % Rechnung beendet mit einer Exception - da im RK-Zwischenritt eine 
        % negative Hoehe in ATMOSPHERE() zum abbruch fuehrt! --> noch nicht
        % schoen!
        try
            [PHI, alpha, InpVec, F_P_B, m, tb_stage, stage] = RKV4O( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                    tb_stage, stage, deltat, inp, ...
                                                    A_bull, B_bull, aerData, m, p0, planet );
        catch exception
            break;
        end
    end

    %%
    
    % time
    tn = tn + deltat;
    
    % Output every 't_out_incr'
    if( n>1 && (n*deltat-out_count*t_out_incr)==0 )
        out_count = out_count + 1;
        %
        tn_out(out_count) = tn;
        m_out(out_count) = m;
        alpha_out(out_count) = alpha;
        gamma_out(out_count) = InpVec(6,1);
        PHI_out(out_count) = PHI;
        V_out(out_count) = InpVec(4,1);
        h_out(out_count) = InpVec(3,1);
        %r_E_out(:,out_count) = r_E;
        %rS_E_out(:,out_count) = rS_E;
        %r_I_out(:,out_count) = r_I;
        %WGS84_out(:,out_count) = WGS84_O;
        F_P_B_out(out_count) = F_P_B(1,1);
        %Ma_out(out_count) = Ma;
        s_out(out_count) = InpVec(7,1);
    end
    
    % loop variable 
    n = n + 1;
end

%%
OUT_TXT = {'time', 'distance', 'height', 'velocity', 'phi', 'gamma', 'alpha', 'thrust', 'mass'};
OUT(:,1)=tn_out;
OUT(:,2)=s_out/1000;
OUT(:,3)=h_out/1000;
OUT(:,4)=V_out(1,:);
OUT(:,5)=PHI_out*180/pi;
OUT(:,6)=gamma_out*180/pi;
OUT(:,7)=alpha_out*180/pi;
OUT(:,8)=F_P_B_out;
OUT(:,9)=m_out;

%% Plot 
% altitude - distance
figure;
plot(OUT(:,2),OUT(:,3));
xlabel('s - distance [km]');
ylabel('h - height [km]');
axis([0, max(OUT(:,2))+10,0,max(OUT(:,3))+10]);
grid on;
% time - speed
figure;
[AX,H1,H2] = plotyy(OUT(:,1),OUT(:,4), OUT(:,1), OUT(:,3));
set(get(AX(1), 'Ylabel'),'String', 'v - velocity [m/s]');
set(get(AX(2), 'Ylabel'),'String', 'h - height [m]');
xlabel('t - time [s]');
grid on;
% altitude - speed
figure;
plot(OUT(:,4),OUT(:,3));
xlabel('s - distance [km]');
ylabel('v - velocity [m/s]');
grid on;
%
figure;
plot(OUT(:,1),OUT(:,6), OUT(:,1),OUT(:,5));
xlabel('t - time [s]');
ylabel('gamma and phi - angle [deg]');
legend('gamma', 'phi');
grid on;
%
figure;
plot(OUT(:,1),OUT(:,7));
xlabel('t - time [s]');
ylabel('alpha - angle [deg]');
grid on;
%
%figure;
%plot(OUT(:,1),Ma);
%xlabel('t - time [s]');
%ylabel('mach');
%grid on;
%
figure;
[AX,H1,H2] = plotyy(OUT(:,1), OUT(:,8), OUT(:,1), OUT(:,9));
set(get(AX(1), 'Ylabel'),'String', 'Thrust');
set(get(AX(2), 'Ylabel'),'String', 'mass');
xlabel('t - time [s]');
grid on;

%% Text Output
fid = fopen('OUTPUT_satellit_System.txt', 'w+');
fprintf(fid, '%s,%s,%s,%s,%s,%s,%s,%s,%s\n', OUT_TXT{1},OUT_TXT{2},OUT_TXT{3},OUT_TXT{4},OUT_TXT{5},OUT_TXT{6},OUT_TXT{7},OUT_TXT{8},OUT_TXT{9});
for i=1:size(OUT,1)
    fprintf(fid, '%.2f,%.2f,%.2f,%.2f,%.4f,%.4f,%.4f,%.2f,%.2f\n', OUT(i,1),OUT(i,2),OUT(i,3),OUT(i,4),OUT(i,5),OUT(i,6),OUT(i,7),OUT(i,8),OUT(i,9));
end
fclose(fid);
toc;