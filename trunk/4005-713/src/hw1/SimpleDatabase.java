package hw1;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Alex Maskovyak & Young Suk Moon
 *
 */
public class SimpleDatabase {

	protected File fileBase = null;
	protected List<ArrayList<Object>> tupleBase;
	
	public static enum Command { ADD, SHOW, DELETE, INVALID, QUIT };
	
	
	
	/**
	 * 	Default constructor.
	 */
	public SimpleDatabase() {
		tupleBase = new ArrayList<ArrayList<Object>>();		
	}
	
	
	
	/**
	 * Constructor that loads from and maintains the database in the 
	 * filesystem.
	 * @param pFileBase File containing tuples;
	 */
	public SimpleDatabase( File pFileBase ) {
		this();
		
		this.fileBase = pFileBase;
		loadTuplesFromFile();
	}
	
	
	
	/**
	 * Add the strings of a string array into the tupleBase as a new tuple.
	 * @param pStringTuple Strings from which to create a tuple that is to be
	 * 			inserted into the tupleBase
	 */
	public void addTuple( String[] pStringTuple ) {
		ArrayList<Object> listTuple = new ArrayList<Object>();
		
		for( String t : pStringTuple ) {
			if( t.length() > 0 ) { 
				listTuple.add( t.replaceAll( "^\\s+|\\s+$", "") ); 
			}
		}
		
		tupleBase.add( listTuple );
	}
	
	/**
	 * Adds the strings of a string array into the tupleBase as a new tuple,
	 * also saves this addition to the filesystem.
	 * @param pStringTuple
	 */
	public void addTupleAndSave( String[] pStringTuple ) {
		addTuple( pStringTuple );
		saveTuplesToFile();
	}
	
	
	
	/**
	 * Deletes the specified tuple.
	 * @param tuple Tuple to be deleted.
	 */
	public void deleteTuple( ArrayList<Object> tuple ) {
		this.tupleBase.remove( tuple );
	}

	/**
	 * Deletes all tuples that have an element that matches the sepcified
	 * regular expression.
	 * @param regex Regular expression to match against tuples in the
	 * 			tuplebase.
	 */
	public void deleteTuple( String regex ) {
		this.tupleBase.removeAll( getTuples( regex ) );
	}
	
	/**
	 * Deletes all tuples from the specific list.
	 * @param tuples Tuples to be deleted.
	 */
	public void deleteTuples( List<ArrayList<Object>> tuples ) {
		this.tupleBase.removeAll( tuples );
	}
	
	
	
	/**
	 * Displays the tuple specified.
	 * @param tuple Tuple to be displayed.
	 */
	public void displayTuple( ArrayList<Object> tuple ) {
		String[] tupleString = new String[ tuple.size() ];
		displayTuple( tuple.toArray( tupleString ) );
	}
	
