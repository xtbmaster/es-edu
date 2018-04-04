(ns es-edu.reader
  (:require
    [es-edu.macros :refer [slurp]]
    [es-edu.utils :as utils]
    [cljs.reader :as reader]
    [clojure.set :as set]
    [clojure.string :as string]
    [goog.net.cookies]))

(enable-console-print!)


(defn upload-qzs []
  (reader/read-string (slurp "resources/sp-expr.edn")))

(defn from-local-storage [name]
  (reader/read-string (utils/get-item name)))


(defn new-elements
  "Gets `n` elements of `col-from` which are not present in `col-to`, Or just `n` elements from `col-from`."
  ([n col-from] (new-elements n col-from #{}))
  ([n col-from col-to] (loop [ agg col-to
                               amount-taken 0]
                         (let [ unique (not-empty (vec (set/difference (set col-from) (set agg))))
                                element (rand-nth unique)]
                           (if (or (= amount-taken n) (nil? element))
                             agg
                             (recur (conj agg element) (inc amount-taken)))))))
