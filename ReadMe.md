# jClassViewer

jClassViewer is a command line utility which examines all classes and its members for specified namespace. It extracts this information from a local or a remote jar file. All these details can be stored in a text, json or xml formats for easier parsing.


To get started using the command-line utility, use:

```
gradle clean build && java -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar --help
```

**Sample run:**

```
gradle clean build && java -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar --path https://repo1.maven.org/maven2/org/springframework/spring-web/7.0.0-M3/spring-web-7.0.0-M3.jar --output json
```

> **_NOTE:_**  XML Generation uses XStream library, which hasn't yet upgraded to using Java Module system. Workaround would be to expose all non-public members outside the containing module. Not ideal or secure! Use `--add-opens` JVM switch to make this happen. As under:

```
java --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.text=ALL-UNNAMED --add-opens java.desktop/java.awt.font=ALL-UNNAMED -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar --path https://repo1.maven.org/maven2/org/springframework/spring-web/7.0.0-M3/spring-web-7.0.0-M3.jar --output xml
```

**Sample output would contain:**

* **Class Details:** If a file is a class/interface/abstract/enum class, along with inheritance details, annotations, access modifiers, class compiled jdk version etc
* **Properties Details:** All properties along with access modifiers, arguments & annotations details
* **Method Details:** All public/private methods along with its entire signatures i.e. arguments and their types/annotations, return types, exceptions, access modifiers etc.

**Output:**

```
{
  "ArchiveName": "https://repo1.maven.org/maven2/org/springframework/spring-web/7.0.0-M3/spring-web-7.0.0-M3.jar",
  "ClassFiles": [
    ...
    {
      "ClassName": "org.springframework.web.bind.annotation.GetMapping",
      "ClassAccessModifiers": [
        "abstract"
      ],    
      "Inherits": "java.lang.Object",
      "ImplementsList": [
        "java.lang.annotation.Annotation"
      ],  
      "MajorNumber": 61,
      "MinorNumber": 0,
      "Annotations": [
        { 
          "Name": "java.lang.annotation.Target",
          "Elements": [
            { 
              "value": "[METHOD]"
            }
          ] 
        },    
        {     
          "Name": "java.lang.annotation.Retention",
          "Elements": [
            {
              "value": "RUNTIME"
            }
          ]
        },  
        { 
          "Name": "java.lang.annotation.Documented"
        },
        {
          "Name": "org.springframework.web.bind.annotation.RequestMapping",
          "Elements": [
            {
              "method": "[GET]"
            }
          ]
        }
      ],
      "MethodList": [
        {
          "MethodName": "name",
          "MethodAccessModifiers": [
            "abstract"
          ],
          "ReturnType": "java.lang.String",
          "Annotations": [
            {
              "Name": "org.springframework.core.annotation.AliasFor",
              "Elements": [
                {
                  "annotation": "Lorg.springframework.web.bind.annotation.RequestMapping;"
                }
	      ]
            }
          ]
        },
	...
```

# Disclaimer:
This tool was developed as part of a Hackfest event at veracode, with hope to be useful for accelerating research work. Its more under development with more stability with active usage within team. Not accepting any PR/bug reports at the moment. All work done as part of this is a personal project, so direct all complaints to me.

# LICENSE:

TreeViewer is released under the [MIT license](https://opensource.org/licenses/MIT)

```
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
