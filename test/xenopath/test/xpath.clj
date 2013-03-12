(ns xenopath.test.xpath
  (:require [clojure.test :refer :all]
            [xenopath.xpath :as xpath])
  (:import [javax.xml.xpath XPathConstants XPathExpression XPathFactory]
           [org.w3c.dom Node NodeList]))

(deftest test-compile-expr
  (let [expr (xpath/compile-expr "/a")]
    (is (instance? XPathExpression expr))
    (is (identical? expr (xpath/compile-expr expr)))))

(deftest test-lookup-boolean
  (is (instance? Boolean (xpath/lookup-boolean "/a" "<a>true</a>")))
  (is (instance? Boolean (xpath/lookup "/a" "<a>false</a>" :boolean))))

(deftest test-lookup-node
  (is (instance? Node (xpath/lookup-node "/a" "<a/>")))
  (is (instance? Node (xpath/lookup "/a" "<a/>" :node))))

(deftest test-lookup-nodeset
  (is (instance? NodeList (xpath/lookup-nodeset "/a" "<a><b/><b/></a>")))
  (is (instance? NodeList (xpath/lookup "/a" "<a><b/><b/></a>" :nodeset))))

(deftest test-lookup-number
  (is (instance? Number (xpath/lookup-number "/a" "<a>1</a>")))
  (is (instance? Number (xpath/lookup "/a" "<a>1</a>" :number))))

(deftest test-lookup-string
  (is (instance? String (xpath/lookup-string "/a" "<a>b</a>")))
  (is (instance? String (xpath/lookup "/a" "<a>b</a>" :string))))
