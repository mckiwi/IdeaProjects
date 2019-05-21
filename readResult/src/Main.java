import java.io.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "result-opt.csv";
        String fileOut = "output.csv";
        for(int i=2;i<=40;i+=2){
            int lineNum=1,tmp=i;
            try(FileReader fileReader = new FileReader(fileName)){
                try(FileWriter fileWriter = new FileWriter(fileOut,true)) {
                    int ch = fileReader.read();
                    int col = 0;
                    while(ch != -1){

                        if((char)ch == '\n') {
                            lineNum++;
                            col=0;
                        }

                        if(lineNum%2==0) {
                            if((char)ch == ',')
                                col++;
                            if(i==40)tmp=0;
                            if(col == 0 && lineNum%40 == tmp){   //choose column
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
                    /*if(lineNum%2==0) {
                        if((char)ch == ',')
                            col++;
                        if(col == 0){   //choose column
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

                    }*/
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
}
