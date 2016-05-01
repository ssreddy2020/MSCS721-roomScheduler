@echo off
:: call the input file
call result.json

:: select the option from room scheduler list
echo 7
For /F "tokens=*" %%A IN (result.json) DO (
	echo %%A
)
endlocal