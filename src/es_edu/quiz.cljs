(ns es-edu.quiz
  (:require
    [es-edu.utils :as utils]
    [clojure.spec.alpha :as spec]
    [es-edu.reader :as reader]))

(enable-console-print!)

(defonce raw-qz-list (reader/upload-qzs))
(def *qzs (atom (zipmap (range) (reader/new-elements 10 raw-qz-list))))
(def *crnt-qz (atom {}))


(def *answer-correct? (atom true))

(defn color-watch
  [key identity old new]
  (utils/change-color))

(add-watch *answer-correct? :color-switcher color-watch)
  

(defn qz-id [qz]
  (first qz))


(defn qz-content [qz]
  (second qz))


(defn crnt-qz-id []
  (qz-id @*crnt-qz))


(defn crnt-qz-content []
  (qz-content @*crnt-qz))


(defn check-quiz [users-answer original-answer]
  (reset! *answer-correct?
    (= (.toLowerCase users-answer) (.toLowerCase original-answer))))


(defn inc-score! [qz]
  (let [ id (qz-id qz)
         content (qz-content qz)]
    (if (contains? content :score)
      (swap! *qzs update-in [id :score] inc)
      (swap! *qzs assoc-in [id :score] 1))))


(defn next-qz []
  (let [ qz (first (reader/new-elements 1 @*qzs))]
    (reset! *crnt-qz qz)
    (reader/get-defn (qz-content qz))))


(defn increase-score [quiz]
  (let [ old-score (get quiz :score)
         new-score (inc old-score)]
    (assoc quiz :score new-score)))


(defn get-score [data]
  (let [score :score]
    (get data score)))

(spec/def ::Definition string?)
(spec/def ::Expression string?)
(spec/def :unq/pair (spec/keys :req-un [::Definition ::Expression]))


(defn qz-defn [qz]
  ;; { :pre [(spec/explain :unq/pair (crnt-qz-content))]
  ;;   :post [(spec/explain string? %)]}
  (get (qz-content qz) :Definition))


(defn qz-expr [qz]
  ;; { :pre [(spec/explain :unq/pair (crnt-qz-content))]
  ;;   :post [(spec/explain string? %)]}
  (get (qz-content qz) :Expression))

