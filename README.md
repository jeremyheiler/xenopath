# Xenopath

Xenopath is an XPath and DOM library for Clojure.

The primary goal of this project is to provide a straightforward
Clojure interface to the JDK's built-in XPath and DOM packages. The
advantage is that it allows you to operate on sequences and maps
instead of DOM collection objects. The disadvantage is that you're
still working with a stateful DOM, so tread carefully.

## Installation

Xenopath is available on [Clojars](https://clojars.org/xenopath). Add
the following dependency to your `project.clj` in order to use it.

```clojure
[xenopath "0.1.0"]
```

## Usage

```clojure

(require '(xenopath [xpath :as xpath] [dom :as dom]))

;; Get the string value of the node.
(xpath/lookup-string "/foo" "<foo>Foo</foo>")
;;=> "Foo"

;; Get the name of the node.
(dom/name (xpath/lookup-node "/foo" "<foo>Foo</foo>"))
;;=> "foo"

;; Return a sequence of text for all matching nodes.
(map dom/text (xpath/lookup-nodeset "/a/b" "<a><b>x</b><b>y</b></a>"))
;;=> ("x" "y")

;; Return the text of the first matching node.
(dom/text (xpath/lookup-node "/a/b" "<a><b>x</b><b>y</b></a>"))
;;=> "x"
(xpath/lookup-string "/a/b" "<a><b>x</b><b>y</b></a>")
;;=> "x"

;; Does the node have attributes?
(dom/attributes? (xpath/lookup-node "/foo" "<foo bar=\"baz\"/>"))
;;=> true

;; Get the attributes from a node.
(dom/attributes (xpath/lookup-node "/foo" "<foo bar=\"baz\" tar=\"taz\"/>"))
;;=> {:taz "taz" :bar "baz"}

;; Does the node have children?
(dom/children? (xpath/lookup-node "/a" "<a><b/><c/></a>"))
;;=> true
(dom/children? (xpath/lookup-node "/a/c" "<a><b/><c/></a>"))
;;=> false

;; Get the text for all the child nodes.
(map dom/text (dom/children (xpath/lookup-node "/a" "<a><b>x</b><c>y</c></a>")))
;;=> ("x" "y")
```

## License

Copyright © 2013 Jeremy Heiler

Distributed under the Eclipse Public License, the same as Clojure.
