/*mport java.io.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "result-opt-mblty.csv";
        String fileOut = "output.csv";
        int take=1;
        for(int i=1;i<=take;i++){
            int lineNum=1,tmp=i;
            try(FileReader fileReader = new FileReader(fileName)){
                try(FileWriter fileWriter = new FileWriter(fileOut,true)) {



                    int ch = fileReader.read();
                    int col = 0;
                    while(ch != -1){



                        if((char)ch == '\n') {  //new line
                            lineNum++;
                            col=0;
                        }


                        if((char)ch == ',') //next column
                            col++;

                        if(tmp==take)tmp=0;
                        if(lineNum%2==0) {
                            fileWriter.write(ch);
                            System.out.print((char) ch);
                        }
                        //System.out.print((char) ch);



                        ch = fileReader.read();
                    }



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
}*/

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "rawdata_opt.csv";
        String fileOut = "output.csv";
        int take=1;
        for(int i=1;i<=take;i++){
            int lineNum=1,tmp=i;
            try(FileReader fileReader = new FileReader(fileName)){
                try(FileWriter fileWriter = new FileWriter(fileOut,true)) {



                    int ch = fileReader.read();
                    int col = 0;
                    while(ch != -1){



                        if((char)ch == '\n') {  //new line
                            lineNum++;
                            col=0;
                        }


                            if((char)ch == ',') //next column
                                col++;

                            if(tmp==take)tmp=0;
                            if(lineNum%take==tmp && col==2) {
                                if((char)ch == ','){
                                    System.out.println();
                                    String newline = "\n";
                                    fileWriter.write(newline);
                                }
                                else {
                                    fileWriter.write(ch);
                                    System.out.print((char) ch);
                                }
                            }
                            //System.out.print((char) ch);



                        ch = fileReader.read();
                    }



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
