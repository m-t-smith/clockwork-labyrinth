package game.main;


import game.main.Room;
import game.main.PathCarver;
import game.main.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelBuilder {
    private List<Room> roomList; //maybe declare as arraylist
    private PathCarver carver;
    public static final int MINIMUM_ROOM_SIZE = 4;
    private int[][] tileMap;
    private Room startingRoom;
    private double[][] objMap;
    private int numRooms;
    private int mapSideLength;
        
    
    public LevelBuilder(int numRooms, int roomSize){
        
        this.numRooms = numRooms;
        roomList = new ArrayList<Room>();
        mapSideLength = (numRooms) * (roomSize);
        Map map = new Map(new int[mapSideLength][mapSideLength], mapSideLength);
        
//        System.out.println("MapSize: " + Math.pow(mapSideLength, 2));
        fillRoomList(numRooms, roomSize, map);
        
        for(Room room: roomList){
            fillRoom(room);
//            System.out.println("Room coordinates(x,y: " 
//                            + room.getX() + " " + room.getY());
//            System.out.println("Room Dimensions(width,height): "
//                              + "" + room.getWidth() + " " + room.getHeight());
                                
        }
        fillMap(map, 2);
        placeRooms(50, map);
        startingRoom = getRandomRoom(numRooms);
        carver = new PathCarver(map.getMap(), startingRoom);
        carver.startCarving(map.getMap(), startingRoom);
        makeWalls(map);
        for(Room room: roomList){
            //System.out.println("Room: " + room.getRoomID() );
            connectRooms(room, map);
        }
        tileMap = doubleMap(map.getMap());
       
        objMap = new double[tileMap.length][tileMap[0].length];
        makeObjMap(tileMap);
        
    }
    
    public int[][] getTileMap(){
        return tileMap;
    }
    
    public double[][] getObjMap(){
        return objMap;
    }
    public void makeObjMap(int[][] tileMap){
        
        for(int i = 0; i < tileMap.length; i++){
            for(int j = 0; j < tileMap[0].length; j++){
                int rand = (int)(Math.random()*20);
               
                if(tileMap[i][j] != 1 && rand > 17){
                    objMap[i][j] = 0.01;
                   
                } else {
                objMap[i][j] = Math.rint(tileMap[i][j])*3;
                }
            }
        }
        objMap = fillObjectMap(objMap);
        fillObjectMap(objMap);
    
    }
    private double[][] fillObjectMap(double[][] objMap){
        int i, k = 0, l = 0;
        int m = objMap.length -1;
        int n = objMap[0].length;
        /*  k - starting row index
        m - ending row index
        l - starting column index
        n - ending column index
        i - iterator
        */
          
        while (k < m && l < n)
        {
            // traverse the first row from the remaining rows
            for (i = l; i < n; ++i){
                if(k > 0 && k < m){
                    if(i > 0 && i < n){
                      if(objMap[k + 1][i + 1] < 1 && objMap[k + 1][i + 1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k-1][i-1] < 1 && objMap[k-1][i-1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k+1][i] < 1 && objMap[k + 1][i] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k][i+1] < 1 && objMap[k][i+1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k][i-1] < 1 && objMap[k][i-1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k-1][i] < 1 && objMap[k-1][i] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k-1][i+1] < 1 && objMap[k-1][i+1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                      if(objMap[k+1][i-1] < 1 && objMap[k + 1][i-1] > 0.001){
                          objMap[k][i] += 0.01;
                      }
                    }
                } 
            }
            k++;
  
            // traverse the last column from the remaining columns 
            for (i = k; i < m; ++i){
                if(i > 0 && i < m){
                    if(n-1 > 0 && n < objMap[0].length){
                      if(objMap[i + 1][n] < 1 && objMap[i + 1][n] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i-1][n-2] < 1 && objMap[i-1][n-2] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i+1][n-1] < 1 && objMap[i + 1][n-1] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i][n] < 1 && objMap[i][n] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i][n-2] < 1 && objMap[i][n-2] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i-1][n-1] < 1 && objMap[i-1][n-1] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i-1][n] < 1 && objMap[i-1][n] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                      if(objMap[i+1][n-2] < 1 && objMap[i + 1][n-2] > 0.001){
                          objMap[i][n-1] += 0.01;
                      }
                    }
                }
            }
            n--;
  
            // traverse the last row from the remaining rows */
            if (k < m){
                for (i = n-1; i > l; --i){
                    if(m-1 > 0 ){ 
                      if(objMap[m][i+1] < 1 && objMap[m][i+1 ] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m-2][i-1] < 1 && objMap[m-2][i-1] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m][i] < 1 && objMap[m][i] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m-1][i+1] < 1 && objMap[m-1][i+1] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m-1][i-1] < 1 && objMap[m-1][i-1] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m-2][i] < 1 && objMap[m-2][i] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m-2][i+1] < 1 && objMap[m-2][i+1] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                      if(objMap[m][i-1] < 1 && objMap[m][i-1] > 0.001){
                          objMap[m-1][i] += 0.01;
                      }
                    
                    }
                }
                m--;
            }
  
            // Print the first column from the remaining columns */
            if (l < n){
                if(l>0){
                for (i = m-1; i >= k; --i){
                    if(objMap[i + 1][l+1] < 1 && objMap[i + 1][l+1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i-1][l-1] < 1 && objMap[i-1][l-1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i+1][l] < 1 && objMap[i + 1][l] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i][l+1] < 1 && objMap[i][l+1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i][l-1] < 1 && objMap[i][l-1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i-1][l] < 1 && objMap[i-1][l] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i-1][l+1] < 1 && objMap[i-1][l+1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                      if(objMap[i+1][l-1] < 1 && objMap[i + 1][l-1] > 0.001){
                          objMap[i][l] += 0.01;
                      }
                }
                }
                l++;    
            }        
        }
