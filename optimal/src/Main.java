//import com.sun.xml.internal.bind.v2.TODO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    static int relay(Ue relayer, Ue requester) {
        if (requester.token > 0) {
            requester.token -= 1;
            relayer.token += 1;
            return 1;
        } else return 0;
    }



    static int[] sendPacket(Random ran, Ue[] ue) {
        int[] result = new int[3]; //d2d:0, b2d:1, drop:2
        result[0]=result[1]=result[2]=0;
        int noNeighbor=0,noToken=0;
        int sendNum = ran.nextInt(ue.length) + 1;
        sendNum = 1000;
        //System.out.println("Packet num: "+sendNum);
        for(int co=0; co<sendNum; co++){

            int target = ran.nextInt(ue.length);
            if(ue[target].ifRelay())    //direct
                result[1]++;
            else{
                if(ue[target].token<=0) { //no token, packet drop
                    result[2]++;
                    noToken++;
                    continue;
                }
                else {  //try relay
                    if(ue[target].neighbor.size()<=0){
                        result[2]++;noNeighbor++;
                        continue;
                    }
                    int relayer = ran.nextInt(ue[target].neighbor.size());
                    relay(ue[ue[target].neighbor.get(relayer)], ue[target]);
                    result[0]++;
                }
            }
        }
        //System.out.printf("PacketNum:%2d D2D:%2d B2D:%2d DRP:%2d NoNeighbor:%2d Notoken:%2d\n",sendNum, result[0],result[1],result[2],noNeighbor,noToken);
        return result;
    }

    static int getTotalTk(Ue[] ue,int ueNum){
        int totalTk=0;
        for(int i=0;i<ueNum;i++){
            totalTk+=ue[i].token;
        }
        return totalTk;
    }

    static int classify(Ue[] ue,int ueNum,Ue ueTarget,double thresholdHigh){
        //double thresholdHigh=0.001;//0.001
        int thresholdLow=1;
        if( (double)ueTarget.token/getTotalTk(ue,ueNum) >= thresholdHigh){
            return 1; //is bad UE
        }
        if(ueTarget.token<=thresholdLow) {
            if(ueTarget.cqi<=5)
                return -1; //poor UE
            else
                return 0;
        }
        return 0; //is normal UE
    }

    public static void main(String[] args) {
        for(double thresholdHigh=0.0004;thresholdHigh<=0.0016;thresholdHigh+=0.0002){
        for(int tokenNum=1;tokenNum<=30;tokenNum++){
            for(int badNum = 300;badNum<=300;badNum+=100) {
                for(int highMobilityNum=10;highMobilityNum<=10;highMobilityNum+=20) {

                    String fileName = "result.csv";
                    try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                        String fileContent = "Token:" + tokenNum + "," + "Bad UE:" + badNum + "\n";
                        fileWriter.write(fileContent);
                    } catch (IOException e) {
                    }

                    int ueNum = 3000, range = 2000;


                    Random ran = new Random();
                    Ue[] ue = new Ue[ueNum];
                    for (int i = 0; i < ueNum; i++) {   //initial
                        ue[i] = new Ue(tokenNum, range);
                        if (i < badNum)
                            ue[i].badUe = true;
                        if (i < badNum * (highMobilityNum / 100)) {
                            ue[i].mobility = 1;
                        }
                        if (i > badNum && i < ueNum * (highMobilityNum / 100)) {
                            ue[i].mobility = 1;
                        }
                        //System.out.printf("Ue:%d,X:%3d,Y:%3d,CQI:%2d\n", i, ue[i].x, ue[i].y, ue[i].cqi);
                    }

                    //see neighbor
        /*for(int i = 0; i< ueNum;i++){
            ue[i].table(ue, i);
            System.out.print("Ue"+i+":");
            for(int j=0;j<ue[i].neighbor.size();j++){
                System.out.printf("%2d ",ue[i].neighbor.get(j));

            }System.out.println();
        }*/

                    //myDraw drawing = new myDraw(ue, range);


                    int rnd = 200;
                    int[] resultTotal = new int[3];
                    resultTotal[0] = resultTotal[1] = resultTotal[2] = 0;

                    while (rnd > 0) {
                        System.out.println("TK:" + tokenNum + " Rnd:" + rnd);
                        for (int i = 0; i < ueNum; i++) {
                            ue[i].move(ue, i);
                            //System.out.println("Ue Moving: "+i);
                /*try{
                    Thread.sleep(10);
                }
                catch (InterruptedException ex){
                    Thread.currentThread().interrupt();
                }*/
                        }
                        int[] result;
                        result = sendPacket(ran, ue);
                        resultTotal[0] += result[0];
                        resultTotal[1] += result[1];
                        resultTotal[2] += result[2];

                        //draw network
                    /*try {
                        Thread.sleep(5);
                    }
                    catch(InterruptedException ex){
                        Thread.currentThread().interrupt();
                    }
                    drawing.draw();*/
                        if (tokenNum <= 5) {
                            int badue = 0, poorue = 0, tokenDec = 0;
                            ArrayList<Integer> poor = new ArrayList<>();
                            int tmpTotal = getTotalTk(ue, ueNum);
                            for (int i = 0; i < ueNum; i++) {
                                if (classify(ue, ueNum, ue[i],thresholdHigh) == 1) {
                                    int tmp = ue[i].token;
                                    ue[i].decline();
                                    tokenDec += tmp - ue[i].token + 1;
                                    badue++;
                                } else if (classify(ue, ueNum, ue[i],thresholdHigh) == -1) {
                                    poorue++;
                                    poor.add(i);
                                }
                            }
                            int tokenToGive = tmpTotal - getTotalTk(ue, ueNum);
                            for (int i = 0; i < poorue; i++) {
                                if (tokenToGive <= 0)
                                    break;
                                ue[poor.get(i)].giveTk(tmpTotal - getTotalTk(ue, ueNum), poorue);
                                if ((tmpTotal - getTotalTk(ue, ueNum)) / poorue < 1)
                                    tokenToGive--;
                                else
                                    tokenToGive = tokenToGive - ((tmpTotal - getTotalTk(ue, ueNum)) / poorue);
                            }
                        } else {
                            for (int i = 0; i < ueNum; i++) {
                                ue[i].decline();
                                ue[i].giveTk(tokenNum);
                            }
                        }

                        //System.out.println(getTotalTk(ue,ueNum));

                        rnd--;
                    }

                    //drawing.close();
            /*for(int co1=0;co1<ueNum;co1++){
                System.out.printf("Ue%2d: %3d\n",co1,ue[co1].token);
            }*/
                    //System.out.printf("D2D:%9d B2D:%9d DRP:%9d\n", resultTotal[0],resultTotal[1],resultTotal[2]);
                    //String fileName =  "result.csv";
                    try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                        String fileContent = resultTotal[0] + "," + resultTotal[1] + "," + resultTotal[2] + "\n";
                        fileWriter.write(fileContent);
                    } catch (IOException e) {
                    }
                }
            }
            }

        }
        return;
    }
}