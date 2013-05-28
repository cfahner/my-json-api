MyWebApi
=========

MyWebApi is a Java library designed to make working with online web-based (HTTP) APIs
extremely easy.

The library's goals are:

* Create an easy interface for communicating with an HTTP based API so you
  only have to worry about completing or failing a request
* Flexible callback architecture, register your callback once when your class is
  instantiated and never worry about it again
* No manual thread management for single requests
* Library usage is cleary visible while scanning source code (everything is
  prefixed with "My")

Things to consider before using this library:

* Uses UTF-8 encoding exclusively (unless specified differently by the server)
* Heavily simplified; if you need micromanagement of HTTP requests, this is not for you
* All callbacks do NOT run on any UI-thread when they are invoked, you need to make sure your
  UI-manipulation-code happens on the UI-thread yourself (<code>runOnUiThread(...)</code> in Android for example)
* Caching only happens in RAM by default (file-system caching takes some extra effort, since
  persistance methods may differ depending on your platform)

How to include
--------------

Simply merge the src/ directory into your existing project, or make a .jar
file and include that as a library.

Usage example
-------------

TODO

Things not yet implemented
--------------------------

1.  Cookie support
2.  Automatic queue creation. If the platform tells MyWebApi that all requests should be queued from now on,
    it should queue all incoming requests until the platform tells MyWebApi to process the queue. This is
	useful when the connection is down for example.
3.  Built-in JSON support (and a JSON request base class)
4.  Automatic cache serialization (right now you have to serialize the MyWebCache instance manually)