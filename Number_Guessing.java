import java.util.Random;
import java.util.Scanner;


 class Task_2
{
    private static final int MIN_RANGE=1;
    private static final int MAX_RANGE=100;
    private static final int MAX_ROUNDS=5;
    private static final int MAX_ATTEMPTS=8;
    public static void main(String args[])
    {
        Random random=new Random();
        Scanner sc=new Scanner(System.in);
        int Total_score=0;

        System.out.println();
        System.out.println("                                 NUMBER GUESSING GAME  ");
        System.out.println();
        System.out.println("Total Rounds = "+MAX_ROUNDS);
        System.out.println("Attempts In each Round = "+MAX_ATTEMPTS);


        for(int i=1;i<=MAX_ROUNDS;i++)
        {
            int number=random.nextInt(MAX_RANGE);
            int attempts=0;

            System.out.println( "Round :"+i+" Guess the number between "+MIN_RANGE+" and "+MAX_RANGE+" In "+MAX_ATTEMPTS+" attempts");
            while(attempts!=MAX_ATTEMPTS)
            {
                int Guess_number;
                System.out.println("Enter ur number");
                Guess_number=sc.nextInt();
                attempts++;


                if(Guess_number==number)
                {
                    System.out.println("_____THE NUMBER HAS BEEN GUESSED SUCCESSFULLY_____ "+number);
                    System.out.println();
                    int score=MAX_ATTEMPTS-attempts;
                    Total_score=Total_score+score;
                    System.out.println("Number of attempts = "+attempts+ "\nRound score = "+score);
                    break;
                }
                else if(number>Guess_number)
                {
                    System.out.println("The Guessed number "+Guess_number+" is less than the Number\nAttempts Left = "+(MAX_ATTEMPTS-attempts));
                }
                else
                {
                    System.out.println("The Guessed number "+Guess_number+" is Greater than the Number\nAttempts Left = "+(MAX_ATTEMPTS-attempts));
          
                }
            }
            if(attempts==MAX_ATTEMPTS)
            {
                System.out.println("Round Number = "+i);
                System.out.println("Attempts Left = 0");
                System.out.println("THE RANDOM NUMBER IS = "+number);
            }
        }
        System.out.println();
        System.out.println("********** GAME OVER **********");
        System.out.println("TOTAL SCORE = "+Total_score);
        sc.close();
    }
}
