import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		//convert player square to state
		State cur = new State(maze.getPlayerSquare(), null, 0, 0);

		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();

		//push 'S' node into stack
		stack.push(cur);

		while (!stack.isEmpty()) {
			// maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			cost = cur.getDepth();
			noOfNodesExpanded += 1;
			if(cur.getDepth() > maxDepthSearched)
				maxDepthSearched = cur.getDepth();
			if(stack.size()>maxSizeOfFrontier)
				maxSizeOfFrontier = stack.size();

			// use stack.pop() to pop the stack.
			cur = stack.pop();

			// return true if find a solution and update path
			if(cur.isGoal(maze)){
				//get final update on cost and maxDepthSearched
				cost = cur.getDepth();
				if(cur.getDepth() > maxDepthSearched)
					maxDepthSearched = cur.getDepth();

				//Update path in maze
				for(int i = 0; i < cost - 1;i++){
					maze.setOneSquare(cur.getParent().getSquare(), '.');
					cur = cur.getParent();
				}
				return true;
			}

			//mark node as explored in map
			explored[cur.getX()][cur.getY()] = true;

			// use stack.push(...) to elements to stack
			ArrayList<State> successorList = new ArrayList<State>();
			successorList = cur.getSuccessors(explored, maze);
			for(int i = successorList.size() - 1; i >= 0; i--){
				int XCoor = successorList.get(i).getX();
				int YCoor = successorList.get(i).getY();
				if(explored[XCoor][YCoor] == true){
					//CYCLE CHECKING
					State tmp = cur;
					boolean cycle = false;
					do{
						if(XCoor == tmp.getSquare().X 
								&& YCoor == tmp.getSquare().Y){
							cycle = true;
						}
						tmp = tmp.getParent();

					}
					while(tmp != null);
					tmp = cur;
					if(cycle){
					}else{
						stack.push(successorList.get(i));
					}
				}else{

					stack.push(successorList.get(i));
				}
			}
		}

		//return false if no solution
		return false;
	}
}
