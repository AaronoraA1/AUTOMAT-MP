package main;

import java.util.ArrayList;

public class SearchNode {

    private SearchNode parent;
    private ArrayList<SearchNode> children;
    private int depth;
    private State state;

    public String move; // The move (transition) that creates the current

    public SearchNode(State s){
        parent = null;
        children = new ArrayList<SearchNode>();
        state = s;
        depth = 0;
    }

    public boolean isAncestor()
    {
        SearchNode currNode = parent;
        while (currNode != null) {
            if (state.compareElements(currNode.state))
                return true;

            currNode = currNode.parent;
        }
        return false;
    }

    public SearchNode getParent() {
        return parent;
    }

    public void setParent(SearchNode parent) {
        this.parent = parent;
    }

    public ArrayList<SearchNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<SearchNode> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
