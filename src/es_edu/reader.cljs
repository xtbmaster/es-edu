(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [es-edu.macros :refer [slurp spit]]
    [cljs.reader :as edn]))

(enable-console-print!)


(defonce qzs (into #{} (edn/read-string (slurp "resources/sp-expr.edn"))))

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


#_(defn save-data [file data]
    (spit file data))

(defn random-element
  "Gets a random elem from a COL-FROM. If passed two collections - will return a unique element,
   that not exists in COL-TO"
  ([col-from] (rand-nth col-from))
  ([col-from col-to]
    (loop [element (rand-nth col-from)]
      (if (some #(= element %) col-to)
        (recur (rand-nth col-from))
        element))))


(defn new-elements [n col-from col-to]
  (concat col-to (take n (random-element col-from col-to))))
