(ns es-edu.quiz
  (:require
    [es-edu.reader :as reader]))

(enable-console-print!)

(defn new-quiz [bufferd-quizzes]
  (let [ quiz (rand-nth buffered-quizzes)]
    (reader/get-defn quiz)))

(defn check-quiz [question answer]
  (println (= (.toLowerCase question) (.toLowerCase answer))))
