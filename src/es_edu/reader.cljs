(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [es-edu.macros :refer [slurp]]
    [cljs.reader :as edn]
    [clojure.set :as set]
    [clojure.string :as string]))

(enable-console-print!)


(defn upload-qzs []
  (edn/read-string (slurp "resources/sp-expr.edn")))

(spec/def ::Definition string?)
(spec/def ::Expression string?)
(spec/def :unq/pair (spec/keys :req-un [::Definition ::Expression]))


(defn get-defn [pair]
   ;; { :pre [(spec/explain :unq/pair pair)]
   ;;   :post [(spec/explain string? %)]}
  (let [definition :Definition]
    (get pair definition)))


(defn get-expr [pair]
  ;; { :pre [(spec/explain :unq/pair pair)]
  ;;   :post [(spec/explain string? %)]}
  (let [expression :Expression]
    (get pair expression)))


(defn new-elements
  "Gets N of COL-FROM elements which are not present in COL-TO, Or just N from COL-FROM."
  ([n col-from] (vec (take n (repeatedly #(rand-nth (vec col-from))))))
  ([n col-from col-to] (loop [ agg col-to
                               amount-taken 0]
                         (let [ unique (not-empty (vec (set/difference (set col-from) (set agg))))
                                element (rand-nth unique)]
                           (if (or (= amount-taken n) (nil? element))
                             agg
                             (recur (conj agg element) (inc amount-taken)))))))
