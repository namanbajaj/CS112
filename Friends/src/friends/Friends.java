package friends;

import java.util.ArrayList;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		ArrayList<String> shortest = new ArrayList<String>();

		if(g == null || p1 == null || p2 == null || g.map.get(p1) == null || g.map.get(p2) == null)
			return null;

		if(p1.equals(p2)){
			shortest.add(p1);
			return shortest;
		}

		int size = g.members.length;

		Queue<Integer> q = new Queue<Integer>();
		int[] dist = new int[size], pred = new int[size];
		boolean[] visited = new boolean[size];

		for(int i = 0; i < size; i++){
			dist[i] = Integer.MAX_VALUE;
			pred[i] = -1;
		}

		int start = g.map.get(p1);
		visited[start] = true;
		dist[start] = 0;
		q.enqueue(start);

		while(!q.isEmpty()){
			int i = q.dequeue();
			Person p = g.members[i];

			for(Friend f = p.first; f != null; f = f.next){
				int fnum = f.fnum;

				if(!visited[fnum]){
					dist[fnum] = dist[i] + 1;
					pred[fnum] = i;
					visited[fnum] = true;
					q.enqueue(fnum);
				}
			}
		}

		Stack<String> path = new Stack<String>();
		int spot = g.map.get(p2);

		if(!visited[spot])
			return null;

		while(spot != -1){
			path.push(g.members[spot].name);
			spot = pred[spot];
		}

		while(!path.isEmpty())
			shortest.add(path.pop());

		return shortest;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		ArrayList<ArrayList<String>> cliques = new ArrayList<ArrayList<String>>();

		if(g == null || school == null)
			return null;

		boolean[] visited = new boolean[g.members.length];

		for(Person p : g.members){
			if(!visited[g.map.get(p.name)] && p.school != null && p.school.equals(school)){
				Queue<Integer> q = new Queue<Integer>();
				ArrayList<String> clique = new ArrayList<>();

				int start = g.map.get(p.name);
				visited[start] = true;

				q.enqueue(start);
				clique.add(p.name);

				while(!q.isEmpty()){
					int i = q.dequeue();
					Person person = g.members[i];

					for(Friend f = person.first; f != null; f = f.next){
						int fnum = f.fnum;
						Person friend = g.members[fnum];

						if(!visited[fnum] && friend.school != null && friend.school.equals(school)){
							visited[fnum] = true;
							q.enqueue(fnum);
							clique.add(g.members[fnum].name);
						}
					}
				}

				cliques.add(clique);
			}
		}
		return cliques;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		ArrayList<String> connectors = new ArrayList<String>();

		int size = g.members.length;
		boolean[] visited = new boolean[size];
		int[] dfsnum = new int[size], back = new int[size];

		for(Person p : g.members){
			if(!visited[g.map.get(p.name)]){
				dfsnum = new int[g.members.length];
				dfs(g.map.get(p.name), g.map.get(p.name), g, visited, dfsnum, back, connectors);
			}
		}

		for(int i = 0; i < connectors.size(); i++){
			Friend ptr = g.members[g.map.get(connectors.get(i))].first;

			int count = 0;
			while(ptr != null){
				ptr = ptr.next;
				count++;
			}

			if(count == 0 || count == 1)
				connectors.remove(i);
		}

		for(Person p : g.members){
			if(p.first.next == null && !connectors.contains(g.members[p.first.fnum].name))
				connectors.add(g.members[p.first.fnum].name);
		}


		return connectors;
	}

	private static void dfs(int v, int start, Graph g, boolean[] visited, int[] dfsnum, int[] back, ArrayList<String> connectors){
		Person p = g.members[v];
		visited[g.map.get(p.name)] = true;

		int count = 1;
		for(int i = 0; i < dfsnum.length; i++)
			if(dfsnum[i] != 0)
				count++;

		if(dfsnum[v] == 0 && back[v] == 0){
			dfsnum[v] = count;
			back[v] = dfsnum[v];
		}

		for(Friend ptr = p.first; ptr != null; ptr = ptr.next){
			if(!visited[ptr.fnum]){
				dfs(ptr.fnum, start, g, visited, dfsnum, back, connectors);

				if(dfsnum[v] > back[ptr.fnum])
					back[v] = Math.min(back[v], back[ptr.fnum]);

				else{
					if(Math.abs(dfsnum[v] - back[ptr.fnum]) < 1 && Math.abs(dfsnum[v] - dfsnum[ptr.fnum]) <= 1 && back[ptr.fnum] == 1 && v == start)
						continue;

					if(dfsnum[v] <= back[ptr.fnum] && (v != start || back[ptr.fnum] == 1))
						if(!connectors.contains(g.members[v].name))
							connectors.add(g.members[v].name);
				}
			}

			else
				back[v] = Math.min(back[v], dfsnum[ptr.fnum]);
		}
		return;
	}
}

