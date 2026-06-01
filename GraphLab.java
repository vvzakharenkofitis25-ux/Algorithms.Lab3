import java.util.*;

public class GraphLab {
    private Map<Integer, List<Integer>> adjList;

    public GraphLab() {
        adjList = new HashMap<>();
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(1, 3);
        addEdge(1, 4);
        addEdge(2, 5);
        addEdge(2, 6);
    }

    private void addEdge(int source, int destination) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
        adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(source);
    }

    public void dfs(int startNode, Set<Integer> visited, List<Integer> order) {
        visited.add(startNode);
        order.add(startNode);
        for (int neighbor : adjList.getOrDefault(startNode, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, order);
            }
        }
    }

    public int countConnectedComponents() {
        Set<Integer> visited = new HashSet<>();
        int count = 0;
        for (int node : adjList.keySet()) {
            if (!visited.contains(node)) {
                List<Integer> order = new ArrayList<>();
                dfs(node, visited, order);
                count++;
            }
        }
        return count;
    }

    public List<Integer> bfs(int startNode) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        visited.add(startNode);
        queue.add(startNode);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);
            for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    public List<Integer> bfsShortestPath(int start, int goal) {
        Map<Integer, Integer> parent = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        parent.put(start, -1);
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            if (node == goal) {
                List<Integer> path = new ArrayList<>();
                for (int cur = goal; cur != -1; cur = parent.get(cur)) {
                    path.add(cur);
                }
                Collections.reverse(path);
                return path;
            }

            for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!parent.containsKey(neighbor)) {
                    parent.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        GraphLab lab = new GraphLab();

        System.out.println("ЗАВДАННЯ 2: DFS");
        Set<Integer> dfsVisited = new HashSet<>();
        List<Integer> dfsOrder = new ArrayList<>();
        lab.dfs(0, dfsVisited, dfsOrder);
        System.out.print("Порядок відвідування DFS: ");
        for (int i = 0; i < dfsOrder.size(); i++) {
            System.out.print(dfsOrder.get(i) + (i < dfsOrder.size() - 1 ? " > " : "\n"));
        }
        System.out.println("Кількість компонент зв'язності: " + lab.countConnectedComponents());

        System.out.println("\nЗАВДАННЯ 3: BFS");
        List<Integer> bfsOrder = lab.bfs(0);
        System.out.print("Порядок відвідування BFS: ");
        for (int i = 0; i < bfsOrder.size(); i++) {
            System.out.print(bfsOrder.get(i) + (i < bfsOrder.size() - 1 ? " -> " : "\n"));
        }

        List<Integer> path = lab.bfsShortestPath(0, 6);
        System.out.print("Найкоротший шлях від 0 до 6: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + (i < path.size() - 1 ? " > " : "\n"));
        }
        System.out.println("Довжина шляху: " + (path.size() - 1) + " ребер");
    }
}