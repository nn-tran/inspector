import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InspectorTest {

	@Test
	void testInspect() {
		Inspector ins = new Inspector();
		assertEquals("\t\t\t", ins.indent(3));
		
	}

}
