package hw1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleDatabase {

	protected File fileBase;
	protected List<ArrayList<Object>> tupleBase;
	
	public static enum Command { ADD, SHOW, DELETE };
	
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
	 */
	public void saveTuplesToFile() {
		saveTuplesToFile( this.fileBase );
	}
	
	/**
	 * Save the active memory tuplebase to the specified file in the 
	 * filesystem.
	 * @param pFile File into which the tuplebase will be stored.
	 */
	public void saveTuplesToFile( File pFile ) {
		// fail fast
		if( pFile ==  null ) {
			return;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Add the strings of a string array into the tupleBase as a new tuple.
	 * @param pStringTuple Strings from which to create a tuple that is to be
	 * 			inserted into the tupleBase
	 */
	public void addTuple( String[] pStringTuple ) {
		ArrayList<Object> listTuple = new ArrayList<Object>();
		
		for( String t : pStringTuple ) {
			listTuple.add( t );
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
	 * Displays all tuples stored in the database.
	 */
	public void displayTuples() {
		for( ArrayList<Object> tuple : tupleBase ) {
			displayTuple( tuple );
		}
	}
	
	/**
	 * Displays the tuple specified.
	 * @param tuple Tuple to be displayed.
	 */
	public void displayTuple( ArrayList<Object> tuple ) {
		for( Object element : tuple ) {
			System.out.print( element.toString() + " " );
		}
		System.out.println();
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
	 * Deletes the specified tuple.
	 * @param tuple Tuple to be deleted.
	 */
	public void deleteTuple( ArrayList<Object> tuple ) {
		this.tupleBase.remove( tuple );
	}

	/**
	 * Deletes all tuples from the specific list.
	 * @param tuples Tuples to be deleted.
	 */
	public void deleteTuples( List<ArrayList<Object>> tuples ) {
		this.tupleBase.removeAll( tuples );
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
	
	public static void main( String... args )  {
		SimpleDatabase db = new SimpleDatabase();
		db.loadTuplesFromFile( new File( "C:\\Java\\workspace\\4005-713\\tuples.txt" ) );
		db.displayTuples( ".*a.*" );
		System.out.println();
		db.displayTuples();
		db.deleteTuple( ".*a.*" );
		
		System.out.println();
		db.displayTuples();
		
		db.saveTuplesToFile( new File( "tuples_output.txt" ) );
	}
}