clear
k1=0;k2=0;k3=0;
for i=1:200
   i
   R1=rand
if R1<=0.5
   R2=rand
   if R2<=3/6
      k1=k1+1;
   else if R2>5/6
         k3=k3+1;
      else
         k2=k2+1;
      end
   end
else
   k1=k1+1;
end
end
E=(k2+k3)/200
E1=(0*k1+1*k2+2*k3)/200

