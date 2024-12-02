import java.io.*;
import java.nio.file.*;
import java.util.*;

import javax.swing.GroupLayout.Group;

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
    
    static List<int[]> solveDFS() {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        
        // TODO: Implement DFS
        // Helper methods available:
        // - isValid(row, col): check if position is valid
        // - markVisited(row, col): mark position and print current maze path
        // Return the path as list of int[2] arrays containing [row, col]
        
        return path;
    }
    
    static List<int[]> solveBFS() {
        List<int[]> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        
        // TODO: Implement BFS
        // Helper methods available:
        // - isValid(row, col): check if position is valid
        // - markVisited(row, col): mark position and print current maze path
        // Return the path as list of int[2] arrays containing [row, col]
        
        return path;
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

    // Group Members:
        
    // Answers:
    //     What is the path for each?
    //     Small:
    //         DFS:
    //         BFS:
                
    //     Medium:
    //         DFS:
    //         BFS:
                
    //     Large:
    //         DFS:
    //         BFS:
}