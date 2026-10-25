% author: Michael Sams
function [Isp] = IspCalc(inp, p, p0, A_bull, B_bull)
    if( inp.mode == 'BMTC' )
        % specific impulse
        Isp = inp.Ispvac-(inp.Ispvac-inp.Isp0)*(p)/(p0);
    elseif( inp.mode == 'BULL' )
        % Isp
        Isp = inp.Ispvac*(1-B_bull/A_bull*p/inp.pc);
    end 
end
