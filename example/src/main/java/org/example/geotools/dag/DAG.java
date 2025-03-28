package org.example.geotools.dag;

/**
 * @Description:
 * @Author: 张黎 zliprogram@163.com 15972037154
 * @CreateDate: 2024/6/13 11:34
 * @UpdateUser:
 * @UpdateDate: 2024/6/13 11:34
 * @UpdateRemark:
 * @Version: 1.0
 * Copyright (c) 2024,南方数码
 * All rights reserved.
 */
import java.util.*;

class DAG {
    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
    private Map<Integer, Integer> inDegreeMap = new HashMap<>();

    public void addNode(int node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
        inDegreeMap.put(node, 0);
    }

    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
        inDegreeMap.put(to, inDegreeMap.getOrDefault(to, 0) + 1);
    }

    public boolean isDAG() {
        Queue<Integer> queue = new LinkedList<>();
        for (Integer node : inDegreeMap.keySet()) {
            if (inDegreeMap.get(node) == 0) {
                queue.add(node);
            }
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> recStack = new HashSet<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (recStack.contains(current)) {
                return false; // 检测到循环，不是DAG
            }
            if (visited.add(current)) {
                recStack.add(current);
                for (int neighbor : adjacencyList.get(current)) {
                    inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                    if (inDegreeMap.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
                recStack.remove(current);
            }
        }
        return visited.size() == inDegreeMap.size();
    }

    public List<Integer> topologicalSort() {
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> topologicalOrder = new ArrayList<>();

        for (Integer node : inDegreeMap.keySet()) {
            if (inDegreeMap.get(node) == 0) {
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            topologicalOrder.add(current);
            for (int neighbor : adjacencyList.get(current)) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (topologicalOrder.size() != inDegreeMap.size()) {
            throw new IllegalStateException("Graph has at least one cycle, not a DAG.");
        }

        return topologicalOrder;
    }

    public static void main(String[] args) {
        DAG dag = new DAG();
        dag.addNode(1);
        dag.addNode(2);
        dag.addNode(3);
        dag.addNode(4);

        dag.addEdge(1, 2);
        dag.addEdge(2, 3);
//        dag.addEdge(3, 4);
        dag.addEdge(4, 2); // 这将创建一个循环，不是DAG

        if (dag.isDAG()) {
            System.out.println("The graph is a DAG.");
            List<Integer> topologicalOrder = dag.topologicalSort();
            System.out.println("Topological sort: " + topologicalOrder);
        } else {
            System.out.println("The graph is not a DAG.");
        }
    }
}