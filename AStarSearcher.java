import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		
		// implement frontier list
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// initialize the root state and add
		// to frontier list
		State rootState = new State(maze.getPlayerSquare(), null, 0, 0);
		double rootFValue = Math.abs(maze.getPlayerSquare().X - maze.getGoalSquare().X) + Math.abs(maze.getPlayerSquare().Y - maze.getGoalSquare().Y);
		StateFValuePair cur = new StateFValuePair(rootState, rootFValue);
		frontier.add(cur);

		while (!frontier.isEmpty()) {
			// return true if a solution has been found
			if(cur.getState().isGoal(maze)){
				// update final cost and maxDepthSearched
				cost = (int) cur.getFValue();
				if(cur.getState().getDepth() > maxDepthSearched){
					maxDepthSearched = cur.getState().getDepth();
				}
				// update path of maze
				rootState = cur.getState();
				while(rootState.getParent().getSquare() != maze.getPlayerSquare()){
					maze.setOneSquare(rootState.getParent().getSquare(), '.');
					rootState = rootState.getParent();
				}
				return true;
			}
			// maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			noOfNodesExpanded++;
			cost = (int) cur.getFValue();
			if(cur.getState().getDepth() > maxDepthSearched){
				maxDepthSearched = cur.getState().getDepth();
			}
			if(frontier.size() > maxSizeOfFrontier){
				maxSizeOfFrontier = frontier.size();
			}
			
			// use frontier.poll() to extract the minimum stateFValuePair.
			cur = frontier.poll();

			// mark as explored
			explored[cur.getState().getX()][cur.getState().getY()] = true;
			
			// Get successor list for
			// current node
			ArrayList<State> successorList = new ArrayList<State>();
			successorList = cur.getState().getSuccessors(explored, maze);
			
			// use frontier.add(...) to add stateFValue pairs
			for(int i = successorList.size() - 1; i >= 0; i--){
				int GValue = successorList.get(i).getGValue();
				int XCoord = successorList.get(i).getX();
				int YCoord = successorList.get(i).getY();
				double nodeFValue = GValue + Math.abs(XCoord - maze.getGoalSquare().X) + Math.abs(YCoord - maze.getGoalSquare().Y);
				StateFValuePair addFValueNode = new StateFValuePair(successorList.get(i),nodeFValue);
				
				// if node to be added to frontier
				// has already been encounter
				if(explored[XCoord][YCoord] == true){
					// iterate thru frontier list
					// and compare g(n) w/ g(m)
					Iterator<StateFValuePair> iter = frontier.iterator();
					while(iter.hasNext()){
						StateFValuePair Current = iter.next();
						if(Current.getState().getX() == XCoord
								&& Current.getState().getY() == YCoord){
							// if g(n) < g(m), keep node n
							// and throw away node m to frontier
							if(successorList.get(i).getGValue() < Current.getState().getGValue()){
								iter.remove();
								frontier.add(addFValueNode);
								break;
							}
						}
					}
				}else{
					// if never encountered node n
					// add to frontier
					frontier.add(addFValueNode);
				}
			}
		}
		// return false if no solution
		return false;
	}

}
