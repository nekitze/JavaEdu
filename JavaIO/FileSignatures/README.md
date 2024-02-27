# File Signatures

During execution program accept full paths to files on hard disk and keep the type which file signature corresponds to. 
The result of program execution will be written to result.txt file. 
If a signature cannot be defined, the execution result is UNDEFINED (no information should be written into the file).

Example of program operation:

``` PowerShell
$java Main
-> C:/Users/Admin/images.png
PROCESSED
-> C:/Users/Admin/Games/WoW.iso
PROCESSED
-> 42
```

Contents of result.txt file (there is no need to load this file as a result):

```
PNG
GIF
```

Note:

- We can accurately determine the content type by analyzing the file signature, since the file extension contained in the name (e. g. image.jpg) can be changed by simply renaming the file.
