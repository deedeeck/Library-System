ee
package tls;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Selection {
    Engine engine;

    public Selection()
    {
        engine = new Engine();
    }

    public void LibSystem(String[] args)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            String choice = "";
            while(!choice.equals("0"))
            {
                displayMenu();
                choice = br.readLine();
                dispatch(choice);
            }
            return;
        } catch (Exception ex)
        {
            System.err.println("\nERROR: Caught an " + " unexpected exception!");
        }
    }

    private void displayMenu()
    {
        System.out.println("\n\n\t\tWELCOME TO THE LIBRARY SYSTEM\n");
        System.out.println("1a Add a Member");
        System.out.println("1b Delete a Member");
        System.out.println();
        System.out.println("2a Add Titles");
        System.out.println("2b Add Books");
        System.out.println("2c Delete Books");
        System.out.println();
        System.out.println("3a Add Loan");
        System.out.println("3b Remove Loan");
        System.out.println("0 Exit");
        System.out.println("\nEnter choice: ");
    }

    private void dispatch(String choice)
    {
        if(choice.equals("1a"))
            engine.doAddMember();
        else if (choice.equals("1b"))
            engine.doDeleteMember();
        else if (choice.equals("2a"))
            engine.doAddTitle();
        else if (choice.equals("2b"))
            engine.doAddBook();
        else if (choice.equals("2c"))
            engine.doDeleteBook();
        else if (choice.equals("3a"))
            engine.doAddLoan();
        else if (choice.equals("3b"))
            engine.doDeleteLoan();
        else if (choice.equals("0"))
        {
            System.out.println("Exiting system....");
            return;
        }

        else
            System.out.println("\nERROR: Invalid choice.");
    }
}
