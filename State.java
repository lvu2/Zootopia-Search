import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the DFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD
		Square upSquare =  new Square(square.X - 1, square.Y);
		Square rightSquare = new Square(square.X, square.Y + 1);
		Square downSquare =  new Square(square.X + 1, square.Y);
		Square leftSquare = new Square(square.X, square.Y - 1);

		ArrayList<State> unvisitedNeighbors = new ArrayList<State>();

		//make current node into parent node for successor states
		State squareState = new State(square, parent, gValue, depth);

		//change successor squares into states
		State upState = new State(upSquare, squareState, gValue + 1, depth + 1);
		State rightState = new State(rightSquare, squareState, gValue + 1, depth + 1);
		State downState = new State(downSquare, squareState, gValue + 1, depth + 1);
		State leftState = new State(leftSquare, squareState, gValue + 1, depth + 1);

		// check all four neighbors (up, right, down, left)
		if(square.X - 1 >= 0 && maze.getSquareValue(square.X-1, square.Y) != '%'){
			unvisitedNeighbors.add(upState);
		}
		if(square.Y + 1 < maze.getNoOfCols() && maze.getSquareValue(square.X, square.Y+1) != '%'){
			unvisitedNeighbors.add(rightState);
		}
		if(square.X + 1 < maze.getNoOfRows() && maze.getSquareValue(square.X+1, square.Y) != '%'){
			unvisitedNeighbors.add(downState);
		}
		if(square.Y-1 >= 0 && maze.getSquareValue(square.X, square.Y-1) != '%'){
			unvisitedNeighbors.add(leftState);
		}
		// return all unvisited neighbors
		return unvisitedNeighbors;

		// remember that each successor's depth and gValue are
		// +1 of this object.
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the DFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
