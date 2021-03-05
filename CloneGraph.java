import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Class for graph nodes.
 */
class Node {

    // **** members ****
    public int val;
    public List<Node> neighbors;

    // **** constructors ****
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }

    @Override
    public String toString() {
        return "" + this.val + "(" + this.neighbors.size() + ")";
    }
}


/**
 * LeetCode 133. Clone Graph
 * https://leetcode.com/problems/clone-graph/
 */
public class CloneGraph {


    /**
     * Populate initial graph.
     * 
     * !!!! NOT PART OF SOLUTION !!!!
     */
    static Node populateGraph(List<Integer[]> al) {

        // **** sanity checks ****
        if (al == null) 
            return null;

        if (al.size() == 0)
            return new Node(1);

        // **** initialization ****
        HashMap<Integer,Node> hm = new HashMap<>();

        // **** get root neighbors ****
        Integer[] arr = al.get(0);

        // **** create and populate node neighbors ****
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (int j = 0; j < arr.length; j++) {
            Node n = new Node(arr[j]);
            neighbors.add(n);
            hm.put(arr[j], n);
        }

        // **** create root node ****
        Node graph = new Node(1, neighbors);

        // **** save node reference in map ****
        hm.put(graph.val, graph);

        // **** traverse the array list creating and connecting nodes for the graph ****
        for (int i = 1; i < al.size(); i++) {

            // **** get values for node neighbors ****
            arr = al.get(i);

            // **** check if the current node is in the graph ****
            Node node = hm.get(i + 1);

            // **** connect neighbors to this node ****
            for (int j = 0; j < arr.length; j++) {

                // **** look for node in the hash map ****
                Node n = hm.get(arr[j]);
                if (n == null) {
                    n = new Node(arr[j]);
                    hm.put(arr[j], n);
                } else {
                    if (!n.neighbors.contains(node))
                        n.neighbors.add(node);
                }

            }

            // **** add neighbors to this node ****
            for (int j = 0; j < arr.length; j++) {

                // **** ****
                Node n = hm.get(arr[j]);
                
                // **** ****
                if (!node.neighbors.contains(n))
                    node.neighbors.add(n);
            }
        }

        // **** return graph ****
        return graph;
    }


    /**
     * Display graph.
     * 
     * !!!! NOT PART OF SOLUTION !!!
     */
    static void displayGraph(Node graph,  ArrayList<Integer[]> al) {

        // **** sanity checks ****
        if (graph == null)
            return;

        // **** initialization ****
        Node p = graph;
        Node q = null;

        // **** ****
        for (int i = 0; i < al.size(); i++) {

            // **** display neighbors for this node ****
            for (int j = 0; j < p.neighbors.size(); j++) {
                System.out.print(p.neighbors.get(j).val + " ");
                if (p.neighbors.get(j).val == (i + 2))
                    q = p.neighbors.get(j);
            }

            // **** move to next node in the graph ****
            p = q;
        }

        // **** ****
        System.out.println();
    }

    
    /**
     * Recursive DFS method.
     */
    static private Node cloneGraph(Node node, HashMap<Integer,Node> hm) {

        // **** check if node is in hm (no need to visit this node) ****
        if (hm.containsKey(node.val))
            return hm.get(node.val);

        // **** clone this node  ****
        Node clone = new Node(node.val);

        // **** put cloned node in hm ****
        hm.put(clone.val, clone);

        // **** recursive call ****
        node.neighbors.forEach( n -> clone.neighbors.add(cloneGraph(n, hm)));

        // **** return cloned node (copy of graph) ****
        return clone;
    }


    /**
     * Return a deep copy (clone) of the graph.
     * Entry point for recursive DFS method.
     * 
     * Runtime: 25 ms, faster than 90.62% of Java online submissions.
     * Memory Usage: 39 MB, less than 87.79% of Java online submissions.
     * 
     * Time complexity: O(n) because we visit each node once.
     * Space complexity: O(n) because we make a copy of each cloned node.
     */
    static Node cloneGraph(Node node) {

        // **** sanity check(s) ****
        if (node == null)
            return null;

        // **** call recursive method (returns cloned graph) ****
        return cloneGraph(node, new HashMap<>());
    }


    /**
     * Test scaffolding
     * 
     * !!!! NOT PART OF THE SOLUTION !!!!
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** ****
        ArrayList<Integer[]> al = null;

        // **** open a buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read adjacency list ****
        String[] strArr = br.readLine().trim().split("],\\[");

        // **** close the buffered reader ****
        br.close();

        // **** get the length of the String[] ****
        int len = strArr.length;

        // ???? ????
        System.out.println("main <<< len: " + len);

        // **** ****
        if (len == 1 && strArr[0].equals("[]")) {
            al = new ArrayList<>();
        } else if (len == 1) {
            al = null;
        } else {

            // **** remove [ and ] from first and last entries ****
            strArr[0] = strArr[0].substring(1);
            strArr[len - 1] = strArr[len - 1].substring(0, len - 1);

            // ???? ????
            for (String str : strArr)
                System.out.println("main <<< str ==>" + str + "<==");

            // **** convert array of string to list of Integer[] ****
            al = new ArrayList<>();

            // **** populate the array list ****
            for (String str : strArr) {

                // **** convert String to int[] ****
                int[] tmp = Arrays.stream(str.split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();

                // **** convert int[] yp Integer[] ****
                Integer[] arr = IntStream.of(tmp)
                                        .boxed()
                                        .toArray(Integer[]::new);

                // **** add Integer[] to list ****
                al.add(arr);
            }
        }

        // ???? ????
        // if (al != null)
        //     al.forEach( x -> System.out.println("main <<< x: " + Arrays.toString(x)) );

        // **** populate initial graph ****
        Node graph = populateGraph(al);

        // ???? display initial graph ????
        if (graph == null)
            System.out.println("main <<< graph: null");
        else {
            System.out.print("main <<< graph: ");
            displayGraph(graph, al);
        }

        // **** clone the graph (method of interest) ****
        Node clone = cloneGraph(graph);

        // ???? display cloned graph ????
        if (graph == null)
            System.out.println("main <<< clone: null");
        else {
            System.out.print("main <<< clone: ");
            displayGraph(clone, al);
        }
    }
}