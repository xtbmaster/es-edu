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


(def dom-answer (sel1 :#answer))
(def dom-quesion (sel1 :#question))

(def ^:const text-field-id "text-field")

(defn init []
  (utils/set-innerHTML
    dom-quesion
    (str (quiz/new-quiz)))
  (println (reader/check))

  (dommy/listen!
    dom-answer
    :submit
    #(.alert js/window (utils/component-value text-field-id))))

