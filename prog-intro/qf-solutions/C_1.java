import java.io.*;
import java.util.*;

public class C {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        int h = in.nextInt();
        List<IntPair>[][][] graph = new List[h + 1][w + 1][2];
        for (int i = 0; i <= h; i++) {
            for (int j = 0; j <= w; j++) {
                for (int k = 0; k < 2; k++) {
                    graph[i][j][k] = new ArrayList<>();
                }
            }
        }
        for (int i = 0; i < h; i++) {
            String line = in.next();
            for (int j = 0; j < w; j++) {
                if (line.charAt(j) == 'X') {
                    add(i, j, i + 1, j + 1, 1, graph);
                    add(i, j + 1, i + 1, j, 1, graph);
                    add(i + 1, j, i, j, 0, graph);
                    add(i + 1, j + 1, i, j + 1, 0, graph);
                }
            }
        }
        List<IntPair> path = new ArrayList<IntPair>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (graph[i][j][0].size() != 0) {
                    findPath(new IntPair(i, j), 0, path, graph);
                }
            }
        }
        path.remove(path.size() - 1);
        System.out.println(path.size() - 1);
        for (IntPair u : path) {
            System.out.println(u.s + " " + u.f);
        }
    }

    public static void add(int ux, int uy, int vx, int vy, int side, List<IntPair>[][][] graph) {
        graph[ux][uy][side].add(new IntPair(vx, vy));
        graph[vx][vy][side].add(new IntPair(ux, uy));
    }

    public static void findPath(IntPair u, int side, List<IntPair> path, List<IntPair>[][][] graph) {
        List<IntPair> graphU = graph[u.f][u.s][side];
        while (graph[u.f][u.s][side].size() != 0) {
            IntPair v = graphU.get(graphU.size() - 1);
            graphU.remove(graphU.size() - 1);
            graph[v.f][v.s][side].remove(u);
            findPath(v, 1 - side, path, graph);
        }
        path.add(u);
    }
}

class IntPair {
    int f;
    int s;

    public IntPair(int first, int second) {
        f = first;
        s = second;
    }

    @Override
    public boolean equals(final Object o) {
        final IntPair pair = (IntPair) o;
        return f == pair.f && s == pair.s;
    }
}
