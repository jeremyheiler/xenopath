(ns xenopath.dom
  (:import [java.io ByteArrayInputStream]
           [javax.xml.parsers DocumentBuilderFactory]
           [org.w3c.dom Document Node NodeList])
  (:refer-clojure :exclude [name]))

(defn parse-xml
  "Parses XML from the given source. Returns a Document.

  Supported source types are: String, File, InputStream and
  org.xml.sax.InputSource. To convert other types to an InputStream,
  such as URL and URL, use clojure.java.io/input-stream."
  [source]
  (if (instance? Document source)
    source
    (let [doc-builder (.newDocumentBuilder (DocumentBuilderFactory/newInstance))]
      (if (string? source)
        (.parse doc-builder (ByteArrayInputStream. (.getBytes source)))
        (.parse doc-builder source)))))

(defn ^:private node-seq
  ([nodelist]
     (node-seq nodelist 0))
  ([nodelist index]
     (lazy-seq (if-not (= index (.getLength nodelist))
                 (cons (.item nodelist index) (node-seq nodelist (inc index)))
                 nil))))

(defn name
  [node]
  (.getNodeName node))

(defn children?
  [node]
  (.hasChildNodes node))

(defn children
  [node]
  (node-seq (.getChildNodes node)))

(defn text
  [node]
  (.getTextContent node))

(defn attributes?
  [node]
  (.hasAttributes node))

(defn attributes
  [node]
  (.getAttributes node))
