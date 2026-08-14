# The-Keyboard
The Keyboard is a learning JavaFX project, for typing by mouse-clicking. It had also been intended as a way to learn GUI element placement, and manipulation by coding. TheKeyboard had been written in Notepad++. 

Project had been compiled manually, by command

javac --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard.java

and run by command

java --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard

during code testing, and writing. Compiling TheKeyboard.java compiles all .java folders, for it is the highest file in .java file hiararchy of project.   
      The module-info.java is not compiled,
but is not necessary to run the project outside of .jar file, which is why it is present in .jar file
but not in project src file.
      At this time, a .jar file is placed which can be run by command

java --module-path lib/javafx/lib add-modules javafx.controls -jar theKeyboard.jar

but requires lib/javafx/lib to be downloaded, and be in the same folder as theKeyboard.jar file which is why these are provided in folder the_keyboard_jar, and that you may _cd_ to the correct folder, where it had been downloaded for example. 
      I am working on improving the user experience by structuring the project in a msi, rpm, and other bundles for different distributions, which would ease the testing of the project. 
    This will be done manually in jpackage likely, without the compromise of an IDE. Code files, without compiled files can be found in another folder named the_keyboard_code.
      This code can be compiled, and run by the two commands provided that you _cd_ to the correct folder, for example where they had been downloaded (and are right now).

javac --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard.java

java --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard

javac (command), which will compile the files to .class files, and 
java (command), which will run the (compiled) .class files.

This code had been written without version-control, but any changes made to the project will be observable in git version control. 
      
