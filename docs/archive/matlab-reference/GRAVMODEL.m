% author: Michael Sams
function [g_O,g0] = GRAVMODEL(h, Lat, GravModel)
    if( GravModel == 'Newton' )
        rE = 6378000; % mean earth radius (FSD, Boiffier)
        % Boiffier - Somigliana
        g0 = 9.7803*(1+0.0053*sin(Lat)^2-5.8*10^-6*sin(2*Lat)^2);
        % Walter, FSD
        g_O = g0*(rE/(rE+h))^2*[0;0;1];
    end 
end
