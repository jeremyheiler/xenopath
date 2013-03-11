# Xenopath

A Clojure XPath library that wraps `java.xml.xpath` and `org.w3c.dom`.

## Usage

```clojure
(require '(xenopath [xpath :as xpath] [dom :as dom]))

(def doc (dom/parse-xml "<foo bar=\"baz\">Foo</foo>"))

(xpath/lookup-string "/foo" doc)
; => "Foo"
```

## License

Copyright © 2013 Jeremy Heiler

Distributed under the Eclipse Public License, the same as Clojure.
