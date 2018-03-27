(ns es-edu.reader-test
  (:require [es-edu.reader :as sut]
    [cljs.test :as t :include-macros true]))

(def col-to-add-to [{:a 1} {:b 2} {:c 3}])
(def col-to-add-from [{:a 1} {:b 2} {:c 3} {:d 4} {:e 5} {:f 6}])

(deftest check
  (t/is (= 2 2)))
