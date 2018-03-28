(ns es-edu.quiz
  (:require
    [es-edu.reader :as reader]))

(enable-console-print!)

(defn check-quiz [question answer]
  (println (= (.toLowerCase question) (.toLowerCase answer))))