	/**
	 * Displays the specified tuple.
	 * @param pTuple Tuple to be displayed.
	 */
	public void displayTuple( String[] pTuple ) {
		for( int i = 0; i < pTuple.length; ++i ) {
			if( pTuple[ i ].length() > 0 ) {
				System.out.print( pTuple[ i ] );
				if( i + 1 < pTuple.length ) {
					System.out.print( " " );
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * Displays all tuples stored in the database.
	 */
	public void displayTuples() {
		for( ArrayList<Object> tuple : tupleBase ) {
			displayTuple( tuple );
		}
	}
	
	/**
	 * Displays the list of specified tuples.
	 * @param tuples List of tuples to be displayed.
	 */
	public void displayTuples( List<ArrayList<Object>> tuples ) {
		for( ArrayList<Object> tuple : tuples ) {
			displayTuple( tuple );
		}
	}
	
	/**
	 * Display those tuples which have an element that matches the provided
	 * regular expression.
	 * @param regex Regular expression to match against tuple elements.
	 */
	public void displayTuples( String regex ) {
		displayTuples( getTuples( regex ) );
	}
	
	
	
	/**
	 * Obtain those tuples which have an element that matches the provided
	 * regular expression.
	 * @param regex Regular expression to match against tuple elements.
	 */
	public List<ArrayList<Object>> getTuples( String regex ) {
		List<ArrayList<Object>> tupleMatches = new ArrayList<ArrayList<Object>>();
		
		for( ArrayList<Object> tuple : tupleBase ) {
			for( Object element : tuple ) {
				// check to see whether this element matches, if it does
				// display the entire tuple and move on to the next tuple
				// in the database
				if( element.toString().matches( regex ) ) {
					tupleMatches.add( tuple );
					break;
				}
			}
		}
		
		return tupleMatches;
	}
	
	
	
	/**
	 * Loads tuples from the tuplebase file associated with this simpledatabase
	 * .  This file has a separate tuple on each line.  Each element of the 
	 * tuple is separated by a space.
	 */
	public void loadTuplesFromFile() {
		loadTuplesFromFile( this.fileBase );
	}
	
	/**
	 * Loads tuples from the specified tuplebase file.
	 * @param pFile
	 */
	public void loadTuplesFromFile( File pFile ) {
		this.fileBase = pFile;
		try {
			Scanner scanner = new Scanner( pFile );
			String line;			
			while( scanner.hasNextLine() ) {
				line = scanner.nextLine();
				String[] stringTuple = line.replace( "\n", "" ).split( " " );
				if( stringTuple != null ) {
					addTuple( stringTuple );
				}
			}
			
			scanner.close();
		} 
		catch( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Save the active memory tuplebase to the filesystem.
	 * @return True if the save operation succeeding, false otherwise.
	 */
	public boolean saveTuplesToFile() {
		return saveTuplesToFile( this.fileBase );
	}
	
	/**
	 * Save the active memory tuplebase to the specified file in the 
	 * filesystem.
	 * @param pFile File into which the tuplebase will be stored.
	 * @return True if the save operation succeeding, false otherwise.
	 */
	public boolean saveTuplesToFile( File pFile ) {
		// fail fast
		if( pFile ==  null ) {
			return false;
		}
		
		
		try {
			BufferedWriter out = new BufferedWriter( new FileWriter( pFile ) );
			for( ArrayList<Object> tuple : tupleBase ) {
				for( Object element : tuple ) {
					out.write( element.toString() );
					out.write( " " );
				}
				out.write( "\n" );
			}
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println( "Unable to save to file: " + e.getMessage() );
			return false;
		}
		return true;
	}

	
	
	/**
	 * Main program.
	 * @param args Filename from which to load the database.  Optional.
	 */
	public static void main( String... args )  {
		// display greeting
		System.out.println("== Welcome to Simple Database! ==");
		System.out.println(
				"Commands:\nadd {tuple}, \nshow {description}, " +
				"\ndelete {description}, \nquit {filename*}");

		
		// create database
		SimpleDatabase db = new SimpleDatabase();
		
		
		// load database filename if available
		if( args.length == 1 ) {
			String fileName = args[ 0 ];
			System.out.printf( "Loading db info from: '%s'\n", fileName );
			db.loadTuplesFromFile( new File( fileName ) );
		}

				
		// begin reading from standard in
		Scanner scanner = new Scanner( System.in );
		
		
		// setup variables for operation
		Command c = SimpleDatabase.Command.INVALID;
		String description = "";
		
		
		// accept commands until QUIT has been received, then exit the program
		while( c != SimpleDatabase.Command.QUIT ) {
			
			// pretty up where the user enters text
			System.out.print("> ");
			
			
			// attempt to understand the command they gave us
			if( scanner.hasNext() ) { 
				try {
					c = Command.valueOf( 
							SimpleDatabase.Command.class, 
							scanner.next().toUpperCase() );
				}
				catch( Exception e ) {
					c = SimpleDatabase.Command.INVALID;
				}
			}
			
			
			// perform an operation that is command-dependent
			switch( c ) {
			
				// add the tuple to the db's active memory
				case ADD :
					String tupleList = 
						scanner.nextLine().replaceFirst( "^\\s", "" );
					
					// if properly formatted
					if( tupleList != null && !tupleList.equals( "" ) ) {
						String[] tuple = tupleList.split( " " );
						
						System.out.println( "==============" );
						System.out.print( "Adding tuple: " );
						
						db.displayTuple( tuple );
						
						System.out.println( "==============" );
						
						db.addTuple( tuple );
					}
					// complain otherwise
					else {
						System.out.println( "==============" );
						System.out.println( 
								"Use: add {element_1, element_2, " +
								"... elemnt_n}" );
						System.out.println( "==============" );
					}
					break;
					
				// delete tuples which contain an element which matches the 
				// description pattern that was passed in
				case DELETE :
					description = scanner.nextLine().replaceFirst("^\\s+", "");
					
					// if properly formatted
					if( description != null && !description.equals( "" ) ) {
						System.out.println( "==============" );
						System.out.printf( 
								"Deleting tuples that have an element that matches '%s':\n", 
								description );
						db.displayTuples( db.getTuples( description ) );
						System.out.println( "==============" );
						db.deleteTuple( description );
					}
					// complain otherwise
					else {
						System.out.println( "==============" );
						System.out.println( "Use: delete {description}" );
						System.out.println( "==============" );
					}
					break;
					
				// quit program operation, save the database if we have a file
				// name to save to
				case QUIT :
					System.out.println( "==============" );
										
					String filename = 
						scanner.nextLine().replaceFirst( "^\\s*", "" );
					
					// save to the file that was specified
					if( filename != null && !filename.equals("") ) {
						if( db.saveTuplesToFile( new File( filename ) ) ) {
							System.out.printf( 
									"Saved db to '%s'\n", 
									filename );
						}
					}
					// save to the file specified at program start otherwise
					else {
						if( db.saveTuplesToFile() ) {
							System.out.printf( 
									"Saved db to '%s'\n", 
									db.fileBase );
						}
					}
					
					System.out.println( "Quiting program..." );
					System.out.println( "==============" );
					System.exit( 0 );
					break;
					
				// show those tuples in active memory that have an element that
				// matches the regular expression pattern
				case SHOW :
					description = scanner.nextLine().replaceFirst( "^\\s*", "" );
					
					// if description is properly formatted
					if( description != null && !description.equals("") ) {
						System.out.println( "==============" );
						System.out.printf( 
								"Displaying tuples that have an element that matches '%s':\n", 
								description );
						db.displayTuples( description );
						System.out.println( "==============" );
					}
					// complain otherwise
					else {
						System.out.println( "==============" );
						System.out.println( "Use: show {description}" );
						System.out.println( "==============" );
					}
					break;
					
				// catch all of the bad commands and yell at the user
				case INVALID :	
					System.out.println( "==============" );
					System.out.println( 
							"Please enter a command: 'ADD {tuple}, " +
							"SHOW {description}, DELETE {description}, " +
							"or QUIT {filename*}' ");
					System.out.println( "==============" );
					
					break;
			}
		}
	}
}
