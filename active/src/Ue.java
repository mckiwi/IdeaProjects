import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ue {

    int token;
    int cqi; // range/2 -> 0-10
    Random ran;
    int x,y,range;
    int[][] dir = new int[4][2];
    double declineRate = 0.8;//0.8
    boolean badUe = false;
    List<Integer> neighbor = new ArrayList<Integer>();

    Ue(int tk,int range){
        token=tk;
        this.range=range;
        //4 directions
        dir[0][0]=0;dir[0][1]=1;
        dir[1][0]=1;dir[1][1]=0;
        dir[2][0]=0;dir[2][1]=-1;
        dir[3][0]=-1;dir[3][1]=0;

        ran = new Random();
        x = ran.nextInt(range);
        y = ran.nextInt(range);
        //x=y= 100;
        //move();


    }

    boolean ifRelay(){  //true:direct false:relay
        if(cqi>6){  //b2d
            if(this.token>0){
                if(ran.nextInt(100)<70)
                    return false;
                else return true;
            }
            return true;
        }
        else{   //request relay
            if(badUe == true)
                return true;
            return false;
        }
    }
    void move(Ue[] other, int thisUeNum){
        neighbor.clear();
        while(true){
            int far = 20;
            int direction = ran.nextInt(4);
            x+=dir[direction][0]*far;
            y+=dir[direction][1]*far;
            if(x>=0 && x<=range && y>0 && y<=range)
                break;
            x-=dir[direction][0]*far;
            y-=dir[direction][1]*far;
        }
        changeCQI();
        table(other,thisUeNum);
    }
    void changeCQI() {
        double dist = Math.sqrt(Math.pow(x - range / 2, 2) + Math.pow(y - range / 2, 2));
        cqi = (int)(10 - (dist/(range/10)));
        if(cqi<0)
            cqi = 0;
    }
    void table(Ue[] other, int thisUeNum){
        //List<Integer> list = new ArrayList<Integer>();
        for(int co=0; co<other.length; co++){
            if(co != thisUeNum)
                if((Math.sqrt(Math.pow(other[co].x - this.x, 2 ) + Math.pow(other[co].y - this.y, 2 ))<50))
                    neighbor.add(co);
        }
        //this.neighbor = list;
    }

    void decline(){
        if(token>0){
            token*=declineRate;
        }
        return;
    }

    void giveTk(int tokenDec, int poorue){
        double tmp = (double)tokenDec;
        tmp = tokenDec/poorue;
        //if(token<2)     //threshold:2
            //token+=2;
            token = token+(int)tmp;
            if(tmp<1)
                token++;
        return;
    }
}

