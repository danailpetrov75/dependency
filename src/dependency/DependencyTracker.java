package dependency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * @author Danail Petrov
 * 
 *         Tracks and collects all forward and inverted set of dependencies of
 *         the set of Node items. For convenience a text file input is
 *         implemented and its name must be provided as command line argument.
 * 
 */
public class DependencyTracker {

	// Contains direct dependencies for the 'node' item
	private static DependencyMap directDependency = new DependencyMap();
	// Items from which 'node' item is depending on
	private static DependencyMap transitiveDependency = new DependencyMap();
	// Items which are dependent from this particular 'node' item
	private static DependencyMap invertTransitiveDependency = new DependencyMap();

	/**
	 * Traverses call graph based on the node direct dependencies given from the
	 * input file. Set of the node descendants compose direct and transitive
	 * dependencies. Parent node is collected as invert dependency item for all its
	 * descendants.
	 * 
	 * @param item    Node item which dependencies we are looking for
	 * @param current Current node item we reach during the traverse
	 */
	public static void track(Node item, Node current) {

		transitiveDependency.addDependency(item, current);
		TreeSet<Node> currentDependency = directDependency.getDependency(current);

		if (currentDependency == null) {

			return;

		} else {

			for (Node nextNode : currentDependency) {

				invertTransitiveDependency.addDependency(nextNode, item);
				track(item, nextNode);
			}
		}
	}

	public static void main(String[] args) {

		try {

			if (args.length < 1) {

				throw new IllegalArgumentException(
						"Error! Input file is missing. \nUsage: java 'className' 'inputfile' | java -jar 'jarfile' 'inputfile'");
			}

			Scanner reader = new Scanner(new FileInputStream(args[0]));

			while (reader.hasNext()) {

				String line = reader.nextLine();
				directDependency.addDependency(line);
			}

			reader.close();

			System.out.println("Given is the following direct dependency lines: \n"
					+ DependencyTracker.directDependency.toString());

			for (Node item : DependencyTracker.directDependency.keySet()) {

				DependencyTracker.track(item, item);
			}

			System.out.println(
					"The full dependency set for each item is: \n" + DependencyTracker.transitiveDependency.toString());
			System.out.println("The full inverse dependency set for each item is: \n"
					+ DependencyTracker.invertTransitiveDependency.toString());

		} catch (FileNotFoundException e) {

			System.out.print(e.getMessage());

		} catch (IllegalArgumentException e) {

			System.out.print(e.getMessage());
		}
	}
}
