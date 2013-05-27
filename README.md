MyWebApi
=========

MyWebApi is a Java library designed to make working with online Web-based APIs
extremely easy.

The library's goals are:

* Create an easy interface for communicating with a RESTful Web-Api
* Persist callbacks / listeners across multiple instances of the same class
  (Android recreates instances when the device is rotated, for example)
* Built-in caching (that is only there when you need it)
* Library usage is cleary visible while scanning source code (everything is
  prefixed with "My")

Things to consider before using this library:

* Uses UTF-8 encoding exclusively

How to include
--------------

Simply merge the src/ directory into your existing project, or make a .jar
file and include that as a library.

Usage example
-------------

TODO

Wishlist
--------

* Automatic queue creation. If the platform tells MyWebApi that all requests should be queued from now on,
  it should queue all incoming requests until the platform tells MyWebApi to process the queue.