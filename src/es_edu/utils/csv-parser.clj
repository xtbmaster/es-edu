(ns es-edu.utils.csv-parser
  (:require
    [clojure.java.io :as io]
    [clojure.data.csv :as csv]))


(defn line-splitter
  "Returns the hashmap of features and weights given the sequence
   of elements in a line representing a test example."
  [labels line]
  {:pre [(= (count labels) (count line))]}
  (zipmap labels line))
 
(defn parse-feature-csv
  "Processes a csv file and returns the seq of instances per topic."
  [csv-file-name line-splitter]
  (with-open [in-file (io/reader csv-file-name)]
    (let [ csv-data (csv/read-csv in-file)
           labels (first csv-data)
           content (rest csv-data)]
      (->> content
           (pmap (partial line-splitter labels))
           doall))))
 
(defn csv-collection-to-edn
  "Dumps the csv test collection into an edn file using a
   splitter function."
  [csv-file edn-file]
  (spit edn-file (pr-str (parse-feature-csv csv-file line-splitter))))
