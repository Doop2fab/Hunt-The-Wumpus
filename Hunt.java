import java.io.*;
import java.util.*;
/**
  Driver class for The Hunt The Wumpus programming assignment.
  @author Andrew Spores
*/
public class Hunt
{
  /**
   * Checks to see if any of the adjacent rooms are traps / Wumpus
   * @param d = A single room of the dungeon
   * @param dungeon = Array of all dungeon rooms
   */
  public static void isAdjacentTrap(DungeonRoom d, DungeonRoom [] dungeon)
  {
    int[] adjacentRooms = {d.getLeftRoom(), d.getMiddleRoom(), d.getRightRoom()};
    
    if((isTrap(dungeon[adjacentRooms[0] - 1]) == 1 || isTrap(dungeon[adjacentRooms[1]- 1]) == 1 || isTrap(dungeon[adjacentRooms[2]- 1]) == 1))
    {System.out.println("You smell some nasty Wumpus!");}

    if((isTrap(dungeon[adjacentRooms[0]- 1]) == 2 || isTrap(dungeon[adjacentRooms[1]- 1]) == 2 || isTrap(dungeon[adjacentRooms[2]- 1]) == 2))
    {System.out.println("You hear a faint clicking noise.");}

    if((isTrap(dungeon[adjacentRooms[0]- 1]) == 3 || isTrap(dungeon[adjacentRooms[1]- 1]) == 3 || isTrap(dungeon[adjacentRooms[2]- 1]) == 3))
    {System.out.println("You smell a dank odor.");}
  }

  /**
   * Selects random rooms with no duplicates
   * @param numOfRooms number of rooms in dungeon
   * @return array of random rooms to be set as traps
   */
  public static int[] roomSelector(int numOfRooms)
  {
    int num = numOfRooms - 1;
    int [] rand = new int[num];
    for(int i=0;i<num;i++)
    {
      rand[i]=(int)(1+Math.random()*num);
      if(i>0)
        {
          for(int k=0;k<=i-1;k++)
          {if(rand[k]==rand[i]){i--;}}
        }          
    }
    return rand;     
  } 

  public static void addWumpus(DungeonRoom d){d.setRoomTrap(1);}
  public static void addSpiders(DungeonRoom d){d.setRoomTrap(2);}
  public static void addPits(DungeonRoom d){d.setRoomTrap(3);}

  /**
   * reads out where the user is in the dungeon and how many arrows they have left
   * @param currentRoom what room user is currently in
   * @param leftRoom room to the left
   * @param middleRoom room in front
   * @param rightRoom room to the right
   * @param sign custom message
   * @param arrows amount of arrows
   * @param dungeon room in dungeon
   * @param repeat should function omit some known info (repeating to user)
   */
  public static void dungeonLocation(int currentRoom, int leftRoom, int middleRoom, int rightRoom, String sign, int arrows, DungeonRoom [] dungeon, boolean repeat)
  {
    if(repeat = false)
    {
      System.out.println("You are currently in room " + currentRoom);
      System.out.println("You have " + arrows + " arrows left.");
      System.out.println(sign);
      System.out.println("There are tunnels to rooms " + leftRoom + ", " + middleRoom + ", and " + rightRoom + ".");
    }
    else{System.out.println("There are tunnels to rooms " + leftRoom + ", " + middleRoom + ", and " + rightRoom + ".");}
  }

  /**
   * Asks user what they want to do
   * @return user's action
   */
  public static int userAction()
  {
    Scanner cin=new Scanner(System.in);
    while(true)
    {
      System.out.println("(M)ove or (S)hoot?");
      String userSelection = cin.nextLine();
      if(userSelection.equals("M"))
      {
        System.out.println("Which room?");
        int roomSelection = cin.nextInt();
        return roomSelection;
      }
      else if(userSelection.equals("S")){return 0;}
      else{System.out.println("Invalid Selection!");}
    }
  }

