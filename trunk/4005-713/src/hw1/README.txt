=======================================
HW1 ` SimpleDatabase
authors: Alex Maskovyak & Young Suk Moon
=======================================


=======================================
1.  Executing the Program
=======================================
java -jar hw1.SimpleDatabase {path to database file}*

The path to a database file is an optional parameter.  
If this parameter is provided then the database will 
load the tuples stored in the file into active memory.


=======================================
2.  Commands
=======================================
There are 4 commands accepted by the program:

=======================================
== add command ==
=======================================
add (element_1, element_2, element_3..., element_n)

Add accepts a tuple of n elements.  The tuple will be 
inserted into the database.



=======================================
== show command ==
=======================================
show {description}

Where description is a regular expression.  This 
displays those tuples from the database which have 
at least one element which matches the regular 
expression.


=======================================
== delete description* ==
=======================================
delete {description}

Where description is a regular expression.  This 
deletes those tuples frolm the database which have at
lease one element which matches the regular 
expression.



=======================================
== quit command ==
=======================================
quit {filename}*

Saves the state of the database that is active memory
to the file system.  Optionally, this command can be
supplied with a filename for the save operation's use.  
If no filename is provided here, then the database 
defaults to using the filename provided at startup.  If
no filename was provided at initial program load, then
the program exits without saving.