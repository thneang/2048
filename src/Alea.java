import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Neang Thomas and Bruit Gabriel
 *
 */
public class Alea {
	private File file;
	private FileReader input;
	private boolean binary;
	
	/**
	 *  Construct the object Alea which will set the next value with the file.
	 * @param name The name of the file.
	 * @param binary Boolean that specify the format of reading, true for binary mode else false.
	 * @return An int.
	 */
	public Alea(String name,boolean binary) throws IOException{
		file = new File(name);
		if(!file.exists())
			throw new IOException("The file " + name + " doesn't exist for option -a or -n !");
		input = new FileReader(file);
		this.binary = binary;
	}
	
	/**
	 *  Load the next int in the file.
	 * @details Ignore space,commentary and \n.
	 * @return This int or -1 if end of file is read.
	 */
	private int loadNextValue()throws IOException{
		int c;
		int value;
		boolean ignore = true;
		do{
			c = input.read();
			switch(c){
			case '#':
				while( (c = input.read()) != '\n'){
					/* Ignore everything after # */
				}
				/* Go to the nextLine */
				break;
			case ' ':
				while( (c = input.read()) == ' '){
					/* Ignore all space */
				}
				break;
			case '\n':break;
			case -1:return -1;/* End of File*/
			default:ignore = false;break;
			}
		}while(ignore);
		value = Character.getNumericValue(c);
		return value;
	}
	
	/**
	 *  This method use loadNextValue to set the board.
	 * @details At the end of file it returns at the beginning of file.
	 * This method can throw an exception if the format is not respected.
	 * @return void.
	 */
	public void setNextValue(Board board) throws IOException{
		int nextValue = loadNextValue();
		if(nextValue == -1){
			input = new FileReader(file); /* return to the beginning of file*/
			nextValue = loadNextValue();/*get the first number*/
		}
		if(nextValue == -1)
			throw new IOException("Format of file not respected or Empty file");
	
		/* mode -a */
		if(binary){
			switch(nextValue){
				case 0:board.getNextValue().setValue(2);break;
				case 1:board.getNextValue().setValue(4);break;
				default:throw new IOException("Format of file not respected");
			}
		}
		/* mode -n */
		else{
			switch(nextValue){
				case 2:board.getNextValue().setValue(2);break;
				case 4:board.getNextValue().setValue(4);break;
				default:throw new IOException("Format of file not respected");
			}
		}	
		
	}
}
