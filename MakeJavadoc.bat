@echo off
call ./gradlew javadoc
xcopy /s /y /i build\docs ..\DOCS\Javadoc
