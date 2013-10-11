package tls;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;

public class Input
{
    Scanner mysc = new Scanner(System.in);
    
    public Input(){}

    public String getString(String attrName, String oldValue)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String stringValue = null;

        try
        {
         while(true)
            {
                 System.out.print("Enter " + attrName +
                         (oldValue==null?"":"(" + oldValue + ")") +" : ");
                 stringValue = br.readLine();

                 if(stringValue.length() != 0)
                 {
                     break;
                 }

                 else if (stringValue.length() == 0 && oldValue != null)
                 {
                     stringValue = oldValue;
                     break;
                 }
                 System.out.println("Invalid " + attrName + "...");
             }
         }
        catch(Exception ex)
         {
             System.out.println("\nERROR: " + ex.getMessage());
         }
        return stringValue.trim();
    }

    public Long getLong(String attrName)
    {
     
     Long input = -1L;
     try
     {
         while(true)
         {
            System.out.print("Enter " +attrName+ " : ");
            input = mysc.nextLong();
        
            if(input != null)
            break;
         }

     }
     catch(Exception ex)
         {
             System.out.println("\nERROR: " + ex.getMessage());
         }
     return input;
    }

    public int getInt(String attrName)
    {
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     int input = -1;

     try{
         while(true)
         {
        System.out.print("Enter " +attrName+ " : ");
        input = mysc.nextInt();
        Integer checkinput = input;
        if(checkinput != null)
            break;
         }

     }
     catch(Exception ex)
         {
             System.out.println("\nERROR: " + ex.getMessage());
         }
     return input;
    }

    public Date getDateFromString(String attrName)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = null;
        Date dateValue = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            while(true)
            {
                System.out.print("Enter the " + attrName + " (yyyy-mm-dd) : ");
                s = br.readLine();

                if(s.length() != 0)
                {
                    try
                    {
                        dateValue = sdf.parse(s);
                        break;
                    } catch(ParseException ex){
                        System.out.println("\nInvalid Date \n");
                    }
                }
                System.out.println("Invalid " + attrName + "...");
            }// end while
        } catch(Exception ex)
        {
            System.out.println("\nERROR: " + ex.getMessage());
        }
        return dateValue;
    }
}
