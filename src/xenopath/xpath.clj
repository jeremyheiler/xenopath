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

(defn ^:private lookup*
  "Low level lookup function where return-type is a QName object."
  [expr source return-type]
  (let [doc (dom/parse-xml source)]
    (if (instance? XPathExpression expr)
      (.evaluate expr doc return-type)
      (.evaluate (new-xpath) expr doc return-type))))

(defn lookup-boolean
  "Lookup a boolean value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/BOOLEAN))

(defn lookup-node
  "Lookup a single node with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/NODE))

(defn lookup-nodeset
  "Lookup a sequence of nodes with the given XPath expression."
  [expr source]
  (dom/node-seq (lookup* expr source XPathConstants/NODESET)))

(defn lookup-number
  "Lookup a number value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/NUMBER))

(defn lookup-string
  "Lookup a string value with the given XPath expression."
  [expr source]
  (lookup* expr source XPathConstants/STRING))

(defn ^:private lookup-fn
  [key]
  (condp = key
    :boolean lookup-boolean
    :node    lookup-node
    :nodeset lookup-nodeset
    :number  lookup-number
    :string  lookup-string))

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
  ((lookup-fn return-type) expr source))
