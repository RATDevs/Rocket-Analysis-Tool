% author: Michael Sams

function [CL, CD] = AeroTable(engine, alpha, mach, data)
    % search for correct mach-number
    pos = 1;
    while( pos < length(data.mach) )
        if( mach >= data.mach(pos) && mach < data.mach(pos+1) )
            break;
        else
            pos = pos + 1;
        end
    end
    % Lagrange Polynom: n=1 (linear)
    if( mach >= data.mach(end) )
        CL = data.CLalpha(end)*alpha;
        if( engine == 1 )
            CD = data.CD0EngON(end) + data.k*CL^2;
        else
            CD = data.CD0EngOFF(end) + data.k*CL^2;
        end
    elseif( mach == 0 )
        CL = data.CLalpha(1)*alpha;
        CD = data.CD0EngON(1) + data.k*CL^2;
    else
        CLa = data.CLalpha(1,pos)*(mach-data.mach(1,pos+1))/(data.mach(1,pos)-data.mach(1,pos+1))+...
            data.CLalpha(1,pos+1)*(mach-data.mach(1,pos))/(data.mach(1,pos+1)-data.mach(1,pos));
        CL = CLa*alpha;
        if( engine == 1 )
            CD0 = data.CD0EngON(pos)*(mach-data.mach(pos+1))/(data.mach(pos)-data.mach(pos+1))+...
                data.CD0EngON(pos+1)*(mach-data.mach(pos))/(data.mach(pos+1)-data.mach(pos));
            CD = CD0 + data.k*CL^2;
        else
            CD0 = data.CD0EngOFF(pos)*(mach-data.mach(pos+1))/(data.mach(pos)-data.mach(pos+1))+...
                data.CD0EngOFF(pos+1)*(mach-data.mach(pos))/(data.mach(pos+1)-data.mach(pos));
            CD = CD0 + data.k*CL^2;
        end
    end
end
