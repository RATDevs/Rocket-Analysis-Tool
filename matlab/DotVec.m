% author: Michael Sams
function [ SysVec ] = DotVec( InpVec, F_T_K, m, planet, tn )
    
    % System an nichtlinearen DGLs 1.Ordnung
    %         [ lambda_dot ]
    %         | Phi_dot    |
    %         | h_dot      | 
    % f^dot = | V_dot      |
    %         | chi_dot    |
    %         | gamma_dot  |
    %         [ s_dot      ]

    N_phi = planet.a/(sqrt(1-planet.e^2*sin(InpVec(2,1))^2));
    M_phi = N_phi*(1-planet.e^2)/(1-planet.e^2*sin(InpVec(2,1))^2);
    
    lambda_dot = sin(InpVec(5,1))*cos(InpVec(6,1))*InpVec(4,1) / ...
        ((N_phi+InpVec(3,1))*cos(InpVec(2,1)));
    
    Phi_dot = cos(InpVec(5,1))*cos(InpVec(6,1))*InpVec(4,1) / ...
        (M_phi + InpVec(3,1));
    
    h_dot = sin(InpVec(6,1))*InpVec(4,1);
    
    V_dot =  ( 2*F_T_K(1,1) + m*planet.omega^2 * ...
        ( 2*cos(InpVec(2,1))^2 * sin(InpVec(6,1)) - ...
        cos(InpVec(6,1))*cos(InpVec(5,1))*sin(2*InpVec(2,1))) * (InpVec(3,1)+N_phi)) / (2*m);   
    
    if( InpVec(6,1) < 85*pi/180 )
        chi_dot = 1/(InpVec(4,1)) * ( F_T_K(2,1)/(cos(InpVec(6,1))*m) +...
            1*(sin(InpVec(2,1))*InpVec(4,1)*(2*planet.omega+lambda_dot) -...
            cos(InpVec(2,1))*cos(InpVec(5,1))*InpVec(4,1)*(2*planet.omega+lambda_dot)*tan(InpVec(6,1)) + ...
            sin(InpVec(5,1))*( (planet.omega^2*cos(InpVec(2,1))*sin(InpVec(2,1))*(InpVec(3,1)+N_phi)) / cos(InpVec(6,1)) +...
            InpVec(4,1)*Phi_dot*tan(InpVec(6,1)))));
    else
        chi_dot = 0;
    end
    
    if( tn > 3 )
        gamma_dot = 1/(InpVec(4,1)) * (-F_T_K(3,1)/m +...
             InpVec(3,1)*planet.omega^2*cos(InpVec(2,1))*...
            ( cos(InpVec(6,1))*cos(InpVec(2,1)) + cos(InpVec(5,1))*sin(InpVec(6,1))*sin(InpVec(2,1)) )+...
            planet.omega^2*cos(InpVec(2,1))*...
            ( cos(InpVec(6,1))*cos(InpVec(2,1)) + cos(InpVec(5,1))*sin(InpVec(6,1))*sin(InpVec(2,1)) )*N_phi +...
            InpVec(4,1)*(cos(InpVec(2,1))*sin(InpVec(5,1))*(2*planet.omega+lambda_dot)+cos(InpVec(5,1))*Phi_dot));
    else 
        gamma_dot = 0;
    end
    
    s_dot = InpVec(4,1)*cos(InpVec(6,1))*N_phi/(N_phi+InpVec(3,1));
    
    % return
    SysVec = [lambda_dot; Phi_dot; h_dot; V_dot; chi_dot; gamma_dot; s_dot];
end