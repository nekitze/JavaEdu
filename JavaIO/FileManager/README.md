# FileManager

The program accept as an argument the absolute path to the folder where we start to work, and support the following commands:

```mv WHAT WHERE``` – enables to transfer or rename a file if WHERE contains a file name without a path.

```ls``` – displays the current folder contents (file and subfolder names and sizes in KB).

```cd FOLDER_NAME``` – changes the current directory.

Let's assume there is MAIN folder on disk C:/ (or in the root directory, depending on OS) with the following hierarchy:

- MAIN
  - folder1
    - image.jpg
    - animation.gif
  - folder2
    - text.txt
    - Main.java


Example of the program operation for MAIN directory:
```
$ java Main --current-folder=C:/MAIN
C:/MAIN
-> ls
folder1 60 KB
folder2 90 KB
-> cd folder1
C:/MAIN/folder1
-> ls
image.jpg 10 KB
animation.gif 50 KB
-> mv image.jpg image2.jpg
-> ls
image2.jpg 10 KB
animation.gif 50 KB
-> mv animation.gif ../folder2
-> ls
image2.jpg 10 KB
-> cd ../folder2
C:/MAIN/folder2
-> ls
text.txt 10 KB
Main.java 80 KB
animation.gif 50 KB
-> exit
```
