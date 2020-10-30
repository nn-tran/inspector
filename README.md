 **Reflective Object Inspector** by Nguyen Tran
This is a java object inspector.

**Code History**
- 902365ba: The long `inspectClass()` method is split up so that each significant attribute is handled in its own method (methods, constructors, fields).
- a28da2c7: `inspectClass()` used `System.out.println()` excessively, so the code was reworked such that a string is constructed first before it is printed. This enabled further simplification, as well as unit testing.
- 74e46679: The previous update allowed a method extraction of the pattern of printing 'NONE' if an array is empty, and its contents otherwise. The result is `printArrayWithData()`. Unfortunately, this cannot handle recursion, so it's only used for methods and constructors. On a different note, the unit test is also completed here.

Note that most temps are eventually replaced with queries, however the change is scattered throughout.
