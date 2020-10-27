import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
    	
    	String name = c.getName();
    	Class superC = c.getSuperclass(); 
    	String superName = superC.getName();
    	Class[] interfaces = c.getInterfaces();
    	Constructor[] constructors = c.getDeclaredConstructors();
    	for (Constructor i : constructors) {
    		String cName = i.getName();
    		Class[] cExceptions = i.getExceptionTypes();
    		Class[] cParams = i.getParameterTypes();
    		int cMod = i.getModifiers();
    	}
    	
    	Method[] methods = c.getDeclaredMethods();
    	for (Method j : methods) {
    		String mName = j.getName();
    		Class[] mExceptions = j.getExceptionTypes();
    		Class[] mParams = j.getParameterTypes();
    		Class mRet = j.getReturnType();
    		int cMod = j.getModifiers();
    	}
    	
    	Field[] fields = c.getDeclaredFields();
    	for (Field k : fields) {
    		String fName = k.getName();
    		Class fType = k.getType();
    		int fMod = k.getModifiers();
    		try {
				Object value = k.get(obj);
				if (recursive) {
					Class cField = value.getClass();
					inspectClass(cField, value, recursive, depth+1);
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
