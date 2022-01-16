import java.io.*;
import java.util.*;
public class CompareFile{	
	public static ArrayList<String> config = new ArrayList<String>();
	
	public static void main(String[] args) throws FileNotFoundException{
		createVariable(args[0]);
		try {
			compile(config.get(0), config.get(1));
			System.out.println("Running...");
			Thread.sleep(1500);
		} catch (Exception e) {
			;
		}

		Thread compileThread = new Thread(() -> {
			try {
				String nameCase = config.get(2);
				String pathCase = config.get(3);
				String nameAns = config.get(4);
				String pathAns = config.get(5);
				for(int i = 6;i<config.size();i++) {
					run(config.get(0), config.get(1), config.get(i));
				}

				Thread.sleep(1000);
				compare(nameCase,pathCase,nameAns,pathAns);
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

	public static void compare(String nameCase, String pathCase, String nameAns, String pathAns) throws FileNotFoundException{
		int fileWrong = 0;
		int caseNumbers = 0;
		for(int i=1; i>=0; i++){
			File fileCase = new File(pathCase+"/"+nameCase+String.valueOf(i)+".txt");
			if(!fileCase.exists()){
				caseNumbers = i;
				break;
			}

			BufferedReader readerCase = new BufferedReader(new FileReader(fileCase));
			File fileAns = new File(pathAns+"/"+nameCase+String.valueOf(i)+".txt");

			if(!fileAns.exists()){
				caseNumbers = i;
				continue;
			}


			BufferedReader readerAns = new BufferedReader(new FileReader(fileAns));
			try {
				String lineCase = readerCase.readLine();
				String lineAns = readerAns.readLine();
				while(lineCase!=null){
					if(lineAns==null){
						System.out.println("Not enough answers in Case "+i);
						fileWrong++;
						break;
					}
					
					if(!lineCase.equals(lineAns)){
						System.out.println("Answers is wrong in Case "+i);
						fileWrong++;
						break;
					}

					lineCase = readerCase.readLine();
					lineAns = readerAns.readLine();
				}
			} catch(IOException ex){
				System.out.println("Can't read file");
			}
		}

		System.out.format("Result: %.2f%%", ((caseNumbers - fileWrong)/(double)caseNumbers)*100);	
	}

    private static void compile(String language, String fileName) {
        try {
            switch(language) {
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

    private static void run(String language, String fileName, String argument) {
        try {
            switch(language) {
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