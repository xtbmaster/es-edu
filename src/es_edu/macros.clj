(ns es-edu.macros
  (:refer-clojure :exclude [slurp spit]))

(defmacro slurp [file]
  (clojure.core/slurp file))

(defmacro spit [file data]
  (clojure.core/spit file data))

