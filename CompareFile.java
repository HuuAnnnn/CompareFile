import java.io.*;
import java.util.*;
public class CompareFileUpgrade{	
	public static ArrayList<String> config = new ArrayList<String>();
	public static void main(String[] args) throws FileNotFoundException{
		createVariable("./config.txt");
		try {
			compile(config.get(0), config.get(1));
			Thread.sleep(1500);
		} catch (Exception e) {
			;
		}

		Thread compileThread = new Thread(() -> {
			try {
				String pathCase = config.get(2);
				String pathAns = config.get(3);
				for(int i = 4;i<config.size();i++) {
					run(config.get(0), config.get(1), config.get(i));
				}

				Thread.sleep(1000);
				compare(pathCase,pathAns);
            } catch(Exception e) {
				e.printStackTrace();
                System.out.println("Cannot run thread");
            }
        });

        compileThread.start();
	}

	public static void createVariable(String path) throws FileNotFoundException{
		File file = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try{
			String line = reader.readLine();
			int count = 0;
			while(line!=null){
				config.add(line);
				line = reader.readLine();
				count++;
			}

			reader.close();
		}
		catch(IOException ex){
			System.out.println("Can't read file");
		}	
	}

	public static File[] readFolder(String pathFolder){
		File fileOrDir = new File(pathFolder);
        if (fileOrDir.isDirectory()) {
            File[] children = fileOrDir.listFiles();
            return children;
        }
        return null;
	}
	public static void compare(String pathCase, String pathAns) throws FileNotFoundException{
		File[] folderCase = readFolder(pathCase);
		File[] folderAns = readFolder(pathAns);
		int caseRight = folderCase.length;
		for (File eachCase : folderCase) {
			File fileCase = new File(pathCase+"./"+eachCase.getName());
			BufferedReader readerCase = new BufferedReader(new FileReader(fileCase));

			File fileAns = new File(pathAns+eachCase.getName());
			BufferedReader readerAns = new BufferedReader(new FileReader(fileAns));
			
			try{
				String lineAns = readerAns.readLine();
				String lineCase = readerCase.readLine();
				while(lineCase!=null){
					if(!lineAns.equals(lineCase)){
						System.out.println("You wrong case "+eachCase.getName());
						caseRight--;
						break;
					}
					lineCase = readerCase.readLine();
					lineAns = readerAns.readLine();
				}
			}
			catch(IOException ex){
				System.out.println("Can't read file");
			}	
        }
        System.out.println("Percent = " + ((double)caseRight/folderCase.length)*100);
	}

    private static void compile(String languages, String fileName) {
        try {
            switch(languages) {
                case "java":
                    Runtime.getRuntime().exec("javac "+fileName+".java");
                    break;
                case "c":
                    Runtime.getRuntime().exec("gcc "+fileName +".c -o "+fileName);
                    break;
            }
        } catch(Exception e) {
            System.out.println("Cannot compile");
        }
    }

    private static void run(String languages, String fileName, String argument) {
        try {
            switch(languages) {
                case "java":
                    Runtime.getRuntime().exec("java "+fileName + " " +argument);
                    break;
                case "c":
                    Runtime.getRuntime().exec("./"+fileName + " " +argument);
                    break;
                case "python":
                    Runtime.getRuntime().exec("python "+fileName+".py " + argument);
                    break;
            }
        } catch(Exception e) {
            System.out.println("Cannot run");
        }
    }
}