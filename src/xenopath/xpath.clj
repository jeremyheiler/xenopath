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

(defn ^:private lookup*
  "Low level lookup function where return-type is a QName object."
  [expr source return-type]
  (let [doc (dom/parse-xml source)]
    (if (instance? XPathExpression expr)
      (.evaluate expr doc return-type)
      (.evaluate (new-xpath) expr doc return-type))))

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
  (lookup* expr source (to-qname return-type)))

(defn lookup-boolean
  "Lookup a boolean value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/BOOLEAN))

(defn lookup-node
  "Lookup a node with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/NODE))

(defn lookup-nodeset
  "Lookup a nodeset with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/NODESET))

(defn lookup-number
  "Lookup a number value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/NUMBER))

(defn lookup-string
  "Lookup a string value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/STRING))