//        for(double[] row: objMap){
//            System.out.println(Arrays.toString(row));
//        }
        return objMap;
    }
    
    private int[][] doubleMap(int[][] map){
        int len = map.length;
        int[][] bigAr = new int[len*2][len*2];
        int k = 0;
        int m;
      
        for(int i = 0; i < len; i++){
            m = 0;
            for(int j = 0; j < len; j++){
                
                int e = map[i][j];
                bigAr[i+k][j+m] = e;
                bigAr[i+1+k][j+1+m] = e;
                bigAr[i+k][j+1+m] = e;
                bigAr[i+1+k][j+m] = e;
                m++;
             
            }
            k++;
        }
        for(Room room: roomList){
            room.setX(room.getX()*2);
            room.setY(room.getY()*2);
            room.setHeight(room.getHeight()*2);
            room.setWidth(room.getWidth()*2);
        }
        return bigAr;
    }
    
    @Override
    public String toString(){
        String string = new String();
        for(int[] rows: tileMap){
           string += (Arrays.toString(rows)) + "\n";
        }
        return string;
    }
    
    public Room getStartingRoom(){
        return startingRoom;
    }
    public Room getRandomRoom(int numRooms){
       return roomList.get( (int) Math.floor(Math.random() * numRooms));
    }
    
    public void connectRooms(Room room, Map map){
        boolean connected = false;
        int[][] thisRoom = room.getRoomArray();
        int[][] myMap = map.getMap();
        int x; //carving x position
        int y; //carving y position
        int dx; //distance over x
        int dy; //distance over y
        int ux; //unit vector in x direction
        int uy; //unit vector in y direction
        
        for(int i = 0; i < thisRoom.length; i++){
            for(int j = 0; j< thisRoom[i].length; j++){
                if(i == 0 || i == thisRoom.length - 1){
                    if(thisRoom[i][j] == 0){
                        connected = true;
                    }
                }
                if(j == 0 || j == thisRoom[i].length - 1){
                    if(thisRoom[i][j] == 0){
                        connected = true;
                    }
                }
            }
        }
        if(!connected){
              
            //System.out.println("-Disconnect Detected-");

            Room farthest = getRandomRoom(numRooms);
            int tries = 30;
            
            while(farthest.getRoomID() == room.getRoomID() && tries > 0){
                            farthest = getRandomRoom(numRooms);
                            tries--;
                }
            
            for(Room other: roomList){
                
                if(other.getRoomID() != room.getRoomID() &&
                     (Math.abs(farthest.getX() - room.getX()) + 
                        Math.abs(farthest.getY() - room.getY())) <
                            (Math.abs(other.getX() - room.getX()) +
                                Math.abs(other.getY() - room.getY()))){
                                
                              farthest = other;
                }
            }
            //debug
            //System.out.println("Dest: X" + closest.getX() + "Y" + closest.getY());
            //myMap[closest.getY()][closest.getX()] = 6;
            y = room.getY() + ((room.getHeight() - 1) / 2);
            x = room.getX() + ((room.getWidth() - 1) / 2);
            
            dx = farthest.getX() + ((farthest.getWidth() - 1) / 2) - x;
            dy = farthest.getY() + ((farthest.getHeight() - 1) / 2) - y;
            
            ux = dx == 0 ? 0 : dx/Math.abs(dx); 
            uy = dy == 0 ? 0 : dy/Math.abs(dy);
            
            //Map m = new Map(myMap, mapSideLength);
            boolean wallPassed = false;
            while(dx != 0  || dy != 0){
              //  System.out.println("x: " + x + " y: " + y );
                if(myMap[y][x] == 1){
                    myMap[y][x] = 0;
                    wallPassed = true;
                }
                int v = Math.max(Math.abs(dy), Math.abs(dx));
                if(v == Math.abs(dx)){
                   x += ux;
                   dx -= ux;
                } else {
                   y += uy;
                   dy -= uy;
                }
                if(wallPassed && myMap[y][x] == 0){
                    dx = 0;
                    dy = 0;
                }
               //debug 
               // m.setMap(myMap);
               // System.out.println(m.toString());
            }
        map.setMap(myMap);
        }
    }
    
    public void makeWalls(Map map){
        int[][] mapAr = map.getMap();
        for(int i = 0; i < mapAr[0].length; i++){
            for(int j = 0; j < mapAr.length; j++){
                if(mapAr[i][j] == 2){
                    mapAr[i][j] = 1;
                }
            }
          
        }
    }
    
    public void fillMap(Map map, int fillNum){
        int[][] mapAr = map.getMap();
        for(int[] rows: mapAr){
            Arrays.fill(rows, fillNum);
        }
    }
    
    
    public void placeRooms(int attempts, Map map){
        int[][] mapAr = map.getMap();
        
        for(Room room: roomList){
            int[][] thisRoom = room.getRoomArray();
            int countAttempt = 0;
            boolean placed = false;
            while(countAttempt < attempts && placed == false){
                countAttempt++;
                int countEmptyTiles = 0;
                boolean mapRegionEmpty = false;
                //check room sized map area at room xy for valid room placement
                if((room.getY() + thisRoom[0].length) < mapAr[0].length
                           && room.getX() + thisRoom.length < mapAr.length){
                    for(int k = room.getY(); k 
                                < (room.getY() + thisRoom[0].length); k++){
                        for(int j = room.getX(); j 
                                    < (room.getX() + thisRoom.length); j++){
                            if(mapAr[k][j] == 2){
                                countEmptyTiles++;
                            }
                        }
                    } 
                } else {  //try another spot               
                       newXY(room, map); 
                       }
                if(countEmptyTiles == (thisRoom.length * thisRoom[0].length)){
                    mapRegionEmpty = true;
                }
                if(mapRegionEmpty){
                    //place room in map
                    for(int j = 0; j < thisRoom[0].length; j++){
                        for(int i = 0; i < thisRoom.length; i++){
                        mapAr[j + room.getY()][i + room.getX()] = thisRoom[i][j];
                        }
                    }
                    map.setMap(mapAr);
                    placed = true;    
                } else {   //try another spot                 
                   newXY(room, map);
                    
                }
            }
            if (!placed){
                roomList.remove(thisRoom);
            }
        }   
    }
    public void newXY(Room room, Map map){  //make new coordinates for room
        int randMapX = MINIMUM_ROOM_SIZE + ((int)(Math.random()
                                 * (map.getMapSideLength()
                                       - ((map.getMapSideLength()/2) + 5))));
        int randMapY = MINIMUM_ROOM_SIZE + ((int)(Math.random() 
                                 * ((map.getMapSideLength())
                                       - ((map.getMapSideLength()/2) + 5))));
        room.setX(randMapX);
        room.setY(randMapY);           
    }
    
    
    public void fillRoomList(int numRooms, int roomSize, Map map){
        for(int i = 0; i < numRooms; i++){
            int randRoomWidth =  MINIMUM_ROOM_SIZE 
                            + (int) (Math.random() * roomSize);
            int randRoomHeight = MINIMUM_ROOM_SIZE 
                            + (int) (Math.random() * roomSize);
            int randMapX = MINIMUM_ROOM_SIZE + ((int) (Math.random() 
                                * (map.getMapSideLength() 
                                    - ((map.getMapSideLength()/2) + 1))));
                                       
            int randMapY = MINIMUM_ROOM_SIZE + ((int) (Math.random() 
                                * (map.getMapSideLength()
                                   - ((map.getMapSideLength()/2) + 1))));
            Room room = new Room(randMapY, randMapX, randRoomWidth, randRoomHeight);
            roomList.add(room);
        
        }
            
    }


    public void fillRoom(Room room){
        int[][] roomD = room.getRoomArray();
        for(int i = 0; i < roomD.length; i++){
            for(int j = 0; j< roomD[i].length; j++){
                if(i == 0 || i == roomD.length - 1){
                    roomD[i][j] = 1;
                }
                if(j == 0 || j == roomD[i].length - 1){
                    roomD[i][j] = 1;
                }
            }
        }      
    } 
}
