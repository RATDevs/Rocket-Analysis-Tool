% author: Michael Sams
function [PHI, alpha, InpVec, F_P_B, m, tb_stage, stage] = VorwEuler( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet )
      
    %% Vorwaerts-Euler
    % Krafte auswerten (tn, InpVec)
    [PHI, alpha, F_T_K, m, F_P_B, tb_stage, stage] = EvalForce( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
    % f1(tn, InpVec)
    SysVec = DotVec( InpVec, F_T_K, m, planet, tn );
    % InpVec1
    InpVec = InpVec + deltat * SysVec;
    
end