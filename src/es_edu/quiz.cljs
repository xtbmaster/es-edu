(ns es-edu.quiz
  (:require
    [es-edu.reader :as reader]))

(enable-console-print!)

(defonce raw-qz-list (reader/upload-qzs))
(def *qzs (atom (into #{} (reader/new-elements 10 raw-qz-list))))
(def *current-qz (atom {}))
(def *answer-correct? (atom true))

(defn check-quiz [users-answer original-answer]
  (reset! *answer-correct?
    (= (.toLowerCase users-answer) (.toLowerCase original-answer))))

(defn next-qz []
  (let [ qz (first (reader/new-elements 1 @*qzs))]
    (reset! *current-qz qz)
    (reader/get-defn qz)))
