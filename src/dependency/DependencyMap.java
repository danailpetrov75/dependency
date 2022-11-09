package dependency;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Danail Petrov
 * 
 *         Represents a line of dependencies for a particular 'Node' item.
 *         TreeMap and TreeSet will keep the matching items sorted and will not
 *         allow any duplicate elements.
 *          
 */
@SuppressWarnings("serial")
public class DependencyMap extends TreeMap<Node, TreeSet<Node>> {

	public DependencyMap() {

		super();
	}

	public void addDependency(String line) {

		String[] lineSplited = (line.trim()).split(" ");

		if (lineSplited.length < 2) {

			throw new IllegalArgumentException("Error in the following input line: \n" + "'" + line + "'"
					+ " \nAt least 2 items per each line item must be specified separated by space.");

		} else {

			Node keyNode = new Node(lineSplited[0]);

			String[] dependencyNodeNames = Arrays.copyOfRange(lineSplited, 1, lineSplited.length);
			Node[] dependencyNode = new Node[dependencyNodeNames.length];

			for (int i = 0; i < dependencyNodeNames.length; i++) {

				dependencyNode[i] = new Node(dependencyNodeNames[i]);
			}

			addDependency(keyNode, dependencyNode);
		}
	}

	public void addDependency(Node key, Node[] value) {

		this.put(key, new TreeSet<Node>(Arrays.asList(value)));
	}

	public void addDependency(Node key, Node newValue) {

		if (!newValue.equals(key)) {
			if (this.containsKey(key)) {

				TreeSet<Node> dependencySet = this.get(key);
				dependencySet.add(newValue);
				this.put(key, dependencySet);

			} else {

				this.put(key, new TreeSet<Node>(Arrays.asList(new Node[] { newValue })));
			}
		}
	}

	public TreeSet<Node> getDependency(Node key) {

		return this.get(key);
	}

	public String toString() {

		StringBuffer stringBuffer = new StringBuffer();

		for (Map.Entry<Node, TreeSet<Node>> entry : this.entrySet()) {

			stringBuffer.append(entry.getKey() + " " + entry.getValue());
			stringBuffer.append(System.getProperty("line.separator"));
		}

		return stringBuffer.toString();
	}
}
