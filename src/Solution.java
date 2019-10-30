// my email: satya@nextdoor.com

import java.util.*;
import org.junit.Assert;


/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

// You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:

// 0 represents the obstacle can't be reached.
// 1 represents the ground can be walked through.
// The place with number bigger than 1 represents a tree can be walked through, and this positive number represents the tree's height.


// You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree with lowest height first. And after cutting, the original place has the tree will become a grass (value 1).

// You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees. If you can't cut off all the trees, output -1 in that situation.

// You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off

// Example 1:

// Input: 
// [
//  [1,2,3],
//  [0,0,4],
//  [7,6,5]
// ]
// Output: 6


// Example 2:

// Input: 
// [
//  [1,2,3],
//  [0,0,0],
//  [7,6,5]
// ]
// Output: -1


// Example 3:

// Input: 
// [
//  [2,3,4],
//  [0,0,5],
//  [8,7,6]
// ]
// Output: 6
// Explanation: You started from the point (0,0) and you can cut off the tree in (0,0) directly without walking.


// Hint: size of the given matrix will not exceed 50x50.



class Solution {

    private static int cutTrees(int[][] map) {
        TreeSet<Integer> trees = new TreeSet<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] > 1) {
                    trees.add(map[i][j]);
                }
            }
        }

        // [1, 2, 3] -> tree of length 3 at index 1,2
        LinkedList<int[]> visits = new LinkedList<>();
        visits.add(new int[] {0, 0, map[0][0], 0});
        HashSet<String> visited = new HashSet<>();
        int steps = 0;

        while (visits.size() > 0 && trees.size() > 0) {
            int[] node = visits.poll();
            if (node[3] > 4) {
                throw new RuntimeException();
            }

            String index = node[0] + " " + node[1];
            if (visited.contains(index)) {
                continue;
            }

            if (node[2] == trees.iterator().next()) {
                trees.remove(node[2]);
                map[node[0]][node[1]] = 1;
                visited.clear();
                visits.clear();
                steps += node[3];
                node[3] = 0;
            }

            visited.add(index);

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (node[0] + i >= 0 && node[0] + i < map.length
                            && node[1] + j >= 0 && node[1] + j < map[node[0] + i].length
                            && (i != 0 || j != 0)
                            && map[node[0] + i][node[1] + j] > 0) {
                        visits.add(new int[] {node[0] + i, node[1] + j, map[node[0] + i][node[1] + j], node[3] + 1});
                    }
                }
            }
        }

        if (trees.size() > 0) {
            return -1;
        }

        return steps;
    }

    public static void main(String[] args) {
        Assert.assertEquals(-1, cutTrees(new int[][] {
                new int[] {2, 3, 4},
                new int[] {0, 0, 0},
                new int[] {8, 7, 6}
        }));

        Assert.assertEquals(6, cutTrees(new int[][] {
                new int[] {1, 2, 3},
                new int[] {0, 0, 4},
                new int[] {7, 6, 5}
        }));

        // 10 steps if diagonal steps are allowed, 12 - if not
        Assert.assertEquals(10, cutTrees(new int[][] {
                new int[] {4, 3, 2},
                new int[] {0, 0, 5},
                new int[] {6, 7, 8}
        }));
    }
}



// class Solution {
//     static int[][] dir = {{0,1}, {0, -1}, {1, 0}, {-1, 0}};

//     public int cutOffTree(List<List<Integer>> forest) {
//         if (forest == null || forest.size() == 0) return 0;
//         int m = forest.size(), n = forest.get(0).size();

//         PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

//         for (int i = 0; i < m; i++) {
//             for (int j = 0; j < n; j++) {
//                 if (forest.get(i).get(j) > 1) {
//                     pq.add(new int[] {i, j, forest.get(i).get(j)});
//                 }
//             }
//         }

//         int[] start = new int[2];
//         int sum = 0;
//         while (!pq.isEmpty()) {
//             int[] tree = pq.poll();
//             int step = minStep(forest, start, tree, m, n);

//             if (step < 0) return -1;
//             sum += step;

//             start[0] = tree[0];
//             start[1] = tree[1];
//         }

//         return sum;
//     }

//     private int minStep(List<List<Integer>> forest, int[] start, int[] tree, int m, int n) {
//         int step = 0;
//         boolean[][] visited = new boolean[m][n];
//         Queue<int[]> queue = new LinkedList<>();
//         queue.add(start);
//         visited[start[0]][start[1]] = true;

//         while (!queue.isEmpty()) {
//             int size = queue.size();
//             for (int i = 0; i < size; i++) {
//                 int[] curr = queue.poll();
//                 if (curr[0] == tree[0] && curr[1] == tree[1]) return step;

//                 for (int[] d : dir) {
//                     int nr = curr[0] + d[0];
//                     int nc = curr[1] + d[1];
//                     if (nr < 0 || nr >= m || nc < 0 || nc >= n 
//                         || forest.get(nr).get(nc) == 0 || visited[nr][nc]) continue;
//                     queue.add(new int[] {nr, nc});
//                     visited[nr][nc] = true;
//                 }
//             }
//             step++;
//         }

//         return -1;
//     }
// }