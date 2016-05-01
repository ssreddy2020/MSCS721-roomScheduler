::Import data from json file
@echo off
FOR /F "tokens=*" %%A IN (result.json) DO @echo %%A
:: create rooms and schedule their meeting

FOR /L %%A IN (1,1,10) DO (
echo | set /p  = "{" 
	echo | set /p  = "room":
	echo | set /p  = "["
		echo | set /p  = "{"
			echo | set /p  = ""Building": "building%%A","
			echo | set /p  = ""RoomName": "room%%A","
			echo | set /p = ""Capacity": 10,"
			echo | set /p = ""Meetings": ["
				echo | set /p = "startTime: "
				echo | set /p = "2016-04-%%A " 
				echo | set /p = "10:%%A " 
   				echo | set /p = "endTime: "
				echo | set /p = "2016-06-%%A "
				echo | set /p = "11:%%A " 
				echo | set /p = "subject%%A ""
			echo | set /p = "]" 
			echo | set /p = ""Location": LowellThomas%%A"
		echo | set /p = "}" 
 	echo  ] )
echo }
endlocal