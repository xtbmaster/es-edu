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
    [beicon.core :as beicon]
    [clojure.set :as set])
  (:require-macros
    [cljs.core.async.macros :refer [go go-loop]]
    [hiccups.core :as hiccups :refer [html]]))


(enable-console-print!)

;; TODO clara rule for automatic quiz upload

(defonce *qzs (atom #{}))

(def dom-answer (sel1 :#answer))
(def dom-question (sel1 :#question))

(def ^:const text-field-id "text-field")
(def ^:const quiz-id "question")

(defn buffered-qzs
  "Downloads first bunch of quizes"
  []
  (swap! *qzs set/union (reader/new-elements 10 reader/qzs *qzs)))

(defn init []
  (utils/set-innerHTML
    dom-question
    "ho")
  (println "check")
  (println (buffered-qzs))

  (dommy/listen!
    dom-answer
    :submit
    "ho"))
