import java.util.*;

 public class GraphLab {
    // Представлення графу у вигляді списку суміжності
    private Map<Integer, List<Integer>> adjList;

    public GraphLab() {
        adjList = new HashMap<>();
        // Ініціалізуємо граф. завдання 1
        // Ребра: 0–1, 0–2, 1–3, 1–4, 2–5, 2–6
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(1, 3);
        addEdge(1, 4);
        addEdge(2, 5);
        addEdge(2, 6);
    }

    // Метод для додавання ребра у неорієнтований граф
    private void addEdge(int source, int destination) {
        adjList.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
        adjList.computeIfAbsent(destination, k -> new ArrayList<>()).add(source);
    }

    // 2.1 Реалізація DFS
    public void dfs(int startNode, Set<Integer> visited, List<Integer> order) {
        visited.add(startNode);
        order.add(startNode);

        // Проходимо по всіх сусідах поточної вершини
        for (int neighbor : adjList.getOrDefault(startNode, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, order);
            }
        }
    }

    // 2.4 Підрахунок кількості зв'язних компонент за допомогою DFS
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

    // 3.1 Реалізація BFS
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

    // 3.4 Модифікований BFS для пошуку найкоротшого шляху
    public List<Integer> bfsShortestPath(int start, int goal) {
        Set<Integer> visited = new HashSet<>();
        Queue<List<Integer>> queue = new LinkedList<>();

        // Черга зберігає цілі шляхи
        queue.add(Collections.singletonList(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll();
            int node = path.get(path.size() - 1);

            if (node == goal) {
                return path;
            }

            for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        GraphLab lab = new GraphLab();

        System.out.println("ЗАВДАННЯ 2: DFS ");
        Set<Integer> dfsVisited = new HashSet<>();
        List<Integer> dfsOrder = new ArrayList<>();
        lab.dfs(0, dfsVisited, dfsOrder);


        System.out.print("Порядок відвідування DFS: ");
        for (int i = 0; i < dfsOrder.size(); i++) {
            System.out.print(dfsOrder.get(i) + (i < dfsOrder.size() - 1 ? " > " : "\n"));
        }
        System.out.println("Кількість компонент зв'язності: " + lab.countConnectedComponents());

        System.out.println("\n ЗАВДАННЯ 3: BFS ");
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