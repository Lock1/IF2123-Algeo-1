import core.*;

public class Core {

	public static void main(String[] args) {
		
		try {
			if (args[0].equals("-GUI")) {
				//  --- GUI Part ---
				try {
				 	GUI.main(args);
				}
				catch (NoClassDefFoundError e) {
					System.out.println("Exception : Failed to start GUI\n");
					System.out.println("Starting CLI...\n");
					CLI.main(args);
				}
			}
			else if (args[0].equals("-CLI")) 
				CLI.main(args);
			else {
				System.out.println("Unknown argument");
				System.out.println("Usage: Core [Interface]");
				System.out.println("Interface Options :");
				System.out.println("    -CLI		By default, CLI argument are used");
				System.out.println("    -GUI		Using Eclipse WindowBuilder libraries");
			}
		} 
		catch (Exception e) {
			CLI.main(args);
		}
	}

}
