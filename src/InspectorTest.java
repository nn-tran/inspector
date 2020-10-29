import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

class InspectorTest {

	public class TestClass {
		public int[] c = {1,2,3};
		public TestClass(){
			
		}
		
		public TestClass(int[] intArray, String s){
			
		}
		
	
		void bar(int[] newC) {
			c = newC;
		}
	}
	
	public class TestClass2 {
		public boolean a;
		
		public TestClass2(int[] intArray, String s){
			
		}
		
		int[] foo() {
			int[] c = {2,4,5};
			return c;
		}

	}
	
	@Test
	void testInspect() throws NoSuchMethodException, SecurityException {
		Inspector ins = new Inspector();
		TestClass t = new TestClass();
		int[] arr = {1,2};
		TestClass2 t2 = new TestClass2(arr, "test");
		Constructor ct1 = t.getClass().getDeclaredConstructors()[0];
		Constructor ct2 = t2.getClass().getDeclaredConstructors()[0];
		Method m1 = t.getClass().getDeclaredMethods()[0];
		Method m2 = t2.getClass().getDeclaredMethods()[0];
		Field f1 = t.getClass().getDeclaredFields()[0];
		Field f2 = t2.getClass().getDeclaredFields()[0];
		
		
		assertEquals("\t\t\t", ins.indent(3));
		
		assertEquals(
				  "\t  CONSTRUCTOR\n"
				+ "\t   Name: InspectorTest$TestClass\n"
				+ "\t   Exceptions-> NONE\n"
				+ "\t   Parameters->\n"
				+ "\t   InspectorTest\n"
				+ "\t   Modifiers-> public\n", ins.printConstructor(ct1, "\t"));
		
		assertEquals(
				  "\t  CONSTRUCTOR\n"
				+ "\t   Name: InspectorTest$TestClass2\n"
				+ "\t   Exceptions-> NONE\n"
				+ "\t   Parameters->\n"
				+ "\t   InspectorTest\n"
				+ "\t   [I\n"
				+ "\t   java.lang.String\n"
				+ "\t   Modifiers-> public\n", ins.printConstructor(ct2, "\t"));
		
		assertEquals(
				  "\t  METHOD\n"
				+ "\t   Name: bar\n"
				+ "\t   Exceptions-> NONE\n"
				+ "\t   Parameters->\n"
				+ "\t   [I\n"
				+ "\t   Return type: void\n"
				+ "\t   Modifiers-> \n", ins.printMethod(m1, "\t"));
		
		assertEquals(
				  "\t  METHOD\n"
				+ "\t   Name: foo\n"
				+ "\t   Exceptions-> NONE\n"
				+ "\t   Parameters-> NONE\n"
				+ "\t   Return type: [I\n"
				+ "\t   Modifiers-> \n", ins.printMethod(m2, "\t"));
		
		assertEquals(
				  "\t  FIELD\n"
				+ "\t   Name: c\n"
				+ "\t   Type: class [I\n"
				+ "\t   Modifiers-> public\n", ins.printField(f1, "\t"));
		
		assertEquals(
				  "\t  FIELD\n"
				+ "\t   Name: a\n"
				+ "\t   Type: boolean\n"
				+ "\t   Modifiers-> public\n", ins.printField(f2, "\t"));
	}

}
