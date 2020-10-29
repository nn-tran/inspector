import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * CPSC 501
 * Inspector class
 *
 * @author Nguyen Tran
 */

public class Inspector {

	/**
	 * inspect object
	 * @param obj		object inspected
	 * @param recursive	recursion status
	 */
	public void inspect(Object obj, boolean recursive) {
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, 0);
	}

	/**
	 * inspect class of an object
	 * @param c			class of object
	 * @param obj		object inspected
	 * @param recursive	recursion status
	 * @param depth		current depth, used for recursion
	 */
	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		String tab = indent(depth);
		System.out.println(tab + "CLASS");
		System.out.println(tab + "class: " + c.getName());
		arrayHandler(c, obj, tab);
		superClassHandler(c, obj, recursive, depth, tab);
		interfaceHandler(c, obj, recursive, depth, tab);
		constructorHandler(c, obj, tab);
		methodHandler(c, obj, tab);
		fieldHandler(c, obj, recursive, depth, tab);
		
	}

	/**
	 * finds and prints superclass details
	 * will recursively inspect superclasses
	 * @param c			class of object
	 * @param obj		object inspected
	 * @param recursive	recursion status
	 * @param depth		current depth, used for recursion
	 * @param tab		the indentation for printing
	 */
	private void superClassHandler(Class c, Object obj, boolean recursive, int depth, String tab) {
		Class cSuper = c.getSuperclass();
		if (cSuper!=null) {
			System.out.println(tab + "SUPERCLASS -> Recursively Inspect");
			System.out.println(tab + "Superclass: " + cSuper.getName());
			inspectClass(cSuper, obj, recursive, depth+1);
		}
	}
	
	/**
	 * finds and prints interface details
	 * will recursively inspect interfaces
	 * @param c			class of object
	 * @param obj		object inspected
	 * @param recursive	recursion status
	 * @param depth		current depth, used for recursion
	 * @param tab		the indentation for printing
	 */
	private void interfaceHandler(Class c, Object obj, boolean recursive, int depth, String tab) {
		Class[] interfaces = c.getInterfaces();
		System.out.println(tab + " INTERFACES<" + c.getName() + ">");
		System.out.print(tab + " Interfaces->");
		if (interfaces.length == 0) {
			System.out.println(" NONE");
		} else {
			System.out.println();
			for (Class i : interfaces) {
				System.out.println(tab + "  " + i.getName());
				inspectClass(i, obj, recursive, depth+1);
			}
		}
	}
	
	/**
	 * finds and prints array details
	 * @param c			class of object
	 * @param obj		object inspected
	 * @param tab		the indentation for printing
	 */
	private void arrayHandler(Class c, Object obj, String tab) {
		if (c.isArray()) {
			int len = Array.getLength(obj);
			System.out.println(tab + "Component type: " + c.getComponentType());
			System.out.println(tab + "Length: " + len);
			System.out.println(tab + "Values->");
			for (int i = 0; i < len; ++i) {
				Object value = Array.get(obj, i);
				System.out.println(tab + " " + value);
				
			}
		}
	}
	
	/**
	 * finds and prints constructor details
	 * @param c		class of object
	 * @param obj	object inspected
	 * @param tab	the indentation for printing
	 */
	private void constructorHandler(Class c, Object obj, String tab) {
		System.out.println(tab + " CONSTRUCTORS<" + c.getName() + ">");
		Constructor[] constructors = c.getDeclaredConstructors();
		System.out.print(tab + " Constructors->");
		printArrayWithData(constructors, tab);
	}
	
	/**
	 * finds and prints method details
	 * @param c		class of object
	 * @param obj	object inspected
	 * @param tab	the indentation for printing
	 */
	private void methodHandler(Class c, Object obj, String tab) {
		System.out.println(tab + " METHODS<" + c.getName() + ">");
		Method[] methods = c.getDeclaredMethods();
		System.out.print(tab + " Methods->");
		printArrayWithData(methods, tab);
	}
	
	/**
	 * finds and prints field details
	 * will recursively inspect fields if recursive == true
	 * @param c			class of object
	 * @param obj		object inspected
	 * @param recursive	recursion status
	 * @param depth		current depth, used for recursion
	 * @param tab		the indentation for printing
	 */
	private void fieldHandler(Class c, Object obj, boolean recursive, int depth, String tab) {
		Field[] fields = c.getDeclaredFields();
		Field.setAccessible(fields, true);
		System.out.print(tab + " Fields->");
		if (fields.length == 0) {
			System.out.println(" NONE");
		} else {
			System.out.println();
			for (Field f : fields) {
				System.out.print(printField(f, tab));
				try {
					Object value = f.get(obj);
					System.out.println(tab + "   Value: " + value);
					if (recursive && value != null && !f.getType().isPrimitive()) {
						System.out.println(tab + "    -> Recursively Inspect");
						inspectClass(value.getClass(), value, recursive, depth+1);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * make indentation based on depth
	 * @param depth	current recursion depth
	 * @return		the indent string
	 */
	String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; ++i) {
			res += "\t";
		}
		return res;
	}
	
	/**
	 * make a printable string for a field
	 * does not handle recursion
	 * @param f		field to print
	 * @param tab	indentation
	 * @return		the printable string
	 */
	String printField(Field f, String tab) {
		String output = "";
		output += tab + "  FIELD\n";
		output += tab + "   Name: "+ f.getName() + "\n";
		output += tab + "   Type: "+ f.getType() + "\n";
		output += tab + "   Modifiers-> "+ Modifier.toString(f.getModifiers()) + "\n";
		return output;
	}
	
	/**
	 * make a printable string for a method
	 * @param m		method to print
	 * @param tab	indentation
	 * @return		the printable string
	 */
	String printMethod(Method m, String tab) {
		String output = "";
		output += tab + "  METHOD\n";
		output += tab + "   Name: " + m.getName() + "\n";
		
		output += tab + "   Exceptions->";
		output += printClassArray(m.getExceptionTypes(), tab, 3);

		output += tab + "   Parameters->";
		output += printClassArray(m.getParameterTypes(), tab, 3);

		output += tab + "   Return type: " + m.getReturnType().getName() + "\n";
		
		output += tab + "   Modifiers-> "+ Modifier.toString(m.getModifiers()) + "\n";
		return output;
	}

	/**
	 * make a printable string for a constructor
	 * @param co	constructor to print
	 * @param tab	indentation
	 * @return		the printable string
	 */
	String printConstructor(Constructor co, String tab) {
		String output = "";
		output += tab + "  CONSTRUCTOR\n";
		output += tab + "   Name: " + co.getName() + "\n";
		
		output += tab + "   Exceptions->";
		output += printClassArray(co.getExceptionTypes(), tab, 3);

		output += tab + "   Parameters->";
		output += printClassArray(co.getParameterTypes(), tab, 3);

		output += tab + "   Modifiers-> "+ Modifier.toString(co.getModifiers()) + "\n";
		return output;
	}
	
	/**
	 * make a printable string of an array of an attribute
	 * @param array		attribute array to print
	 * @param tab		indentation
	 * @param space		extra spacing for formatting
	 * @return			the printable string
	 */
	String printClassArray(Class[] array, String tab, int space) {
		String output = "";
		if (array.length == 0) {
			output+= " NONE\n";
		} else {
			output+= "\n";
			for (Class c : array) {
				output+= tab;
				for (int i = 0; i < space; ++i) {
					output += " ";
				}
				output+= c.getName() + "\n";
			}
		}
		return output;
	}
	
	/**
	 * print a complete string for methods and constructors
	 * @param array		array to print
	 * @param tab		indentation
	 */
	void printArrayWithData(Object[] array, String tab) {
		if (array.length == 0) {
			System.out.println(" NONE");
		} else {
			System.out.println();
			for (Object o : array) {
				if (o instanceof Method) {
					System.out.print(printMethod((Method) o, tab));
				}
				if (o instanceof Constructor) {
					System.out.print(printConstructor((Constructor) o, tab));
				}
				
			}
		}
	}
}