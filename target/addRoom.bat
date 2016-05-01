@echo off
:: select option from the list to add rooms
echo 1
:: add rooms
FOR /L %%A IN (1,1,10) DO (
	echo room%%A 
	echo 10
	echo MaristCollege
	echo LowellThomas%%A
)
endlocal