import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game 
{
    public int height;
    public int width;
    private String[][] board;
    private ArrayList<int[]> snake = new ArrayList<int[]>();
    public String direction;
    private String snakeBody = "o";
    private int[] food = {0,0};
    private String foodBody = "X";
    private int score;

    private boolean bugfix = false;

    public Game(int height, int width)
    {
        score = 0;
        this.height = height;
        this.width = width;
        board = new String[width][height];
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                board[x][y] = " ";
            }
        }
        int[] coords = {0,0};
        snake.add(coords);
        int[] coords2 = {1,0};
        snake.add(coords2);
        int[] coords3 = {2,0};
        snake.add(coords3);
        int[] coords4 = {3,0};
        snake.add(coords4);
        int[] coords5 = {4,0};
        snake.add(coords5);
        direction = ">";
        generateFood();
    }
    public int getScore()
    {
        return score;
    }
    public void clearFrame()
    {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                board[x][y] = " ";
            }
        }
    }
    
    public void addSnake()
    {
        int count = 1;
        for(int i = 0; i < snake.size(); i++)
        {
            if(count != snake.size())
            {
                board[snake.get(i)[0]][snake.get(i)[1]] = snakeBody;
            }
            else
            {
                if(!bugfix)
                {
                    if(board[snake.get(i)[0]][snake.get(i)[1]] == snakeBody || board[snake.get(i)[0]][snake.get(i)[1]] == direction)
                    {
                        throw new IndexOutOfBoundsException();
                    }
                    else if(board[snake.get(i)[0]][snake.get(i)[1]] == foodBody)
                    {
                        board[snake.get(i)[0]][snake.get(i)[1]] = snakeBody;
                        score++;
                        growSnake(snake.get(0)[0],snake.get(0)[1]);
                        clearFrame();
                        bugfix = true;
                        addSnake();
                        bugfix = false;
                        generateFood();
                    }
                    else
                    {
                        board[snake.get(i)[0]][snake.get(i)[1]] = direction;
                    }
                }
            }
            count++;
        }
    }

    public void growSnake(int x, int y)
    {
        int[] coords = {x,y};
        snake.add(0, coords);
    }

    public void generateFood()
    {
        while(true)
        {
            int x = ThreadLocalRandom.current().nextInt(2, width - 1);
            int y = ThreadLocalRandom.current().nextInt(2, height - 1);
            if(isValid(x,y))
            {
                food[0] = x;
                food[1] = y;
                return;
            }
        }
    }

    public boolean isValid(int x, int y)
    {
        for(int[] coords: snake)
        {
            if(coords[0] == x && coords[1] == y)
            {
                return false;
            }
        }
        return true;
    }

    public String render()
    {
        String str = "";
        clearFrame();
        board[food[0]][food[1]] = foodBody;
        addSnake();
        str += "Score: " + score + "\n";
        str +="+";
        for(int i = 0; i < width; i++)
        {
            str += "-";
        }
        str += "+\n";
        for(int y = 0; y < height; y++)
        {
            str += "|";
            for(int x = 0; x < width; x++)
            {
                str += board[x][y];
            }
            str += "|\n";
        }
        str += "+";
        for(int i = 0; i < width; i++)
        {
            str += "-";
        }
        str += "+\n";
        return str;
    }

    public void step()
    {
        switch(direction)
        {
            case "v":
                int downx = snake.get(snake.size() - 1)[0];
                int downy = snake.get(snake.size() - 1)[1];
                downy++;
                int[] down = {downx, downy};
                snake.add(down);
                snake.remove(0);
                break;
            case ">":
                int rightx = snake.get(snake.size() - 1)[0];
                int righty = snake.get(snake.size() - 1)[1];
                rightx++;
                int[] right = {rightx, righty};
                snake.add(right);
                snake.remove(0);
                break;
            case "<":
                int leftx = snake.get(snake.size() - 1)[0];
                int lefty = snake.get(snake.size() - 1)[1];
                leftx--;
                int[] left = {leftx, lefty};
                snake.add(left);
                snake.remove(0);
                break;
            case "^":
                int upx = snake.get(snake.size() - 1)[0];
                int upy = snake.get(snake.size() - 1)[1];
                upy--;
                int[] up = {upx, upy};
                snake.add(up);
                snake.remove(0);
                break;
            default:
                break;
        }
    }
    
}
