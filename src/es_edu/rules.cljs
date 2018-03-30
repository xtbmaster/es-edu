(ns es-edu.rules
  (:require
    [clara.rules.accumulators :as acc]
    [clara.rules :refer
      [defsession defrule defquery query insert! insert fire-rules]]))

;; (defrule add-qz
;;   "Adds new quizzes to a qz buffer if there are none exist (at the app first start)
;;    or if a user has answered some question for 10 times, so it can add a new one.")











(defrecord MilkTime [value])
(defrecord FoxAngry [yesno])

(def high-threshold 10)
(def angryfox-threshold 15)

(defn cannot-run-anymore [i]
  (prn "The fox will stop dancing ... no fresh milk for " i " seconds"))

(defrule too-long-without-milk
  ;; 1 - selects all values greater then threshold
  ;; 2 - binds the value to a local var. It could be any field of the mattched expression
  ;; ([?person <- Person (= first-name "Alice") (= ?last-name last-name)])...
  [MilkTime (> value high-threshold) (= ?value value)]
  =>
  (insert! (->FoxAngry (> ?value angryfox-threshold)))
  (cannot-run-anymore ?value))

(defn my-count []
  (acc/accum
    { :initial-value 0
      :reduce-fn
      (fn [count value] (if (get value :yesno) (inc count) count))
      :retract-fn
      (fn [count retracted] (dec count))
      :combine-fn +}))

(defsession session 'es-edu.rules)
(def global-session (atom session))

(defn new-session []
  (reset! global-session session))

(defn new-milk-time [i]
  (reset!
    global-session
    (-> @global-session
      (insert (->MilkTime i))
      (fire-rules)))

 (defquery angry-fox []
   [?angry_foxes <- FoxAngry (= yesno true)])

 (defn is-fox-angry? []
   (> (count (query @global-session angry-fox)) 1))


 (defquery angriness-level []
   [?level <- (my-count) :from [FoxAngry]])

 (defn angry-level []
   [:?level (first (query @global-session angriness-level))]))
