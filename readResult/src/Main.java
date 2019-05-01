import java.io.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "result.csv";
        String fileOut = "output.csv";
        int lineNum=1;
        try(FileReader fileReader = new FileReader(fileName)){
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
                        }
                        else
                        System.out.print((char) ch);
                    }
                    //System.out.print((char) ch);

                }
                ch = fileReader.read();
            }
        } catch(FileNotFoundException e){
            System.out.println("File not found");
        }catch(IOException e){

        }

        /*try(FileWriter fileWriter = new FileWriter(fileOut,true)) {
            String fileContent = "This is a sample text.";
            fileWriter.write(fileContent);
        } catch (IOException e) {
            // exception handling
        }*/
        System.out.println("\nHello World!"+lineNum);
    }
}
