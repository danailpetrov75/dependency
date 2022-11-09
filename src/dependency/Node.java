package dependency;

/**
 * @author Danail Petrov
 * 
 *         Represents a 'class' which dependencies we are trying to find out.
 *         Representation using separate class is chosen in order to keep
 *         dependency tracking logic independent from the Node 'class'
 *         realization.
 * 
 */
public class Node implements Comparable<Node> {

	String name;

	Node(String name) {

		this.name = name;
	}

	String getName() {

		return name;
	}

	@Override
	public String toString() {

		return name;
	}

	public boolean equals(Node otherNode) {

		return (this.name.equals(otherNode.name));

	}

	@Override
	public int compareTo(Node o) {

		return this.name.compareTo(o.name);
	}
}
