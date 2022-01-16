RM = ''
ifeq ($(OS),Windows_NT)
	RM := del
else
	RM := rm -rf
endif

file_name = ''

compile:
	javac CompareFile.java
run:
	java CompareFile $(file_name)
clean:
	$(RM) *.class
	$(RM) *.txt
