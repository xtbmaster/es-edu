(ns es-edu.app
  (:require
    [es-edu.utils :as utils]
    [es-edu.weather :as weather]
    [es-edu.rules :as rules]
    [es-edu.quiz :as quiz]
    [es-edu.reader :as reader]
    [hiccups.runtime]
    [dommy.core :as dommy :refer-macros [sel sel1]]
    [cljs.core.async :refer [put! chan <! >! timeout close!]]
    [beicon.core :as beicon])
  (:require-macros
    [cljs.core.async.macros :refer [go go-loop]]
    [hiccups.core :as hiccups :refer [html]]))


(enable-console-print!)

;; TODO clara rule for automatic quiz upload

(defonce *qzs (atom {}))

(def dom-answer (sel1 :#answer))
(def dom-question (sel1 :#question))

(def ^:const text-field-id "text-field")
(def ^:const quiz-id "question")

;; (defn buffered-quizes
;;   "Downloads first bunch of quizes"
;;   []
;;   (swap! *qzs merge (reader/get-qz 10)))

(defn init []
  (utils/set-innerHTML
    dom-question
    "ho"
    #_(quiz/new-quiz @quizes))

  (dommy/listen!
    dom-answer
    :submit
    "ho"
    #_(let [ answer (utils/component-value text-field-id)]
           question "hoe"
         (println answer " !!!!!!!!!!!! " question)
         (println (= (.toLowerCase answer) (.toLowerCase question)))
         #(quiz/check-quiz question answer))))
