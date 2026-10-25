% author: Michael Sams
% PHI table
% OUTPUT:   PHI [rad]
% INPUT :   t [sec]; table
function [PHI] = PHItable( t , table , GammaHelp)
    % search for correct entries in phi-table
    line = 1; % line in table
    while( line < size(table,1) )
        if( t >= table(line,1) && t < table(line+1,1) )
            break;
        else
            line = line + 1;
        end
    end
    % Lagrange Polynom: n=1 (linear)
    if( t > table(end,1) )
        PHI = GammaHelp;
    elseif( t == 0 )
        PHI = table(1,2)*pi/180;
    elseif( t == table(end,1) )
        PHI = table(end,2)*pi/180;
    else
        PHI = table(line,2)*(t-table(line+1,1))/(table(line,1)-table(line+1,1))+...
              table(line+1,2)*(t-table(line,1))/(table(line+1,1)-table(line,1));
        PHI = PHI*pi/180;
    end
end
