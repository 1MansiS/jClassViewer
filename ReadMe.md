#jClassViewer

jClassViewer is a command line utility which examines all specified classes and their members from binary archive files. All these details are extracted in a text and json file format.


To get started using the command-line utility, use:

```
gradle clean build && java -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar --help
```

Sample run

```
gradle clean build && java -jar build/libs/NamespaceTreeViewer-1.0-SNAPSHOT.jar -p ./spring.jar -n "org.springframework.web.servlet.handler" -o json
```

Sample output would contain:

* Is File a class/interface/abstract/enum class, along with access modifiers, class compiled jdk version etc
* Class it extends and list of classes it implements
* Class/Method/Argument/Properties level annotations details(type,elements)
* list of all properties & methods, along with access modifiers, arguments & annotations details
* List of exceptions thrown
* return types, per method


#Disclaimer:
This tool was developed as part of a Hackfest event at veracode, with hope to be useful for accelerating research work. Its more under development with more stability with active usage within team. Not accepting any PR/bug reports at the moment. All work done as part of this is a personal project, so direct all complaints to me.

#LICENSE:

TreeViewer is released under the [MIT license](https://opensource.org/licenses/MIT)

```
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
