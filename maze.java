import java.io.*;
import java.nio.file.*;
import java.util.*;

public class maze {
    static char[][] maze;
    static int[] start = new int[2];
    static int[] end = new int[2];
    
    static void readMaze(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        maze = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'S') {
                    start[0] = i; start[1] = j;
                }
                if (maze[i][j] == 'E') {
                    end[0] = i; end[1] = j;
                }
            }
        }
    }
    
    static void printMaze() {
        for (char[] row : maze) {
            System.out.println(row);
        }
        System.out.println();
    }
    
    static boolean isValid(int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && 
               (maze[row][col] == '.' || maze[row][col] == 'E');
    }
    
    static void markVisited(int row, int col) {
        if (maze[row][col] != 'E' && maze[row][col] != 'S') {
            maze[row][col] = '*';
        }
        printMaze();
    }

    static int[] findStart() {
        int row;
        int col; 
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'S') {
                    row = i;
                    col = j;
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }
    
    static List<int[]> solveDFS() {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        
        // TODO: Implement DFS
        // Helper methods available:
        // - isValid(row, col): check if position is valid
        // - markVisited(row, col): mark position and print current maze path
        // Return the path as list of int[2] arrays containing [row, col]

        path.add(findStart());

        while(maze[path.getLast()[0]][path.getLast()[1]] != 'E') {
            if(findValidUnvisitedSpace(path.getLast()) != null) {
                path.add(findValidUnvisitedSpace(path.getLast()));
                markVisited(path.getLast()[0], path.getLast()[1]);
            } else {
                path.removeLast();
            }
        }
        
        return path;
    }

    /**
     * Checks for a valid space adjacent to the provided space in the four cardinal
     * directions. Returns null if one isn't found.
     *
     * @param currentSpace The space you are currently in.
     * @return A valid adjacent space, or null if none exist.
     */
    static int[] findValidUnvisitedSpace(int[] currentSpace) {
        if(currentSpace[1] - 1 >= 0 && isValid(currentSpace[0], currentSpace[1] - 1))
            return new int[]{currentSpace[0], currentSpace[1] - 1};

        if(currentSpace[0] - 1 >= 0 && isValid(currentSpace[0] - 1, currentSpace[1]))
            return new int[]{currentSpace[0] - 1, currentSpace[1]};

        if(currentSpace[0] + 1 < maze.length && isValid(currentSpace[0] + 1, currentSpace[1]))
            return new int[]{currentSpace[0] + 1, currentSpace[1]};

        if(currentSpace[1] + 1 < maze[0].length && isValid(currentSpace[0], currentSpace[1] + 1))
            return new int[]{currentSpace[0], currentSpace[1] + 1};

        return null;
    }
    
    static List<int[]> solveBFS() {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        
        // TODO: Implement BFS
        // Helper methods available:
        int[] start = findStart();

        List<List<int[]>> queue = new ArrayList<>();
        List<int[]> ogPath = new ArrayList<>();
        ogPath.add(start);
        queue.add(ogPath);

        while (!queue.isEmpty()) {
            List<int[]> currentPath = queue.removeFirst();
            int[] currentSpace = currentPath.getLast();
            if (maze[currentSpace[0]][currentSpace[1]] == 'E') {
                return currentPath;
            }
            markVisited(currentSpace[0], currentSpace[1]);
            List<int[]> valid = findAdjacentSpaces(currentSpace[0], currentSpace[1]);
            for (int[] validSpace : valid) {
                List<int[]> newPath = new ArrayList<>(currentPath);
                newPath.add(validSpace);
                queue.add(newPath);
            }
            path = currentPath;
        }
        
        // - isValid(row, col): check if position is valid
        // - markVisited(row, col): mark position and print current maze path
        // Return the path as list of int[2] arrays containing [row, col]
        return path;
    }

    static List<int[]> findAdjacentSpaces(int row, int col) {
        List<int[]> valid = new ArrayList<>();
        if (isValid(row, col + 1)) {
            valid.add(new int[] {row, col + 1});
        }

        if (isValid(row, col - 1)) {
            valid.add(new int[] {row, col - 1});
        }

        if (isValid(row + 1, col)) {
            valid.add(new int[] {row + 1, col});
        }

        if (isValid(row - 1, col)) {
            valid.add(new int[] {row - 1, col});
        }

        return valid;
    }
    
    public static void main(String[] args) throws IOException {
        readMaze("maze.txt");

        System.out.println("DFS Solution:");
        List<int[]> dfsPath = solveDFS();
        for (int[] pos : dfsPath) {
            System.out.printf("[%d,%d] ", pos[0], pos[1]);
        }
        
        readMaze("maze.txt");  // Reset maze
        System.out.println("\n\nBFS Solution:");
        List<int[]> bfsPath = solveBFS();
        for (int[] pos : bfsPath) {
            System.out.printf("[%d,%d] ", pos[0], pos[1]);
        }
    }

    // Group Members: Logan Harris, Teresa Trinh, Jacob Rieker
        
    // Answers:
    //     What is the path for each?
    //     Small:
    //         DFS: [1,1] [2,1] [3,1] [4,1] [4,2] [4,3] [3,3] [2,3] [1,3] [1,4] [1,5] [2,5] [3,5]
    //         BFS: [1,1] [2,1] [3,1] [4,1] [4,2] [4,3] [4,4] [4,5] [3,5]
                
    //     Medium:
    //         DFS: [1,1] [1,2] [1,3] [2,3] [3,3] [3,4] [3,5] [2,5] [1,5] [1,6] [1,7] [1,8] [1,9] [1,10] [1,11] [1,12] [1,13] [2,13] [3,13] [4,13] [5,13] [6,13] [7,13] [8,13] [9,13] [10,13] [11,13]
    //         BFS: [1,1] [1,2] [1,3] [2,3] [3,3] [3,4] [3,5] [2,5] [1,5] [1,6] [1,7] [1,8] [1,9] [1,10] [1,11] [1,12] [1,13] [2,13] [3,13] [4,13] [5,13] [6,13] [7,13] [8,13] [9,13] [10,13] [11,13]
                
    //     Large:
    //         DFS: [1,1] [2,1] [3,1] [4,1] [5,1] [6,1] [7,1] [8,1] [9,1] [10,1] [11,1] [12,1] [13,1] [14,1] [15,1] [16,1] [17,1] [18,1] [19,1] [20,1] [21,1] [22,1] [23,1] [24,1] [25,1] [25,2] [25,3] [24,3] [23,3] [22,3] [21,3] [20,3] [19,3] [18,3] [17,3] [16,3] [15,3] [14,3] [13,3] [12,3] [11,3] [10,3] [9,3] [9,4] [9,5] [8,5] [7,5] [7,6] [7,7] [7,8] [7,9] [7,10] [7,11] [6,11] [5,11] [5,12] [5,13] [6,13] [7,13] [7,14] [7,15] [6,15] [5,15] [4,15] [3,15] [3,14] [3,13] [3,12] [3,11] [2,11] [1,11] [1,12] [1,13] [1,14] [1,15] [1,16] [1,17] [2,17] [3,17] [4,17] [5,17] [6,17] [7,17] [7,18] [7,19] [8,19] [9,19] [10,19] [11,19] [12,19] [13,19] [14,19] [15,19] [16,19] [17,19] [18,19] [19,19] [19,20] [19,21] [20,21] [21,21] [22,21] [23,21] [24,21] [25,21] [25,22] [25,23] [25,24] [25,25] [24,25] [23,25] [22,25] [21,25] [21,26] [21,27] [20,27] [19,27] [19,26] [19,25] [18,25] [17,25] [17,24] [17,23] [17,22] [17,21] [16,21] [15,21] [14,21] [13,21] [12,21] [11,21] [10,21] [9,21] [9,22] [9,23] [10,23] [11,23] [12,23] [13,23] [14,23] [15,23] [15,24] [15,25] [15,26] [15,27] [16,27] [17,27] [17,28] [17,29] [18,29] [19,29] [20,29] [21,29] [22,29] [23,29] [23,30] [23,31] [22,31] [21,31] [20,31] [19,31] [18,31] [17,31] [16,31] [15,31] [15,30] [15,29] [14,29] [13,29] [13,30] [13,31] [12,31] [11,31] [10,31] [9,31] [8,31] [7,31] [7,30] [7,29] [6,29] [5,29] [5,30] [5,31] [4,31] [3,31] [3,30] [3,29] [3,28] [3,27] [3,26] [3,25] [2,25] [1,25] [1,26] [1,27] [1,28] [1,29] [1,30] [1,31] [1,32] [1,33] [2,33] [3,33] [4,33] [5,33] [6,33] [7,33] [8,33] [9,33] [10,33] [11,33] [12,33] [13,33] [14,33] [15,33] [16,33] [17,33] [18,33] [19,33] [20,33] [21,33] [22,33] [23,33] [24,33] [25,33]
    //         BFS: [1,1] [2,1] [3,1] [4,1] [5,1] [6,1] [7,1] [8,1] [9,1] [10,1] [11,1] [12,1] [13,1] [14,1] [15,1] [16,1] [17,1] [18,1] [19,1] [20,1] [21,1] [22,1] [23,1] [24,1] [25,1] [25,2] [25,3] [24,3] [23,3] [22,3] [21,3] [20,3] [19,3] [18,3] [17,3] [16,3] [15,3] [14,3] [13,3] [12,3] [11,3] [10,3] [9,3] [9,4] [9,5] [8,5] [7,5] [7,6] [7,7] [7,8] [7,9] [7,10] [7,11] [6,11] [5,11] [5,12] [5,13] [6,13] [7,13] [7,14] [7,15] [6,15] [5,15] [4,15] [3,15] [3,14] [3,13] [3,12] [3,11] [2,11] [1,11] [1,12] [1,13] [1,14] [1,15] [1,16] [1,17] [2,17] [3,17] [4,17] [5,17] [6,17] [7,17] [7,18] [7,19] [8,19] [9,19] [10,19] [11,19] [12,19] [13,19] [14,19] [15,19] [16,19] [17,19] [18,19] [19,19] [19,20] [19,21] [19,22] [19,23] [20,23] [21,23] [22,23] [23,23] [24,23] [25,23] [25,24] [25,25] [25,26] [25,27] [25,28] [25,29] [25,30] [25,31] [25,32] [25,33]
}