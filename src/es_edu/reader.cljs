(ns es-edu.reader
  (:require
    [clojure.spec.alpha :as spec]
    [testdouble.cljs.csv :as csv]
    [es-edu.adaptor :include-macros true :refer [slurp]]))


(defn csv-data->maps [csv-data]
  (map zipmap
    (->> (first csv-data) ;; First row is the header
      (map keyword) ;; Drop if you want string keys instead
      repeat)
    (rest csv-data)))


(defn get-word []
  (first (csv-data->maps (slurp "resources/def_spa.csv"))))

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

(get-defn (get-word))
(get-expr (get-word))
