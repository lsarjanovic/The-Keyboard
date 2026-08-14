# The-Keyboard
The Keyboard is a learning JavaFX project for typing by mouse-clicking. It had also been intended as a way to learn UI element placement, and manipulation by coding. TheKeyboard had been written in Notepad++. 

The project includes dynamic resizing with resizing of window of all elements with the resizing of the window, a trie class (structure) with 3 suggest buttons, and the keyboard. There is the _-Home_ button which moves to the Home Pane (Screen), with The Keyboard button, and the *_ _ _* space button, _--D_ button to delete a single character, the shift button _->_, for capital numbers, and _-Discard-_ button which deletes the entire word.

Additionally, the _-Enter-_ button pushes the written words to the trie class (structure) which writes suggestions (the three buttons) which may (attempt to) complete the currently written word, (and does if clicked,) providing the most often recuring (written) word with this root. Therefore, for _"en"_ it would provide _"engine"_ as suggestion one, if it had been written beforehand (more than any other word with the root _"en"_).
In order for a suggestion to be provided, it must be written at least once, therefore, as the user (customer) writes more on the keyboard, he is increasingly provided the suggestion which may be most likely for the way he may speak (write).

The way the program looks.
            
      _________________________________________________________________________________

<img width="800" height="455" alt="The Home Screen (Pane), image" class="center" src="https://github.com/user-attachments/assets/369b576f-b2fc-4cef-ac54-7b026611a562" />
                                    
      Image_1: The Home Screen (Pane)


<img width="800" height="455" alt="The Keyboard Screen (Pane) with button names, image" src="https://github.com/user-attachments/assets/a785d399-ce62-44ab-853c-59fd99b1c020" />

      Image_2: The Keyboard Screen (Pane) with additional button names


<img width="800" height="455" alt="The Keyboard Screen (Pane), image" class="center" src="https://github.com/user-attachments/assets/16d2323d-97a5-4091-93e8-e30ff04f00e0" />

      Image_3: The Keyboard Screen (Pane)

      ________________________________________________________________________________

      Dynamic resizing of elements examples

<img width="1128" height="480" alt="image" src="https://github.com/user-attachments/assets/6c696aaf-221e-4cfa-989e-11974c968d94" />

      Image_4: Dynamic resizing of elements -- 1 --

<img width="757" height="692" alt="image" src="https://github.com/user-attachments/assets/bb043413-f4cb-4ef6-aa04-0501b5bd935d" />

      Image_5: Dynamic resizing of elements -- 2 --

<img width="174" height="203" alt="resizing_of_elements_1" src="https://github.com/user-attachments/assets/8db26de8-522c-4c07-94c7-f8add9d33ca0" />

      Image_6: Dynamic resizing of elements -- 3 --

<img width="1365" height="345" alt="resizing_of_elements_2" src="https://github.com/user-attachments/assets/4d2a5cd2-6afa-4aa2-9150-df76b2bbc845" />

      Image_7: Dynamic resizing of elements -- 4 --
      
      ________________________________________________________________


The project code had been compiled manually, by command

      javac --module-path path_to_javafx_/lib_library --add-modules javafx.controls *.java 

and run by command

      java --module-path path_to_javafx_/lib_library --add-modules javafx.controls TheKeyboard

during code testing, and writing.
      
The module-info.java is not compiled, but is not necessary to run the project outside of .jar file. Each .java file must be compiled separately, or just do *.java.
      
At this time, the .jar file theKeyboard.jar can be run (to run the application) by the command

      java --module-path path_to_javafx_/lib_library --add-modules javafx.controls -jar theKeyboard.jar

but requires javafx libraries to be downloaded, and present in the same folder as theKeyboard.jar file, and that you may _cd_ to the correct folder, where it had been downloaded for example. 
      
I am working on improving the user experience by packaging the project in a msi, rpm, and other file types for different distributions, which would ease the running of the application. 
    
This will be done manually in jpackage likely, without the compromise of an IDE. Code files, without compiled files can be found in another folder named **src** at _theKeyboard/src_ folder.
      
This code can be compiled, and run by the two commands provided that you _cd_ to the correct folder, for example where the code files had been downloaded (and are right now), and that _javafx/lib_ library is downloaded.

      javac --module-path path_to_javafx_library --add-modules javafx.controls *.java

      java --module-path path_to_javafx_library --add-modules javafx.controls TheKeyboard

javac (command), which will compile the files to .class files, and 
java (command), which will run the (compiled) .class files.

Link to the download of JavaFX files. Download the SDK file (of the two, these being SDK, and JMOD).
      
      https://www.oracle.com/java/technologies/downloads/javafx/

