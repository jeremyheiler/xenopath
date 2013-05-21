(ns xenopath.dom
  (:import [java.io ByteArrayInputStream StringReader StringWriter]
           [javax.xml.parsers DocumentBuilderFactory]
           [javax.xml.transform TransformerFactory OutputKeys]
           [javax.xml.transform.dom DOMSource]
           [javax.xml.transform.stream StreamResult]
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

(defn node-seq
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
  (let [f #(assoc %1 (keyword (.getName %2)) (.getValue %2))]
    (reduce f {} (node-seq (.getAttributes node)))))

(defn raw-string
  [node]
  (let [sw (StringWriter.)
        tf (.newTransformer (TransformerFactory/newInstance))]
    (doto tf
      (.setOutputProperty OutputKeys/OMIT_XML_DECLARATION "yes")
      (.setOutputProperty OutputKeys/INDENT "yes"))
    (.transform tf (DOMSource. node) (StreamResult. sw))
    (.toString sw)))
