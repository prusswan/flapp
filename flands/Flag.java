package flands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Each section may use one or more named flags, each of which is either 'clear'
 * or 'set'. Various node type may register as listeners, so that they are enabled
 * or disabled depending on the flag's state.
 * @author Jonathan Mann
 */
public class Flag {
	private static Map<String,Flag> flagMap = new HashMap<String,Flag>();
	
	public static Flag getFlag(String name) {
		Flag f = flagMap.get(name);
		if (f == null) {
			f = new Flag(name);
			flagMap.put(name, f);
		}
		return f;
	}
	private static void removeFlag(String name) {
		flagMap.remove(name);
	}
	
	public static interface Listener {
		public void flagChanged(String name, boolean state);
	}
	
	private final String name;
	private boolean state;
	private LinkedList<Listener> listeners;

	public Flag(String name) {
		this.name = name;
		this.state = false;
		listeners = new LinkedList<Listener>();
	}

	public String getName() { return name; }
	public boolean getState() { return state; }
	public void setState(boolean b) {
		if (state != b) {
			state = b;
			for (Iterator<Listener> i = listeners.iterator(); i.hasNext(); )
				i.next().flagChanged(name, b);
		}
	}

	public void addListener(Listener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}
	public void removeListener(Listener l) {
		listeners.remove(l);
		if (listeners.size() == 0) {
			System.out.println("Flag '" + name + "' removing itself");
			removeFlag(name);
		}
	}
}