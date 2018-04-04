(ns es-edu.app
  (:require
    [es-edu.utils :as utils]
    [es-edu.rules :as rules]
    [es-edu.quiz :as quiz]
    [hiccups.runtime]
    [dommy.core :as dommy :refer-macros [sel sel1]]
    [beicon.core :as beicon])
  (:require-macros
    [cljs.core.async.macros :refer [go go-loop]]))

(def greeting "Hola! It's time to learn some Spanish words! Press Enter to start!")


(enable-console-print!)

;; TODO clara rule for automatic quiz upload to the collection


(def dom-answer (sel1 :#answer-form))
(def dom-question (sel1 :#question))

(def ^:const text-field-id "text-field")
(def ^:const quiz-id "question")

(defn write-text [text]
  (utils/set-innerHTML
    (sel1 :#question)
    text))
  
(defn answer! [users-answer current-qz]
  (println users-answer " " (quiz/qz-expr current-qz) " " current-qz)
  (let [correct? (quiz/check-quiz users-answer (quiz/qz-expr current-qz))]
    (when correct?
      (quiz/inc-score! current-qz))
    correct?))
    
  
(defn init []
  (write-text greeting)

  (dommy/listen!
    dom-answer
    :submit
    #(let [ users-answer (utils/component-value text-field-id)
            skip? (or
                    (= (utils/read-innerHTML dom-question) greeting)
                    (empty? users-answer))]
       (if skip?
         (write-text (quiz/next-qz))
         (let [ correct? (answer! users-answer @quiz/*crnt-qz)]
           (if correct?
             (do
               (write-text (quiz/next-qz))
               (utils/clear-field! text-field-id))
             (write-text (quiz/qz-expr @quiz/*crnt-qz))))))))



