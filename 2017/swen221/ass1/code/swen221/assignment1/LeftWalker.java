package swen221.assignment1;

import maze.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * An implementation of the left walker, which you need to complete according to
 * the specification given in the assignment handout.
 * 
 */
public class LeftWalker extends Walker {
	private Direction direction = Direction.NORTH;
	private Direction leftDirection = Direction.WEST;
	private Direction rightDirection = Direction.EAST;
	private Direction backDirection = Direction.SOUTH;
	private Map<Point,List<Direction>> points = new HashMap<>();
	private Point location = new Point(0,0);
	private boolean foundWall = false;

	public LeftWalker() {
		super("Left Walker");
	}

	@Override
	protected Direction move(View v) {
		// TODO: you need to implement this method according to the
		// specification given for the left walker!!

		// Use the pause() command to slow the simulation down so you can see
		// what's happening...
		pause(100);

		if(!points.containsKey(location)){
			points.put(location, new ArrayList<>());
		}

		if(v.mayMove(leftDirection) && v.mayMove(rightDirection) && v.mayMove(backDirection) && v.mayMove(direction) && !foundWall){
			movePoint(direction);
			return direction;
		}else if (foundWall) {
			return moveWalker(v);
		}else {
			while (v.mayMove(leftDirection)) {
				turn("right");
			}
			foundWall = true;
			moveWalker(v);
		}
		return direction;
	}

	/**
	 * Decides which direction the walker should move in.
	 *
	 * @param v
	 *
	 * @return Direction
	 */
	private Direction moveWalker(View v) {
		if (v.mayMove(leftDirection) && !points.get(location).contains(leftDirection)) {
			turn("left");
			movePoint(direction);
			return direction;
		} else if (v.mayMove(direction) && !points.get(location).contains(direction)) {
			if(points.get(location).contains(leftDirection)){
				foundWall = false;
			}
			movePoint(direction);
			return direction;
		} else if (v.mayMove(rightDirection) && !points.get(location).contains(rightDirection)) {
			if(points.get(location).contains(direction)){
				foundWall = false;
			}
			turn("right");
			movePoint(direction);
			return direction;
		} else if (v.mayMove(backDirection) && !points.get(location).contains(backDirection)) {
			if(points.get(location).contains(rightDirection)){
				foundWall = false;
			}
			turn("back");
			movePoint(direction);
			return direction;
		}else if(v.mayMove(leftDirection)){
			foundWall = false;
			turn("left");
			movePoint(direction);
			return direction;
		}else if(v.mayMove(direction)){
			foundWall = false;
			movePoint(direction);
			return direction;
		}else if(v.mayMove(rightDirection)){
			foundWall = false;
			turn("right");
			movePoint(direction);
			return direction;
		}else if(v.mayMove(backDirection)){
			foundWall = false;
			turn("back");
			movePoint(direction);
			return direction;
		}
	return direction;
	}

	/**
	 * moves the location point to fit with a change in the walkers position.
	 *
	 * @param direction
	 */
	private void movePoint(Direction direction){
		if(!points.get(location).contains(direction)){
			points.get(location).add(direction);
		}

		if(direction==Direction.NORTH){
			location = new Point((int) location.getX(), (int) location.getY()+1);
		}else if(direction==Direction.SOUTH){
			location = new Point((int) location.getX(), (int) location.getY()-1);
		}else if(direction==Direction.EAST){
			location = new Point((int) location.getX()+1, (int) location.getY());
		}else if(direction==Direction.WEST){
			location = new Point((int) location.getX()-1, (int) location.getY());
		}
	}

	/**
	 * Turns the walker in the given direction.
	 *
	 * @param direction
	 */
	private void turn(String direction){
		if(direction.equals("left")){
			Direction oldDirection = this.direction;
			this.direction = leftDirection;
			leftDirection = backDirection;
			backDirection = rightDirection;
			rightDirection = oldDirection;
		}else if(direction.equals("right")){
			Direction oldDirection = this.direction;
			this.direction = rightDirection;
			rightDirection = backDirection;
			backDirection = leftDirection;
			leftDirection = oldDirection;
		}else if(direction.equals("back")){
			Direction oldDirection = this.direction;
			this.direction = backDirection;
			backDirection = oldDirection;
			oldDirection = rightDirection;
			rightDirection = leftDirection;
			leftDirection = oldDirection;

		}
	}

	/**
	 * This simply returns a randomly chosen (valid) direction which the walker
	 * can moveWalker in.
	 * 
	 * @param v
	 *
	 * @return
	 */
	private Direction getRandomDirection(View v) {
		// The random walker first decides what directions it can moveWalker in. The
		// walker cannot moveWalker in a direction which is blocked by a wall.
		List<Direction> possibleDirections = determinePossibleDirections(v);

		// Second, the walker chooses a random direction from the list of
		// possible directions
		return selectRandomDirection(possibleDirections);
	}

	/**
	 * Determine the list of possible directions. That is, the directions which
	 * are not blocked by a wall.
	 * 
	 * @param v
	 *            The View object, with which we can determine which directions
	 *            are possible.
	 * @return
	 */
	private List<Direction> determinePossibleDirections(View v) {
		Direction[] allDirections = Direction.values();
		ArrayList<Direction> possibleDirections = new ArrayList<Direction>();

		for (Direction d : allDirections) {
			if (v.mayMove(d)) {
				// Yes, this is a valid direction
				possibleDirections.add(d);
			}
		}

		return possibleDirections;
	}

	/**
	 * Select a random direction from a list of possible directions.
	 * 
	 * @param possibleDirections
	 * @return
	 */
	private Direction selectRandomDirection(List<Direction> possibleDirections) {
		int random = (int) (Math.random() * possibleDirections.size());
		return possibleDirections.get(random);
	}
}