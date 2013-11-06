% author: Michael Sams
function [dens, T, p, a] = ATMOSPHERE( h, model )
    if( strcmp(model,'BMTC') )
        h = h/1000; % in [km]
        % Temperature
        if( h<11 )
            T = 288.15-6.5053*h;
        elseif( h>=11 && h<20 )
            T = 217;
        elseif( h>=20 && h<32 )
            T = h+197;
        elseif( h>=32 && h<47 )
            T = 2.8*h+139.4;
        elseif( h>=47 && h<51 )
            T = 271;
        elseif( h>=51 && h<71 )
            T = -2.8*h+413.8;
        elseif( h>=71 && h<86 )
            T = -2*h+357;
        elseif( h>=86 && h<90 )
            T = -h+273;
        elseif( h>=90 && h<100 )
            T = 1.2*h+75;
        else
            T = 5.3*h-335;
        end
        % Pressure
        if( h>=0 && h<5 )
            p = (-3E-10)*(h^6)+4E-8*(h^5)-5E-7*(h^4)-9E-5*(h^3)+0.0054*(h^2)-0.1197*h+1.0139;
        elseif( h>=5 && h<20 )
            p = 9E-10*(h^6)-1E-7*(h^5)+5E-6*(h^4)-0.0002*(h^3)+0.0064*(h^2)-0.123*h+1.0143;
        elseif( h>=20 && h<=100 )
            p = 1.1014*exp(-0.1466*h);
        else
            p = 0;
        end
        % p in [Pa]
        p = p*10^5;
        % Density Determination
        R = 287.053;
        kappa = 1.4;
        dens = p/(R*T);
        % speed of sound
        a = sqrt(R*kappa*T);
    elseif( strcmp(model,'CIRA2012') )
        % CIRA-2012 data
        CIRA2012dat = [ 0,3.00E+02,1.16E+00;
                        20000,2.06E+02,9.37E-02;
                        40000,2.57E+02,4.02E-03;
                        60000,2.45E+02,3.26E-04;
                        80000,1.98E+02,1.83E-05;
                        100000,1.88E+02,5.73E-07;
                        120000,3.65E+02,2.03E-08;
                        140000,6.10E+02,3.44E-09;
                        160000,7.59E+02,1.20E-09;
                        180000,8.53E+02,5.46E-10;
                        200000,9.11E+02,2.84E-10;
                        220000,9.49E+02,1.61E-10;
                        240000,9.73E+02,9.60E-11;
                        260000,9.88E+02,5.97E-11;
                        280000,9.98E+02,3.83E-11;
                        300000,1.00E+03,2.52E-11;
                        320000,1.01E+03,1.69E-11;
                        340000,1.01E+03,1.16E-11;
                        360000,1.01E+03,7.99E-12;
                        380000,1.01E+03,5.60E-12;
                        400000,1.02E+03,3.96E-12;
                        420000,1.02E+03,2.83E-12;
                        440000,1.02E+03,2.03E-12;
                        460000,1.02E+03,1.47E-12;
                        480000,1.02E+03,1.07E-12;
                        500000,1.02E+03,7.85E-13;
                        520000,1.02E+03,5.78E-13;
                        540000,1.02E+03,4.29E-13;
                        560000,1.02E+03,3.19E-13;
                        580000,1.02E+03,2.39E-13;
                        600000,1.02E+03,1.80E-13;
                        620000,1.02E+03,1.36E-13;
                        640000,1.02E+03,1.04E-13;
                        660000,1.02E+03,7.98E-14;
                        680000,1.02E+03,6.16E-14;
                        700000,1.02E+03,4.80E-14;
                        720000,1.02E+03,3.76E-14;
                        740000,1.02E+03,2.98E-14;
                        760000,1.02E+03,2.38E-14;
                        780000,1.02E+03,1.92E-14;
                        800000,1.02E+03,1.57E-14;
                        820000,1.02E+03,1.29E-14;
                        840000,1.02E+03,1.07E-14;
                        860000,1.02E+03,9.03E-15;
                        880000,1.02E+03,7.67E-15;
                        900000,1.02E+03,6.59E-15];
        % search for correct entries in CIRA2012-table
        line = 1; % line in table
        while( line < size(CIRA2012dat,1) )
            if( h >= CIRA2012dat(line,1) && h < CIRA2012dat(line+1,1) )
                break;
            else
                line = line + 1;
            end
        end
        % Lagrange Polynom: n=1 (linear) --> temperature
        % piecewise exponential interpolation --> density
        if( h >= CIRA2012dat(end,1) )
            dens = CIRA2012dat(end,3);
            T = CIRA2012dat(end,2);
        else
            b = 1/(CIRA2012dat(line+1,1)-CIRA2012dat(line,1))*log(CIRA2012dat(line,3)/CIRA2012dat(line+1,3));
            a = CIRA2012dat(line,3)*exp(b*CIRA2012dat(line,1));
            dens = a*exp(-b*h);
            T = CIRA2012dat(line,2)*(h-CIRA2012dat(line+1,1))/(CIRA2012dat(line,1)-CIRA2012dat(line+1,1))+...
                   CIRA2012dat(line+1,2)*(h-CIRA2012dat(line,1))/(CIRA2012dat(line+1,1)-CIRA2012dat(line,1));
        end
        % Pressure [Pa] 
        R = 287.053;
        kappa = 1.4;
        p = dens*R*T;
        % speed of sound
        a = sqrt(R*kappa*T);
    end
end
