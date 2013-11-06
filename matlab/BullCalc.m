% author: Michael Sams
function [A_bull, B_bull] = BullCalc(inp)
    % Newton-solver
    eta0 = 0.2;     % start value
    prec = 10^-6;
    etai = eta0;    % solution
    loop = 1;
    i = 2;          % iterator
    while( loop == 1 && i<1000)
        eta_ip1(i) = etai + inp.n^inp.n/(inp.eps^2*(inp.n+1)^(inp.n+1)*(1-etai)^inp.n*(-inp.n*etai/(1-etai)+1)) -...
                    etai/(-inp.n*etai/(1-etai)+1);
        etai = eta_ip1(i);
        if( (eta_ip1(i) - eta_ip1(i-1)) < prec )
            loop = 0;
        end
        i = i + 1;
    end
    % C_inf
    C_inf = (inp.n+2)*sqrt(inp.n^inp.n/((inp.n+1)^(inp.n+1)));
    % p_e
    p_e = inp.pc*(1-etai)^((inp.n+2)/2);
    %
    A_bull = sqrt(etai)+inp.eps*p_e/(C_inf*inp.pc);
    B_bull = inp.eps/C_inf;
end
