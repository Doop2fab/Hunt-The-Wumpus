import java.util.*;
import java.io.*;
/**
 * @author Andrew Spores
 */
public class DungeonRoom //implements RoomInterface 
{
    
    int currentRoom; int leftRoom; int middleRoom; int rightRoom; int roomTrap;
    String signMessage;
    
    public DungeonRoom(Scanner fin)
    {currentRoom = fin.nextInt(); leftRoom = fin.nextInt(); middleRoom = fin.nextInt(); rightRoom = fin.nextInt(); 
    fin.nextLine();
    signMessage = fin.nextLine(); roomTrap = 0;}

    int[] adjacentRooms = {leftRoom, middleRoom, rightRoom};

    /**@return Room to the left of player */
    public int getLeftRoom(){return leftRoom;}
    /**@return Room in front of player */
    public int getMiddleRoom(){return middleRoom;}
    /**@return Room to the right of player */
    public int getRightRoom(){return rightRoom;}
    /**@return Current room player is in */
    public int getCurrentRoom(){return currentRoom;}
    /**@return Message on sign in room */
    public String getRoomSign(){return signMessage;}
    /**
    * @return Whether the room has a trap in it or not, and if it does specifies what trap it is
    * 0 = no trap
    * 1 = Wumpus
    * 2 = bottomless pit
    * 3 = Poisonous spiders
    */
    public void setRoomTrap(int type){roomTrap = type;}
    public int getRoomTrap(){return roomTrap;}
    public int[] getAdjacentRooms(){System.out.println( adjacentRooms[1]); return adjacentRooms;}
    
}