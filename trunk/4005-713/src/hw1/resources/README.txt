==============================================================================
Assignment: HW1 ` SimpleDatabase
Authors: Alex Maskovyak & Young Suk Moon
JDK: 1.6
==============================================================================



==========================================
== 1.  Executing the Program ==
==========================================
This program is JDK 1.6 compliant.  It was tested on the Windows XP SP2 OS.

java -jar SimpleDatabase {path to database file}*

The path to a database file is an optional parameter.  If this parameter is 
provided then the database will load the tuples stored in the file into 
active memory.


=======================================
== 2.  Commands ==
=======================================
There are 4 commands accepted by the program:

=======================================
== 2.1 add command ==
=======================================
add (element_1, element_2, element_3..., element_n)

Add accepts a tuple of n elements.  The tuple will be inserted into the 
database.


=======================================
== 2.2 show command ==
=======================================
show {description}

Where description is a regular expression.  This displays those tuples from 
the database which have at least one element which matches the regular 
expression.  Note: the expression is taken directly from the command line and
should not be quoted unless the quotes are a part of the regular expression.


=======================================
== 2.3 delete description* ==
=======================================
delete {description}

Where description is a regular expression.  This deletes those tuples from 
the database which have at least one element which matches the regular 
expression.  Note: the expression is taken directly from the command line and
should not be quoted unless the quotes are a part of the regular expression.


=======================================
== 2.4 quit command ==
=======================================
quit {filename}*

Saves the state of the database that is active memory to the file system.  
Optionally, this command can be supplied with a filename for the save 
operation's use.  If no filename is provided here, then the database defaults 
to using the filename provided at startup.  If no filename was provided at 
initial program load, then the program exits without saving.