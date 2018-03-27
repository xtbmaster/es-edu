(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [es-edu.macros :refer [slurp]]
    [cljs.reader :as edn]))

(enable-console-print!)


(defonce qzs (edn/read-string (slurp "resources/sp-expr.edn")))


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

;; TODO use set!

(defn random-element
  "Gets a random elem from a COL. If passed to cols - will return a unique element."
  ([col] (rand-nth col))
  ([col-from col-to]
    (loop [element (rand-nth col-from)]
      (if (contains? col-to element)
        (recur (rand-nth col-from))
        (element)))))



