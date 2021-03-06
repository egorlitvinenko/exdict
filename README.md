# exdict
Application Exception Dictionary Helper

It helps automatically support for API Exception dictionaries like "Oracle Error Codes" for example.
The goal is programmer can using <code>throw new ExdictException</code> like usual application exception, which automatically generate new properties files, where other people (technical or analytical stuff or programmer too) can add additional information or use internationalization.

## About classes

<code>ExceptionInfo</code> class contains information about exception:
* code (Integer, required);
* message (String, required);
* developerMessage (String, optional);
* helpMessage (String, optional).

Code and message are unique, but message can specified with group of exceptions (<code>IGroupInfoHelper</code>).
By default code generates for each new exception message (<code>ExdictCodeGenerator</code>).
And by default group is a part of message, if you specify <code>group</code> and <code>yourmessage</code> a message which stored will be <code>group:yourmessage</code>.
All this interfaces combain into <code>INamespace</code>, which provides different implementations and error's sources/storages for different libraries in one application. Each namespace contains default group and can contains specific groups. Group's names can be the same in different namespaces. See example of namespace initialization in <code>UsingOfExdictException</code>.


## Add custom functionality

For each property of <code>ExceptionInfo</code> you can specify loader (interface <code>ExdictErrorsLoader</code>) from default exception dictionaries and you have to specify classes for saving new exceptions (interface <code>ExdictErrorsStore</code>, or set ignored properties with <code>DefaultErrorsStoreProvider.getExcludedProperties</code> method). 

Loaders and Stores combain into <code>ResolverInitProvider</code> and <code>ErrorsStoreProvider</code> accordingly and set in <code>INamespace</code>. By default <code>ExdictContext</code> use own default namespace.

So after setting you can simple throw <code>ExdictException</code> or <code>ExdictRuntimeException</code> (or subclass), which implements <code>IExdictExcepton</code>, which automatically support ExceptionInfos and generate for you property files by default. After that you can take new files, add additional info if necessary and paste them into default exception dictionaries. And additional info can be used like properties of <code>IExdictExcepton</code> when handling.

To start you can see example in <code>UsingOfExdictException</code>. There is example of default settings initialization and simple using.
After running code you see new files in project location where will be write new property files. In src/test/resources you see default exceptions.

## Additional info

1. Internationalization doesn't support. Now the main loader implementation is <code>ClasspathPropertiesErrorsLoader</code>;
2. Code must be an Integer. But in properties file you can use another property key. You have to specify code loader like in example;
3. It's not enough tested for production using and currently under development;
4. I'm going to make review after some using.