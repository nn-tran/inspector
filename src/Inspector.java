import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * CPSC 501
 * Inspector starter class
 *
 * @author Jonathan Hudson
 */
public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, 0);
	}

	private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		String tab = indent(depth);
		String cName = c.getName();
		System.out.println(tab + "<Inspecting " + cName + ">");
				
		Class cSuper = c.getSuperclass();
		if (cSuper!=null) {
			String superName = cSuper.getName();
			System.out.println(tab + " Superclass: " + superName);
		}
		Class[] interfaces = c.getInterfaces();
		System.out.print(tab + " Interface(s):");
		if (interfaces.length == 0) {
			System.out.println(" None");
		} else {
			System.out.println();
			for (Class i : interfaces) {
				System.out.println(tab + "  " + i.getName());
			}
		}
		constructorHandler(c, obj);
		methodHandler(c, obj);
		fieldHandler(c, obj, recursive, depth);
		
		if (c.isArray()) {
			Class cComp = c.getComponentType();
			int len = Array.getLength(obj);
			for (int i = 0; i < len; ++i) {
				Object value = Array.get(obj, i);
			System.out.print("");
			}
		}
		
		if (cSuper!=null) {
			System.out.println(tab + " Inspecting superclass");
			inspectClass(cSuper, obj, recursive, depth+1);
		}
		
	}

	private void constructorHandler(Class c, Object obj) {
		Constructor[] constructors = c.getDeclaredConstructors();
		for (Constructor i : constructors) {
			String cName = i.getName();
			Class[] cExceptions = i.getExceptionTypes();
			Class[] cParams = i.getParameterTypes();
			int cMod = i.getModifiers();
		}
	}
	
	private void methodHandler(Class c, Object obj) {
		Method[] methods = c.getDeclaredMethods();
		for (Method j : methods) {
			String mName = j.getName();
			Class[] mExceptions = j.getExceptionTypes();
			Class[] mParams = j.getParameterTypes();
			Class mRet = j.getReturnType();
			int cMod = j.getModifiers();
		}
	}

	private void fieldHandler(Class c, Object obj, boolean recursive, int depth) {
		String tab = indent(depth);
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
						System.out.println(value);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					System.out.println(tab + "   Value: not visible");
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