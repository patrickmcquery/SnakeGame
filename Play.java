/*
 * Patrick McQuery Final Project
 * 
 * A snake game that plays in the terminal. Essentially "turn based" due to java terminal limitations.
 * You can save your scores (not sorted) with your name/initials, adjust the game size, and play repeatedly.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Play
{
    static int h = 10;
    static int w = 10;
    public static void main(String[] args)
    {
        System.out.println("Controls:\nW = ^\nA = <\nS = v\nD = >\nPress enter after each input to change direction.");
        System.out.println("You can also press/hold enter to continue in the same direction.");
        System.out.println("Press enter to continue");
        Scanner console = new Scanner(System.in);
        console.nextLine();
        try 
        {
            menu();
        } 
        catch (InterruptedException error) 
        {
            System.out.println("An error has occured.");
        }
    }
    public static void menu() throws InterruptedException
    {
        art();
        options();
        Scanner console = new Scanner(System.in);
        boolean menu = true;
        while(menu)
        {
            String resp = console.nextLine();
            switch(resp.toLowerCase().charAt(0))
                {
                    case 'p':
                        game();
                        art();
                        options();
                        break;
                    case 'c':
                        changeSize();
                        art();
                        options();
                        break;
                    case 'v':
                        viewScores();
                        art();
                        options();
                        break;
                    case 'q':
                        menu = false;
                        break;
                    default:
                        break;
                }
        }
    }

    public static void viewScores()
    {
        try
        {
            File file = new File("highscores.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) 
            {
              String score = reader.nextLine();
              System.out.println(score);
            }
          }
          catch (FileNotFoundException error)
          {
            System.out.println("An error occurred.");
          }
        Scanner console = new Scanner(System.in);
        System.out.println("Press enter to continue.");
        console.nextLine();
    }

    public static void saveScore(int score,String initials)
    {
        String str = "";
        File file = new File("highscores.txt");
        if(file.isFile())
        { 
            try
            {
            File scores = new File("highscores.txt");
            Scanner reader = new Scanner(scores);
            while (reader.hasNextLine()) 
            {
              String in = reader.nextLine();
              str += in + "\n";
            }
            reader.close();
          }
          catch (FileNotFoundException error)
          {
            System.out.println("An error occurred.");
          }
          str += initials + ": ";
          str += String.valueOf(score);
          try 
            {
                FileWriter writer = new FileWriter("highscores.txt");
                writer.write(str);
                writer.close();
            } 
            catch (IOException error) 
            {
                System.out.println("An error occurred.");
            }
        }
        else
        {
            try 
            {
                File myObj = new File("highscores.txt");
                myObj.createNewFile();
              } 
              catch (IOException error) 
              {
                System.out.println("An error occurred.");
              }
            try 
            {
                FileWriter writer = new FileWriter("highscores.txt");
                writer.write(" ___   ___   ___   ___    ___   ___\n" +
                             "| __| |  _| |   | | _ |  | __| | __|\n" +
                             "|__ | | |_  | | | |   \\  | __| |__ |\n" +
                             "|___| |___| |___| |_|\\_\\ |___| |___|\n\n");
                writer.write(initials + ": " + String.valueOf(score));
                writer.close();
            } 
            catch (IOException e) 
            {
                System.out.println("An error occurred.");
            }
        }
    }
    public static void game() throws InterruptedException
    {
        Scanner console = new Scanner(System.in);
        Game game = new Game(h,w);
        boolean gaming = true;
        while(gaming)
        {
            try
            {
                System.out.print(game.render());
            }
            catch(IndexOutOfBoundsException error)
            {
                youDied(game.getScore());
                System.out.print("Please enter your initials: ");
                String initials = console.nextLine();
                saveScore(game.getScore(), initials);
                gaming = false;
            }
            if(gaming)
            {
                String resp = console.nextLine();
                if(resp == "")
                {
                    resp = "x";
                }
                switch(resp.toLowerCase().charAt(0))
                {
                    case 'w':
                        game.direction = "^";
                        break;
                    case 'a':
                        game.direction = "<";
                        break;
                    case 's':
                        game.direction = "v";
                        break;
                    case 'd':
                        game.direction = ">";
                        break;
                    default:
                        break;
                }
                try
                {
                    game.step();
                }
                catch(IndexOutOfBoundsException error)
                {
                    youDied(game.getScore());
                    System.out.print("Please enter your initials: ");
                    String initials = console.nextLine();
                    saveScore(game.getScore(), initials);
                    gaming = false;
                }
            }
        }
    }

    public static void changeSize()
    {
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter your new board size. Default size is 10 x 10. Current size is " + w + " x " + h);
        System.out.print("Width: ");
        w = console.nextInt();
        System.out.print("Height: ");
        h = console.nextInt();
    }
    public static void options()
    {
        System.out.println("             [P]lay");
        System.out.println("       [C]hange size (" + w + " x " + h + ")");
        System.out.println("        [V]iew High Scores");
        System.out.println("             [Q]uit");
    }
    public static void art() throws InterruptedException
    {
        System.out.println(" ___   _____   ___   _  __  ____ ");
        System.out.println("| __| |   | | | - | | |/ / | ___|");
        System.out.println("|__ | | | | | | | | |   <  | ___|");
        System.out.println("|___| |_|___| |_|_| |_|\\_\\ |____|");
        System.out.println("\n        By Patrick McQuery\n\n");
        Thread.sleep(1000);
    }

    public static void youDied(int score) throws InterruptedException
    {
        System.out.println(" __ __  ___   _ _     ____   _   ___   ____");
        System.out.println(" \\ V / |   | | | |   |    | | | | __| |    |");
        System.out.println("  \\ /  | | | | | |   |  | | | | | __| |  | |");
        System.out.println("  |_|  |___| |___|   |____| |_| |___| |____|");
        System.out.println("\n             Your score was " + score + ".\n\n");
        Thread.sleep(1000);
    }
}