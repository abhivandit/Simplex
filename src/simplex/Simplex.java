/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;

import java.util.*;

/**
 *
 * @author Abhivandit
 */
public class Simplex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int numberofconstraints,numberofvariables;
       
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the number of constraints(excluding the non-negativity constraints) and number of variables");
        numberofconstraints=sc.nextInt();
        numberofvariables=sc.nextInt();
        double variablearray[][]=new double[numberofconstraints][numberofvariables+numberofconstraints];
        double rhs[]=new double[numberofconstraints+1];
        //Arrays.fill(variablearray,0.0);
       // System.out.println("enter the number of variables");
        System.out.println("enter the constraints in the format shown(coefficients)");
        for(int i=0;i<numberofvariables;i++){
            System.out.print("x"+i+" ");
        }
        System.out.println("y");
        for(int i=0;i<numberofconstraints;i++){
            for(int j=0;j<numberofvariables;j++){
                variablearray[i][j]=sc.nextDouble();
                
            }
            variablearray[i][i+numberofvariables]=1;
            rhs[i]=sc.nextDouble();
            
        }
        /*for(int i=0;i<numberofconstraints;i++){
            for(int j=0;j<numberofvariables+numberofconstraints;j++){
                System.out.print(variablearray[i][j]+" ");
            }
            System.out.println();
        }*/
       /* for (int i=0;i<=numberofconstraints;i++){
            System.out.print(rhs[i]+" ");
        }*/
        
        System.out.println("enter the negativity, non negativity constraints for all the variables(enter (1) for >=0 and (-1) for <=0");
         for(int i=0;i<numberofvariables;i++){
            System.out.print("x"+i+" ");
        }
           for(int i=0;i<numberofvariables;i++){
            int next=sc.nextInt();
            if(next==((-1))){
                for(int j=0;j<numberofconstraints;j++){
                    variablearray[j][i]=(-1)*variablearray[j][i];
                }
            }
        }//taken all the inputs checkd the constraints
        double objective[]=new double[numberofconstraints+numberofvariables];
        Arrays.fill(objective,0);
        int obj;
       System.out.println("enter the objective function in the format shown");
       System.out.print("(1 for maxmization 0 for min) ");
       for(int i=0;i<numberofvariables;i++){
            System.out.print("x"+i+" ");
        }
       obj=sc.nextInt();
       for(int i=0;i<numberofvariables;i++){
           if(obj==0){
            objective[i]=(-1)*sc.nextDouble();
           }
           else{
               objective[i]=sc.nextDouble();
           }
       }
       int flag=0;
      /* for(int i=0;i<numberofconstraints+numberofvariables;i++){
           System.out.println(objective[i]);}*/
       //int basicvariables;
       int pivotrow,pivotelement;
       double thetha[]=new double[numberofconstraints];
       int basicvariables[]=new int[numberofconstraints];
       int i=0;
       int enteringcolumn=0,leavingrow=0;
       for(i=0;i<numberofconstraints;i++){
           basicvariables[i]=i+numberofvariables;
           //System.out.print(basicvariables[i]+" ");
       }//basicvaribales column
       i=0;
        int sum=numberofvariables+numberofconstraints;
       double cost[]=new double[sum];
      double optimum;
       while(flag==0){
           double max=0;
           rhs[numberofconstraints]=0;//new addition
           for(int j=0;j<(sum);j++){//cj-zj
               double newsum=0;
             int flag2=0;
                for(int k=0;k<numberofconstraints;k++){
                    if(j==basicvariables[k]){
                        flag2=1;
                        break;
                    }
                }
                if(flag2!=1){
               for(int k=0;k<numberofconstraints;k++){//calculation of zj
                   newsum+=((objective[basicvariables[k]])* variablearray[k][j]);
                 
               }
               newsum=objective[j]-newsum;
               //System.out.print(newsum+"j "+j+" ");
                }
            if(flag2!=1 && newsum==0){
                flag=1;
                break;
            }
            else{
              //  System.out.println("flag2 "+flag2+" "+newsum);
                if(newsum>max){
                    max=newsum;
                    enteringcolumn=j;
                }
            }//max of cj-zj
         
       }//end of all cj-zj
            // System.out.println("max"+max);
      if(flag!=1){//if aternate soultion reached
     for(int k=0;k<numberofconstraints;k++){//calculation of zj
        rhs[numberofconstraints]+=((objective[basicvariables[k]])*rhs[k]);
       
        }
    System.out.println(rhs[numberofconstraints]+"here");
        if(max<=0){
            flag=2;
            break;
         }//optimum solution found
       int unboundedflag=0;
       leavingrow=0;
       for(int k=0;k<numberofconstraints;k++){
           if(variablearray[k][enteringcolumn]>0){
            thetha[k]=rhs[k]/variablearray[k][enteringcolumn];
            if(thetha[k]<thetha[leavingrow]){
                leavingrow=k;
            }
            unboundedflag=1;
           }
           
       }//checking for unboundedness and assigning the thetha parameters
         
         if(unboundedflag==0){
          flag=3;
          break;
        }
        
      }//if(flag!=1)
      
      if(flag<=0){//doing the row major operations
          basicvariables[leavingrow]=enteringcolumn;
          double dividefactor=variablearray[leavingrow][enteringcolumn];
          for(int k=0;k<sum;k++){
              if(k==enteringcolumn){
                  variablearray[leavingrow][enteringcolumn]=1;
              
                //  System.out.println(leavingrow+" "+enteringcolumn+"here");
              }
              else{
                  //System.out.println( variablearray[leavingrow][k]+" ");
                  variablearray[leavingrow][k]/=dividefactor;
                   // System.out.println( variablearray[leavingrow][k]+" ");
              }
          }///getting the identity form dvide by pivotelement
          rhs[leavingrow]=rhs[leavingrow]/dividefactor;
          for(int k=0;k<numberofconstraints;k++){
              if(k!=leavingrow){
                  double factor=variablearray[k][enteringcolumn]*(-1);
                  for(int l=0;l<sum;l++){
                      variablearray[k][l]+=variablearray[leavingrow][l]*factor;
                  }//for
                  variablearray[k][enteringcolumn]=0;
                  rhs[k]+=rhs[leavingrow]*factor;// change here
              }//k not equal to leaving row
          }
      }//flag<=0 
      //System.out.println(enteringcolumn+" "+leavingrow+" "+flag);
     
    }//while
       
  for(int  j=0;j<rhs.length;j++){
    //  System.out.println("dd"+rhs[j]);
  }
       System.out.println("here is the answer(1 alternate solution,2 unique solution,3 unbounded) "+flag);
    }
}
