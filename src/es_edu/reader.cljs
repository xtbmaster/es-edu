(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [es-edu.macros :refer [slurp]]
    [cljs.reader :as reader]
    [clojure.set :as set]
    [clojure.string :as string]
    [goog.net.cookies]
    [es-edu.utils :as utils]))

(enable-console-print!)


(defn upload-qzs []
  (reader/read-string (slurp "resources/sp-expr.edn")))

(defn from-local-storage [name]
  (reader/read-string (utils/get-item name)))

(defn add-id
  "Adds an int-based id representation to a quiz, to prevent string searching."
  [qzs]
  (let [ total (count qzs)
         new-id (inc total)]
    (assoc qzs :id new-id)))

(defn increase-score [quiz]
  (let [ old-score (get quiz :score)
         new-score (inc old-score)]
    (assoc quiz :score new-score)))
  

(spec/def ::Definition string?)
(spec/def ::Expression string?)
(spec/def :unq/pair (spec/keys :req-un [::Definition ::Expression]))

(defn get-score [data]
  (let [score :score]
    (get data score)))

  
(defn get-defn [data]
   ;; { :pre [(spec/explain :unq/data data)]
   ;;   :post [(spec/explain string? %)]}
    (get data :Definition))


(defn get-expr [data]
  ;; { :pre [(spec/explain :unq/data data)]
  ;;   :post [(spec/explain string? %)]}
    (get data :Expression))


(defn new-elements
  "Gets `n` elements of `col-from` which are not present in `col-to`, Or just `n` elements from `col-from`."
  ([n col-from] (vec (take n (repeatedly #(rand-nth (vec col-from))))))
  ([n col-from col-to] (loop [ agg col-to
                               amount-taken 0]
                         (let [ unique (not-empty (vec (set/difference (set col-from) (set agg))))
                                element (rand-nth unique)]
                           (if (or (= amount-taken n) (nil? element))
                             agg
                             (recur (conj agg element) (inc amount-taken)))))))
