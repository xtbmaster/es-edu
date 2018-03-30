(ns es-edu.app
  (:require
    [es-edu.utils :as utils]
    [es-edu.rules :as rules]
    [es-edu.quiz :as quiz]
    [es-edu.reader :as reader]
    [hiccups.runtime]
    [dommy.core :as dommy :refer-macros [sel sel1]]
    [beicon.core :as beicon])
  (:require-macros
    [cljs.core.async.macros :refer [go go-loop]]
    [hiccups.core :as hiccups :refer [html]]))


(enable-console-print!)

;; TODO clara rule for automatic quiz upload



(def dom-answer (sel1 :#answer))
(def dom-question (sel1 :#question))

(def ^:const text-field-id "text-field")
(def ^:const quiz-id "question")

(defn init []
  (utils/set-innerHTML
    (sel1 :#question)
    "ho2")

  (dommy/listen!
    (let [ question (quiz/next-qz)]
      (sel1 :#answer)
      :submit
      (utils/set-innerHTML
        (sel1 :#question))
      #(println (quiz/next-qz)))))

