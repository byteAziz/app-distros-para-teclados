@echo off
call ./gradlew clean build
xcopy /s /y /i build\libs EXE