# exdict
Application Exception Dictionary Helper

It helps automatically support for API Exception dictionaries like "Oracle Error Codes" for example.
The goal is using <code>throw new ExdictException</code> like usual application exception, which automatically generate new properties files,
where we can add additional information or use internationalization.

## About classes

ExceptionInfo class contains information about exception:
* code (Integer, required);
* message (String, required);
* developerMessage (String, optional);
* helpMessage (String, optional).
Code and message are unique, but message can specified with group of exceptions (IGroupInfoHelper).
By default code generates for each new exception message (ExdictCodeGenerator).
And by default group is a part of message, if you specify group and yourmessage a message which stored will be "group:yourmessage".

For each property of ExceptionInfo you can specify loader (interface ExdictErrorsLoader) from default exception dictionaries and you have to specify classes for saving new exceptions (interface ExdictErrorsStore, or set ignored properties with DefaultErrorsStoreProvider.getExcludedProperties method). 

Loaders and Stores combain into ResolverInitProvider and ErrorsStoreProvider accordingly and set in ExdictContext.

So after setting you can simple throw ExdictException or ExdictRuntimeException (or subclass), which automatically support ExceptionInfos 
and generate for you properties file by default.

You can see example in UsingOfExdictException.