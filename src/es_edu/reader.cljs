(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [testdouble.cljs.csv :as csv]
    [es-edu.macros :refer [slurp]]))


(spec/def ::valid-string (spec/valid? #(keyword? (keyword (spec/conform string? %)) %)))

(defn stringer
  "Fixes 'broken' strings from .csv file"
  [raw-string]
  { :pre [(spec/valid? string? raw-string)]
    :post [(spec/explain ::valid-string %)]}
  (->
    raw-string
    (.split "\"")
    (.join "")))

(defn check []
  (println (spec/valid? ::valid-string (stringer (first (first (csv/read-csv (slurp "resources/def_spa.csv"))))))))

(defn csv->map []
  (let [ vector (csv/read-csv (slurp "resources/def_spa.csv"))
         ks (vec (map (comp keyword stringer) (first vector)))]
    (for [input (rest vector)]
      (zipmap ks input))))

(spec/def ::Definition string?)
(spec/def ::Expression string?)
(spec/def :unq/pair (spec/keys :req-un [::Definition ::Expression]))

(defn get-defn [pair]
  { :pre [(spec/valid? :unq/pair pair)]
    :post [(spec/valid? string? %)]}
  (let [definition :Definition]
    (get pair definition)))


(defn get-expr [pair]
  { :pre [(spec/valid? :unq/pair pair)]
    :post [(spec/valid? string? %)]}
  (let [expression :Expression]
    (get pair expression)))

