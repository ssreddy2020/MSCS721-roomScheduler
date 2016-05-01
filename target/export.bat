@echo off
:: call the input file
call result.json

:: display the available rooms and their scheduleed meeting to export into the result.json file
echo 5
echo | set /p = "room1--"
echo | set /p = "10--"
echo | set /p = "MaristCollege--"
echo | set /p = "LowellThomas1"
echo 4
echo | set /p = "room1  "
echo | set /p = "2016-09-09  "
echo | set /p = "10:09  "
echo | set /p = "2017-09-09  "
echo | set /p = "11:09"

:: select the option from the room scheduler to export the data
echo 6
endlocal