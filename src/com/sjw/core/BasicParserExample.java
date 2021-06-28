package com.sjw.core;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Bhagvan Kommadi
 * Basic Parser example for parsing application data arguments
 * http://commons.apache.org/proper/commons-cli/introduction.html
 */
public class BasicParserExample extends DefaultParser{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		BasicParserExample exampleParser = new BasicParserExample();
		
		Options options = new Options()
        .addOption("a", "enable-a", false, "turn on or off")
        .addOption("b", "bfile", true, "set the value of [b]")
        .addOption("c", "copt", false, "turn on or off");
		
		String[] parserArgs = new String[] { "-a",
                "-b", "toast",
                "foo", "bar" };
		try
		{
		 CommandLine commandLine = exampleParser.parse(options,parserArgs);
		 System.out.println(commandLine.getOptionValue("b"));
		}
		catch(ParseException parseException)
		{
			System.out.println("Exception "+parseException.getMessage());
		}
	}
	
	
}

