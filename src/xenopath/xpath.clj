(ns xenopath.xpath
  (:require [xenopath.dom :as dom])
  (:import [javax.xml.xpath XPathConstants XPathExpression XPathFactory]))

(defn ^:private new-xpath
  []
  (.newXPath (XPathFactory/newInstance)))

(defn compile-expr
  "Compiles the given XPath expression."
  [expr]
  (if-not (instance? XPathExpression expr)
    (.compile (new-xpath) expr)
    expr))

(defn ^:private to-qname
  [key]
  (condp = key
    :boolean XPathConstants/BOOLEAN
    :node    XPathConstants/NODE
    :nodeset XPathConstants/NODESET
    :number  XPathConstants/NUMBER
    :string  XPathConstants/STRING))

(defn lookup
  "Lookup a value of return-type for the given XPath expression.

  The possible return types are:
   :boolean
   :node
   :nodeset
   :number
   :string

  There also are lookup-* functions for each return type."
  [expr source return-type]
  (let [doc (dom/parse-xml source) qname (to-qname return-type)]
    (if (instance? XPathExpression expr)
      (.evaluate expr doc qname)
      (.evaluate (new-xpath) expr doc qname))))

(defn lookup-boolean
  "Lookup a boolean value with the given XPath expression."
  [expr source]
  (lookup expr source :boolean))

(defn lookup-node
  "Lookup a node with the given XPath expression."
  [expr source]
  (lookup expr source :node))

(defn lookup-nodeset
  "Lookup a nodeset with the given XPath expression."
  [expr source]
  (lookup expr source :nodeset))

(defn lookup-number
  "Lookup a number value with the given XPath expression."
  [expr source]
  (lookup expr source :number))

(defn lookup-string
  "Lookup a string value with the given XPath expression."
  [expr source]
  (lookup expr source :string))
