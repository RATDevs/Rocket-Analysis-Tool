% author: Michael Sams
function [PHI, alpha, InpVec, F_P_B, m, tb_stage, stage] = RKV4O( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet )
      
    %% 1. Halbschritt-Praediktor mittels Vorwaerts-Euler
    % Krafte auswerten (tn, InpVec)
    [PHI, alpha, F_T_K, m, F_P_B, tb_stage, stage] = EvalForce( tn, phi_table, InpVec, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
    % f1(tn, InpVec)
    SysVec1 = DotVec( InpVec, F_T_K, m, planet, tn );
    % InpVec1
    InpVec1 = InpVec + deltat/2 * SysVec1;
    
    %% 2. Halbschritt-Korrektor mittels Rueckwaerts-Euler
    tn2 = tn + deltat/2;
    % Krafte auswerten (tn2, InpVec1)
    [~, ~, F_T_K2, m2, ~, ~, ~] = EvalForce( tn2, phi_table, InpVec1, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
    % f2(tn2, InpVec1)
    SysVec2 = DotVec( InpVec1, F_T_K2, m2, planet, tn2 );
    % InpVec2
    InpVec2 = InpVec + deltat/2 * SysVec2;
    
    %% 3. Mittelpunktregel als Praediktor
    % Krafte auswerten (tn2, InpVec2)
    [~, ~, F_T_K3, m3, ~, ~, ~] = EvalForce( tn2, phi_table, InpVec2, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
    % f3(tn2, InpVec2)
    SysVec3 = DotVec( InpVec2, F_T_K3, m3, planet, tn2 );
    % InpVec3
    InpVec3 = InpVec2 + deltat * SysVec3;
    
    %% 4. Simpson'sche Regel als endgueltigen Korrektor
    tn4 = tn + deltat;
    % Krafte auswerten (tn4, InpVec3)
    [~, ~, F_T_K4, m4, ~, ~, ~] = EvalForce( tn4, phi_table, InpVec3, AtmModel, GravModel,...
                                                tb_stage, stage, deltat, inp, ...
                                                A_bull, B_bull, aerData, m, p0, planet );
    % f4(tn4, InpVec3)
    SysVec4 = DotVec( InpVec3, F_T_K4, m4, planet, tn4 );
    
    % InpVec
    InpVec = InpVec + deltat/6 * (SysVec1 + 2*SysVec2 + 2*SysVec3 + SysVec4);
end