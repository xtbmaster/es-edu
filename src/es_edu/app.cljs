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

(def greeting "Hola! It's time to learn some Spanish words! Press Enter to start!")


(enable-console-print!)

;; TODO clara rule for automatic quiz upload
;; TODO clara rule for background change
;; TODO scoring



(def dom-answer (sel1 :#answer))
(def dom-question (sel1 :#question))

(def ^:const text-field-id "text-field")
(def ^:const quiz-id "question")

(defn write-text [text]
  (utils/set-innerHTML
    (sel1 :#question)
    text))
  
(defn answer! [users-answer original-answer]
  (do
    (quiz/check-quiz users-answer original-answer)
    (write-text original-answer)
    (println users-answer " " original-answer)))
  
(defn init []
  (write-text greeting)

  (dommy/listen!
      (sel1 :#answer)
    :submit
    #(let [ users-answer (utils/component-value text-field-id)
            skip? (or
                    (= (utils/component-value quiz-id) greeting)
                    (empty? users-answer))]
       (if skip?
         (write-text (quiz/next-qz))
         (let [ original-answer (reader/get-expr @quiz/*current-qz)]
           (answer! users-answer original-answer))))))



