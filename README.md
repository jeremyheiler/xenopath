# Xenopath

A Clojure XPath library that wraps `java.xml.xpath` and `org.w3c.dom`.

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