  public static int currentRoom(DungeonRoom d) {return d.getCurrentRoom();}
  public static int leftRoom(DungeonRoom d) {return d.getLeftRoom();}
  public static int middleRoom(DungeonRoom d) {return d.getMiddleRoom();}
  public static int rightRoom(DungeonRoom d) {return d.getRightRoom();}
  public static int isTrap(DungeonRoom d) {return d.getRoomTrap();}
  public static String sign(DungeonRoom d) {return d.getRoomSign();}
  public static void main(String [] args) throws IOException
  {
    Scanner fin=new Scanner(new FileReader("rooms.txt")); //Read in the rooms.txt file
    boolean repeat = false; //Did the user choose an invalid room?
    boolean userHasWon = false; //Has the user won the game?
    int roomSelection = 0; //Room int that the user selects to move to
    int arrows = 3; //Amount of arrows user has
    int numOfRooms = fin.nextInt(); //How many rooms are in the dungeon
    
    int [] trapRooms = roomSelector(numOfRooms);
		DungeonRoom [] dungeon = new DungeonRoom[numOfRooms]; // array of rooms
    
    for(int i=0; i<numOfRooms; i++) // load the dungeons into array
    {dungeon[i]=new DungeonRoom(fin);}

    for(int i=0; i<2; i++) //Add spiders into rooms
    {addSpiders(dungeon[trapRooms[i]]);}

    for(int i=2; i<4; i++) //Add pits into rooms
    {addPits(dungeon[trapRooms[i]]);}

    addWumpus(dungeon[trapRooms[5]]); //Add Wumpus into a room
    
    for(int i=0; i<10; i++)  //debug for traps in rooms
    {System.out.println(isTrap(dungeon[i]));}

    System.out.println("Welcome to **Hunt The Wumpus!**\n");
    while(userHasWon == false)
    {
      dungeonLocation(currentRoom(dungeon[roomSelection]), leftRoom(dungeon[roomSelection]), middleRoom(dungeon[roomSelection]), rightRoom(dungeon[roomSelection]), sign(dungeon[roomSelection]), arrows, dungeon, repeat);
      isAdjacentTrap(dungeon[roomSelection], dungeon);
      Scanner cin=new Scanner(System.in);
      int userAction = userAction();
      if(userAction == 0)
      {
        System.out.println("Which room would you like to shoot? ");
        int roomShoot = cin.nextInt();
        if(roomShoot == leftRoom(dungeon[roomSelection]) || roomShoot == middleRoom(dungeon[roomSelection]) || roomShoot == rightRoom(dungeon[roomSelection]))
        {
          if(arrows > 0) //If user still has an arrow left
          {
            arrows--; System.out.println("You shoot into room " + roomShoot + "!");
            if(isTrap(dungeon[roomShoot - 1]) == 1){System.out.println("You shot the Wumpus! You win!"); break;}
            else{ System.out.println("You missed! You now have " + arrows + " arrows!\n"); repeat = false;}
          }
          else{System.out.println("You are out of arrows!");}
        }
        else {System.out.println("Dimwit! You can't shoot into room " + roomShoot + "!"); repeat = true;}
      }
      else
      {
        if(userAction == leftRoom(dungeon[roomSelection]) || userAction == middleRoom(dungeon[roomSelection]) || userAction == rightRoom(dungeon[roomSelection]))
        {
          roomSelection = userAction - 1; repeat = false; 
          
          if(isTrap(dungeon[roomSelection]) == 1)
          {System.out.println("You've been eaten by the Wumpus! GAME OVER"); break;}
          if(isTrap(dungeon[roomSelection]) == 2)
          {System.out.println("You've been poisoned by the spiders! GAME OVER"); break;}
          if(isTrap(dungeon[roomSelection]) == 3)
          {System.out.println("You fell down a pit! GAME OVER"); break;}
          else{/*Player does not lose*/}
        }
        else {System.out.println("Dimwit! You can't get to room " + userAction + " from here!"); repeat = true;}
      }  
    }
  }
}