Run:

```
gradle clean build && java -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar -p /Users/msheth/Documents/Research-Projects/./Spring_MVC_4.x/spring_mvc_4.x_research_testcases/existing_testcases/MultiActionController/source/lib/spring.jar -n "org.springframework.web.servlet.handler"
```

Output:
- Is File a Class/Interface/Abstract Class 
- Imports/Implements
- Class/Method/Argument/Properties level annotations
- list of properties, along with access modifiers
- list of methods, along with access modified
- List of exceptions thrown
- return types, per method

Design:
- Created a dedicated module OutputGenerator, which takes an arraylist of classfile objects with all loaded data per class file interrogated, and generates requested file format. Can add additional constrcutor when more formats are added
	- No Jackson or any output related libraries to be included in any other parts of this project
- Created a dedicated module for extracting required information from a Class file. This module's entry point would take yaml config file and Input Stream object of a single class file for interrogation. It will return a populated ClassFile Object.
	- No Apache BCEL related librraies to be included in any other parts of the project
- Engine class, will create an array list with each *needed* file, as key and corresponding ClassFile object with populated data. For non-class files, value could be null or whatever.

Assumptions:
- POJO field names mapping to output ? Using Gson's [Field Naming Strategy](https://www.javadoc.io/doc/com.google.code.gson/gson/2.6/com/google/gson/FieldNamingPolicy.html#UPPER_CAMEL_CASE) for field mapping. So, for any future POJO field additions choose accordingly.
- Not generating a ToC of input jar file at the moment. Just looking for .class files.

LICENSE:

TreeViewer is released under the [MIT license](https://opensource.org/licenses/MIT)

```
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
