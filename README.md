# The-Keyboard
The Keyboard is a learning JavaFX project for typing by mouse-clicking. It had also been intended as a way to learn GUI element placement, and manipulation by coding. TheKeyboard had been written in Notepad++. 

The project code had been compiled manually, by command

javac --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard.java

and run by command

java --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard

during code testing, and writing. Compiling TheKeyboard.java compiles all .java folders, for it is the highest file in .java file hierarchy of the project.   
      The module-info.java is not compiled,
but is not necessary to run the project outside of .jar file, which is why it is present in .jar file
but not in project src file (which will be in TheKeyboard_project folder).
      At this time, a .jar file is placed which can be run by command

java --module-path lib/javafx/lib add-modules javafx.controls -jar theKeyboard.jar

but requires lib/javafx/lib to be downloaded, and be in the same folder as theKeyboard.jar file which is why these are provided in folder the_keyboard_jar, and that you may _cd_ to the correct folder, where it had been downloaded for example. 
      
I am working on improving the user experience by structuring the project in a msi, rpm, and other bundles for different distributions, which would ease the running of the application. 
    
This will be done manually in jpackage likely, without the compromise of an IDE. Code files, without compiled files can be found in another folder named the_keyboard_code.
      
This code can be compiled, and run by the two commands provided that you _cd_ to the correct folder, for example where they had been downloaded (and are right now), and that lib/javafx/lib is downloaded.

javac --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard.java

java --module-path ../lib/javafx/lib --add-modules javafx.controls TheKeyboard

javac (command), which will compile the files to .class files, and 
java (command), which will run the (compiled) .class files.

This code had been written without version-control, but any changes made to the project will be observable in git version control, as it may greatly contribute to the managment of the project.

The project includes dynamic resizing with resizing of window of all elements with the resizing of the window, a trie structure with 3 suggest buttons, and the keyboard. There is the -Home button which moves to the Home Pane (Screen), with The Keyboard button, and the _ _ _ space button, --D button to delete a single character, the shift button ->, for capital numbers, and -Discard- button which deletes the entire word.

Additionally, the -Enter- button pushes the written words to the trie structure which writes suggestions (the three buttons) which (attempt to) complete the currently written word, providing the most often recuring (written) word with this root. Therefore, for "en", it would provide "engine" as suggestion one, if it had been written beforehand (more than any other word with the root "en").
In order for a suggestion to be provided, it must be written at least once, therefore, as the user (customer) writes more on the keyboard, he is increasingly provided the suggestion which may be most likely for the way he may speak (write).

The way the program looks.
<img width="1020" height="560" alt="image" src="https://github.com/user-attachments/assets/16d2323d-97a5-4091-93e8-e30ff04f00e0" />



