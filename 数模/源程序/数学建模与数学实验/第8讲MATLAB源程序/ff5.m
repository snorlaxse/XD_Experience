    [T,Y]=ode45('rigid',[0 12],[0 1 1]);
     plot(T,Y(:,1),'-',T,Y(:,2),'*',T,Y(:,3),'+')