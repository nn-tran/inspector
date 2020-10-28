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
		System.out.println(tab + "<Inspecting " + c.getName() + ">");
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
			System.out.println(tab + " Superclass: " + cSuper.getName());
			//System.out.println(tab + " Inspecting superclass");
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
		System.out.print(tab + " Interface(s):");
		if (interfaces.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Class i : interfaces) {
				System.out.println(tab + "  " + i.getName());
				//System.out.println(tab + "  Inspecting interface");
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
			System.out.println(tab + " Object is array");
			System.out.println(tab + " Type: " + c.getComponentType());
			System.out.println(tab + " Length: " + len);
			System.out.println(tab + " Values:");
			for (int i = 0; i < len; ++i) {
				Object value = Array.get(obj, i);
				System.out.println(tab + "  " + value);
				
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
		Constructor[] constructors = c.getDeclaredConstructors();
		System.out.print(tab + " Constructor(s):");
		if (constructors.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Constructor i : constructors) {
				Class[] cExceptions = i.getExceptionTypes();
				Class[] cParams = i.getParameterTypes();
				int cMod = i.getModifiers();
				System.out.println(tab + "  Name: " + i.getName());
				System.out.print(tab + "   Exception(s):");
				if (cExceptions.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : cExceptions) {
						System.out.println(tab + "   " + j.getName());
					}
				}
				System.out.print(tab + "   Parameter(s):");
				if (cParams.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : cParams) {
						System.out.println(tab + "   " + j.getName());
					}
				}
				System.out.println(tab + "   Modifier(s): "+ Modifier.toString(cMod));

			}
		}
		
	}
	
	/**
	 * finds and prints method details
	 * @param c		class of object
	 * @param obj	object inspected
	 * @param tab	the indentation for printing
	 */
	private void methodHandler(Class c, Object obj, String tab) {
		Method[] methods = c.getDeclaredMethods();
		System.out.print(tab + " Method(s):");
		if (methods.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Method i : methods) {
				Class[] mExceptions = i.getExceptionTypes();
				Class[] mParams = i.getParameterTypes();
				Class mRet = i.getReturnType();
				int mMod = i.getModifiers();
				System.out.println(tab + "  Name: " + i.getName());
				System.out.print(tab + "   Exception(s):");
				if (mExceptions.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : mExceptions) {
						System.out.println(tab + "    " + j.getName());
					}
				}
				System.out.print(tab + "   Parameter(s):");
				if (mParams.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : mParams) {
						System.out.println(tab + "    " + j.getName());
					}
				}
				System.out.println(tab + "   Return type: " + mRet.getName());
				System.out.println(tab + "   Modifier(s): "+ Modifier.toString(mMod));

			}
		}
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
		System.out.print(tab + " Field(s):");
		if (fields.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Field k : fields) {
				System.out.println(tab + "  Name: "+ k.getName());
				System.out.println(tab + "   Type: "+ k.getType());
				System.out.println(tab + "   Modifier(s): "+ Modifier.toString(k.getModifiers()));
				try {
					Object value = k.get(obj);
					if (recursive && value != null && !k.getType().isPrimitive()) {
						System.out.println(tab + "   Value: Field is class");
						System.out.println(tab + "   Inspecting field");
						inspectClass(value.getClass(), value, recursive, depth+1);
					} else {
						System.out.println(tab + "   Value:" + value);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					System.out.println(tab + "   Value: not visible (private field)");
					//e.printStackTrace();
				}
			}
		}
	}

	/**
	 * make indentation based on depth
	 * @param depth current recursion depth
	 * @return the indent string
	 */
	String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; ++i) {
			res += "\t";
		}
		return res;
	}
	
}