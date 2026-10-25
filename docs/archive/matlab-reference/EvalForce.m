% author: Michael Sams
function [PHI, alpha, F_T_K, m, F_P_B, tb_stage, stage] = EvalForce( tn, phi_table, InpVec, AtmModel, GravModel, tb_stage, stage, deltat, inp, ...
    A_bull, B_bull, aerData, m, p0, planet )

    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % alpha and phi
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    PHI = PHItable( tn, phi_table, InpVec(6,1));
    %fprintf('%.2f\t%.10f\t%.10f\t%.10f\t%.10f\n',tn,gamma*180/pi,PHI*180/pi,chi*180/pi,alpha*180/pi);
    alpha = PHI-InpVec(6,1);
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Atmosphere + GravModel
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    [dens,~,p,a_sound] = ATMOSPHERE(InpVec(3,1), AtmModel);
    [g_O, ~] = GRAVMODEL(InpVec(3,1), InpVec(2,1), GravModel);
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Trafo-Matrizen
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Trafo-Matrix: B-->K
    M_KB = [cos(alpha), 0, sin(alpha);
            0, 1, 0;
            -sin(alpha), 0, cos(alpha)];
    % Trafo-Matrix: O-->K
    M_KO = [cos(InpVec(5,1))*cos(InpVec(6,1)), sin(InpVec(5,1))*cos(InpVec(6,1)), -sin(InpVec(6,1));
            -sin(InpVec(5,1)), cos(InpVec(5,1)), 0;
            cos(InpVec(5,1))*sin(InpVec(6,1)), sin(InpVec(5,1))*sin(InpVec(6,1)), cos(InpVec(6,1))];
    
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    %% Kraefte
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % get correct stage
    % range: (tn<tb_stage+deltat/2 && tn>tb_stage-deltat/2) --> Matlab
    % trifft die Zeit nicht genau!?
    if( (tn<tb_stage+deltat/2 && tn>tb_stage-deltat/2) && size(inp,2)>1 && stage<size(inp,2) )
        stage = stage + 1; % next stage
        tb_stage = tb_stage+inp(stage).tb; % complete burn-time until start
        m = m-(inp(stage-1).m0-inp(stage-1).tb*inp(stage-1).mdot); % seperate stage
        % Isp
        if( inp(stage).tb == 0 )
            Isp = 0;
        else
            Isp = IspCalc(inp(stage), p, p0, A_bull(stage), B_bull(stage));
        end
        % Schub: P-System
        F_P_B = [Isp*planet.gSI*inp(stage).mdot; 0; 0];
        % mass
        m = m-deltat*inp(stage).mdot;
        engine = 1;
    elseif( tn<tb_stage )
        % Isp
        if( inp(stage).tb == 0 )
            Isp = 0;
        else
            Isp = IspCalc(inp(stage), p, p0, A_bull(stage), B_bull(stage));
        end
        % Schub: P-System
        F_P_B = [Isp*planet.gSI*inp(stage).mdot; 0; 0];
        % mass
        m = m-deltat*inp(stage).mdot;
        engine = 1;
    else
        % Schub: P-System
        F_P_B = [0; 0; 0];
        % Masse 
        %m(n+1) = m(n);
        engine = 0;
    end
    % Schub: K-System
    F_P_K = M_KB*F_P_B;
 
    % Gravitationskraft: O-System
    F_G_O = m*g_O;
    % Gravitationskraft: K-System
    F_G_K = M_KO*F_G_O;
   
    % Lift and Drag: A/K-System
    % --> keine Querbewegung: C_Q=0
    % V_A = V_K, weil A=K
    Ma = InpVec(4,1)/a_sound;
    % calculate lift- and drag-coefficient
    [C_L, C_D] = AeroTable(engine, alpha, Ma, aerData(stage));
    % aerodynamic forces
    F_A_K = dens/2*InpVec(4,1)^2*(inp(stage).dref^2*pi/4)*[-C_D; 0; -C_L];
    
    % Gesamtkraft: K-System
    F_T_K = F_P_K+F_G_K+F_A_K;
end