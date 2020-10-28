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

	public void inspect(Object obj, boolean recursive) {
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, 0);
	}

	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		String tab = indent(depth);
		System.out.println(tab + "<Inspecting " + c.getName() + ">");
				
		Class cSuper = c.getSuperclass();
		if (cSuper!=null) {
			System.out.println(tab + " Superclass: " + cSuper.getName());
			//System.out.println(tab + " Inspecting superclass");
			inspectClass(cSuper, obj, recursive, depth+1);
		}
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
		constructorHandler(c, obj, tab);
		methodHandler(c, obj, tab);
		fieldHandler(c, obj, recursive, depth, tab);
		arrayHandler(c, obj, recursive, depth, tab);
		
		
		
	}

	private void arrayHandler(Class c, Object obj, boolean recursive, int depth, String tab) {
		if (c.isArray()) {
			int len = Array.getLength(obj);
			System.out.println(tab + " Object is array");
			System.out.println(tab + "  Type: " + c.getComponentType());
			System.out.println(tab + "  Length: " + len);
			System.out.println(tab + "  Values:");
			for (int i = 0; i < len; ++i) {
				Object value = Array.get(obj, i);
				if (!recursive || value == null) {
					System.out.println(tab + "   " + value);
				} else {
					System.out.println(tab + "   Inspecting array element");
					inspectClass(value.getClass(), value, recursive, depth+1);
				}
			}
		}
	}
	
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
				System.out.print(tab + "  Exception(s):");
				if (cExceptions.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : cExceptions) {
						System.out.println(tab + "   " + j.getName());
					}
				}
				System.out.print(tab + "  Parameter(s):");
				if (cParams.length == 0) {
					System.out.println(" None");
				} else {
					System.out.println();
					for (Class j : cParams) {
						System.out.println(tab + "   " + j.getName());
					}
				}
			}
		}
		
	}
	
	private void methodHandler(Class c, Object obj, String tab) {
		Method[] methods = c.getDeclaredMethods();
		for (Method j : methods) {
			String mName = j.getName();
			Class[] mExceptions = j.getExceptionTypes();
			Class[] mParams = j.getParameterTypes();
			Class mRet = j.getReturnType();
			int cMod = j.getModifiers();
		}
	}

	private void fieldHandler(Class c, Object obj, boolean recursive, int depth, String tab) {
		Field[] fields = c.getDeclaredFields();
		System.out.print(tab + " Field(s):");
		if (fields.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Field k : fields) {
				String fName = k.getName();
				Class fType = k.getType();
				int fMod = k.getModifiers();
				System.out.println(tab + "  Name: "+ fName);
				System.out.println(tab + "   Type: "+ fType);
				System.out.println(tab + "   Modifiers: "+ Modifier.toString(fMod));
				try {
					Object value = k.get(obj);
					Class cField = value.getClass();
					if (recursive) {
						System.out.println(tab + "   Value: Field is class");
						System.out.println(tab + "   Inspecting field");
						inspectClass(cField, value, recursive, depth+1);
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

	private String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; ++i) {
			res += "\t";
		}
		return res;
	}
	
}