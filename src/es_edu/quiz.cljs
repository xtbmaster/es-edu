(ns es-edu.quiz
  (:require
    [es-edu.reader :as reader]))

(enable-console-print!)

(defonce raw-qz-list (reader/upload-qzs))
(def *qzs (atom (into #{} (reader/new-elements 10 raw-qz-list))))
(def *current-qz (atom {}))

(defn check-quiz [question answer]
  (println (= (.toLowerCase question) (.toLowerCase answer))))

(defn next-qz []
  (do
    (reset! *current-qz (first (reader/new-elements 1 @*qzs)))
    (reader/get-defn @*current-qz)))
