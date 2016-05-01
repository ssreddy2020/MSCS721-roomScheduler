::calling
call addRoom.bat
:: select option from the list
echo 3
:: schedule meeting for rooms
FOR /L %%A IN (1,1,9) DO (
	echo room%%A
	echo 2016-04-%%A
	echo 10:0%%A
	echo 2016-06-%%A
	echo 11:0%%A
	echo subject%%A
)
endlocal