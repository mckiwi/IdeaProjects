import java.io.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "result-a-0.001.csv";
        String fileOut = "output.csv";
        int lineNum=1;
        try(FileReader fileReader = new FileReader(fileName)){
            try(FileWriter fileWriter = new FileWriter(fileOut,true)) {
                int ch = fileReader.read();
                int col = 0,col3 = 0;
                while(ch != -1){

                    if((char)ch == '\n') {
                        lineNum++;
                        col=0;
                    }

                    if(lineNum%2==0) {
                        if((char)ch == ',')
                            col++;
                        if(col == 2){
                            if((char)ch == ','){
                                System.out.println();
                                String fileContent = "\n";
                                fileWriter.write(fileContent);
                            }
                            else{
                                //String fileContent = (char)ch;
                                fileWriter.write(ch);
                                System.out.print((char) ch);
                            }

                        }
                        //System.out.print((char) ch);

                    }
                    ch = fileReader.read();
                }


                    /*String fileContent = "This is a sample text.";
                    fileWriter.write(fileContent);*/
            } catch (IOException e) {
                // exception handling
            }
        } catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){

        }


        System.out.println("\nHello World!"+lineNum);
    }
}
