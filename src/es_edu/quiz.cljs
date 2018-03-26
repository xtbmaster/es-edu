(ns es-edu.quiz
  (:require
    [es-edu.reader :as reader]))

(defn new-quiz []
  (rand-int 30))
